package com.zjmzyx.monoceros.library.base.ui.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.zjmzyx.monoceros.library.R
import com.zjmzyx.monoceros.library.databinding.LibraryViewLoadingBinding
import com.zjmzyx.monoceros.library.util.BaseUtil
import com.zjmzyx.monoceros.library.util.ServiceUtil

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * @author jojotoo
 * @date 2017/9/1
 */

abstract class BaseListAdapter<T>(var data: List<T>,
                                  private val recyclerView: RecyclerView?,
                                  hasHeader: Boolean)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //是否正在加载更多
    var isLoading: Boolean = false
    //是否有头部局
    var hasHeader = false
    private var preScrollState: Int = 0
    private var startY: Int = 0
    private var currentY: Int = 0
    private var loadMoreDataListener: LoadMoreDataListener? = null

    init {
        this.hasHeader = hasHeader
        if (recyclerView != null) {
            initRecyclerView(recyclerView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_TYPE_HEADER -> return onCreateHeaderHolder(parent, viewType)
            ITEM_TYPE_FOOTER -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.library_view_loading, parent, false)
                if (recyclerView!!.layoutManager is StaggeredGridLayoutManager) {
                    val params = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
                    params.isFullSpan = true
                    view.layoutParams = params
                }
                return LoadingHolder(view)
            }
            else -> return onCreateNormalHolder(parent, viewType)
        }
    }

    protected abstract fun onCreateNormalHolder(parent: ViewGroup, viewType: Int)
            : RecyclerView.ViewHolder

    protected abstract fun onCreateHeaderHolder(parent: ViewGroup, viewType: Int)
            : RecyclerView.ViewHolder

    override fun getItemCount(): Int {
        var size = data.size
        if (hasHeader) {
            size++
        }
        if (isLoading) {
            size++
        }
        return size
    }

    override fun getItemViewType(position: Int): Int {
        if (hasHeader) {
            if (position == 0) {
                return ITEM_TYPE_HEADER
            } else if (position == data.size + 1 && isLoading) {
                return ITEM_TYPE_FOOTER
            }
        } else if (position == data.size && isLoading) {
            return ITEM_TYPE_FOOTER
        }
        return ITEM_TYPE_NORMAL
    }

    fun setOnLoadMoreDataListener(loadMoreDataListener: LoadMoreDataListener) {
        this.loadMoreDataListener = loadMoreDataListener
    }

    fun setIsLoading(isLoading: Boolean) {
        Observable.just(isLoading)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { this@BaseListAdapter.isLoading = isLoading }
                .filter { aBoolean -> (!aBoolean) }
                .subscribe(object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(aBoolean: Boolean) {
                        notifyDataSetChanged()
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        ServiceUtil.reportCrash(e.message)
                    }

                    override fun onComplete() {

                    }
                })
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager
        recyclerView.setOnTouchListener { v, event ->
            var lastVisiblePositions = IntArray(0)
            var mLastVisibleItemPosition = 0
            if (layoutManager is StaggeredGridLayoutManager) {
                lastVisiblePositions = layoutManager.findLastCompletelyVisibleItemPositions(IntArray(layoutManager.spanCount))
                mLastVisibleItemPosition = BaseUtil.getMaxElem(lastVisiblePositions)
            } else if (layoutManager is LinearLayoutManager) {
                mLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            }
            val totalItemCount = layoutManager.itemCount

            recyclerView.requestDisallowInterceptTouchEvent(true)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> startY = event.y.toInt()
                MotionEvent.ACTION_MOVE -> {
                }
                MotionEvent.ACTION_UP -> {
                    currentY = event.y.toInt()
                    if (currentY < startY && mLastVisibleItemPosition == totalItemCount - 1) {
                        if (!isLoading) {
                            isLoading = true
                            notifyDataSetChanged()
                            loadMoreDataListener!!.loadMoreData()
                        }
                    }
                }
                else -> {
                }
            }
            false
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE  //停止滑动
                    -> if (Fresco.getImagePipeline().isPaused) {
                        Fresco.getImagePipeline().resume()
                    }
                    RecyclerView.SCROLL_STATE_DRAGGING -> if (preScrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                        Fresco.getImagePipeline().pause()
                    } else {
                        if (Fresco.getImagePipeline().isPaused) {
                            Fresco.getImagePipeline().resume()
                        }
                    }
                    RecyclerView.SCROLL_STATE_SETTLING  //惯性滑动
                    -> Fresco.getImagePipeline().pause()
                    else -> {
                    }
                }
                preScrollState = newState
                val layoutManager = recyclerView!!.layoutManager
                if (layoutManager is StaggeredGridLayoutManager) {
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPositions(null)
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    if (visibleItemCount > 0 && totalItemCount > 4 &&
                            lastVisibleItemPosition[0] >= totalItemCount - 3) {
                        if (Fresco.getImagePipeline().isPaused) {
                            Fresco.getImagePipeline().resume()
                        }
                    }
                } else if (layoutManager is LinearLayoutManager) {
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    if (visibleItemCount > 0 && totalItemCount > 1 &&
                            lastVisibleItemPosition >= totalItemCount - 1) {
                        if (Fresco.getImagePipeline().isPaused) {
                            Fresco.getImagePipeline().resume()
                        }
                    }
                }
            }
        })
        // get rid of default item change animation
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    fun bindLoadingHolder(holder: LoadingHolder, position: Int) {
        val imageRequest = ImageRequestBuilder
                .newBuilderWithResourceId(R.mipmap.library_loading)
                .build()
        val controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageRequest.sourceUri)
                .setAutoPlayAnimations(true)
                .build()
        holder.binding.librarySdvLoading.controller = controller
    }

    /**
     * 加载更多的接口
     */
    interface LoadMoreDataListener {
        fun loadMoreData()
    }

    class LoadingHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        var binding: LibraryViewLoadingBinding = DataBindingUtil.bind(view)

    }

    companion object {

        val ITEM_TYPE_HEADER = 3
        val ITEM_TYPE_NORMAL = 1
        val ITEM_TYPE_FOOTER = 2
    }

}
