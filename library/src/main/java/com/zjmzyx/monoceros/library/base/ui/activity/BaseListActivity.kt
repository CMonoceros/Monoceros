package com.zjmzyx.monoceros.library.base.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.Toast


import com.zjmzyx.monoceros.library.R
import com.zjmzyx.monoceros.library.base.MyApplication
import com.zjmzyx.monoceros.library.base.ui.adapter.BaseListAdapter
import com.zjmzyx.monoceros.library.databinding.LibraryBaseActivityListBinding

import java.util.ArrayList

/**
 * @author zjm
 * @date 2017/8/31
 */

abstract class BaseListActivity<T> : BaseActivity() {

    var list: MutableList<T> = ArrayList()
    var isLoadingMore = false
    var page = 1
    var canLoadMore = true
    protected var binding: LibraryBaseActivityListBinding? = null
    protected var baseListAdapter: BaseListAdapter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initOnCreateData(savedInstanceState)
        if (successView == null) {
            setLoadingViewVisible()
            initData()
        }
    }

    override fun initView(savedInstanceState: Bundle?): View {
        val view = View.inflate(this, R.layout.library_base_activity_list, null)
        binding = DataBindingUtil.setContentView(this, R.layout.library_base_activity_list)
        binding!!.libraryRvMain.layoutManager = initLayoutManager()
        baseListAdapter = initAdapter()
        baseListAdapter!!.setOnLoadMoreDataListener(object : BaseListAdapter.LoadMoreDataListener {
            override fun loadMoreData() {
                if (!isLoadingMore && !binding!!.libraryContainerRefresh.isRefreshing) {
                    //分页加载
                    isLoadingMore = true
                    if (baseListAdapter != null) {
                        baseListAdapter!!.setIsLoading(isLoadingMore)
                    }
                    initData()
                }
            }
        })
        binding!!.libraryRvMain.adapter = baseListAdapter
        binding!!.libraryContainerRefresh.setOnRefreshListener {
            binding!!.libraryContainerRefresh.isRefreshing = true
            isLoadingMore = false
            initData()
        }
        return view
    }

    protected fun getDataCompleted() {
        isLoadingMore = false
        if (binding != null) {
            binding!!.libraryContainerRefresh.isRefreshing = false
        }
        if (baseListAdapter != null) {
            baseListAdapter!!.setIsLoading(isLoadingMore)
        }
    }

    protected fun initEmptyView(): Boolean {
        return false
    }

    protected fun getDataSuccess(data: List<T>) {
        if (successView == null) {
            if (data.isEmpty() && initEmptyView()) {
                return
            } else {
                setSuccessViewVisible()
            }
        }
        if (!isLoadingMore) {
            list.clear()
        }
        list.addAll(data)
        baseListAdapter!!.notifyDataSetChanged()
    }

    fun initData() {
        if (isLoadingMore) {
            if (!canLoadMore) {
                Toast.makeText(MyApplication.context,
                        "已经到最后一页啦！", Toast.LENGTH_SHORT)
                        .show()
                getDataCompleted()
                return
            }
            page++
        } else {
            page = 1
            canLoadMore = false
        }
        getApiData(page)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected abstract fun initOnCreateData(savedInstanceState: Bundle?)

    protected abstract fun getApiData(page: Int)

    protected abstract fun initAdapter(): BaseListAdapter<*>

    protected abstract fun initLayoutManager(): RecyclerView.LayoutManager
}
