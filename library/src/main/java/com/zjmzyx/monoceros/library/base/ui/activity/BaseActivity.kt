package com.zjmzyx.monoceros.library.base.ui.activity

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.test.espresso.IdlingResource
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.squareup.leakcanary.RefWatcher
import com.zjmzyx.monoceros.library.R
import com.zjmzyx.monoceros.library.base.MyApplication
import com.zjmzyx.monoceros.library.test.EspressoIdlingManager
import com.zjmzyx.monoceros.library.util.UIUtils

/**
 *
 * @author zjm
 * @date 2017/11/22
 */

abstract class BaseActivity : AppCompatActivity() {

    protected var loadingText = "加载中..."

    private var frameLayout: FrameLayout? = null
    private var emptyView: View? = null
    private var loadingView: View? = null
    private var errorView: View? = null
    protected var successView: View? = null
        private set

    val rxIdlingResource: IdlingResource
        @VisibleForTesting
        get() = EspressoIdlingManager.idlingResource

    abstract fun errorViewOnClick()

    abstract fun initView(savedInstanceState: Bundle?): View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.library_base_activity)
        if (frameLayout == null) {
            frameLayout = findViewById(R.id.library_container_base)
            initFrameLayout()
        }
    }

    fun setEmptyOnClickListener(onClickListener: View.OnClickListener) {
        if (emptyView != null) {
            val simpleDraweeView = emptyView!!.findViewById<View>(R.id.library_sdv_item)
                    as SimpleDraweeView
            simpleDraweeView.setOnClickListener(onClickListener)
        }
    }

    fun setEmptyImageUrlAndVisible(emptyImageUrl: Int) {
        if (emptyView != null) {
            frameLayout!!.removeAllViews()
            val simpleDraweeView = emptyView!!.findViewById<View>(R.id.library_sdv_item)
                    as SimpleDraweeView
            UIUtils.setSimpleDraweeView("res://" +
                    MyApplication.context!!.packageName +
                    "/" + emptyImageUrl, simpleDraweeView)
            val toolbar = emptyView!!.findViewById<Toolbar>(R.id.library_tb_item)
            setSupportActionBar(toolbar)
            frameLayout!!.addView(emptyView)
        }
    }

    fun setEmptyToolbarTitle(title: String) {
        if (emptyView != null) {
            val toolbar = emptyView!!.findViewById<Toolbar>(R.id.library_tb_item)
            toolbar.title = title
        }
    }

    fun setEmptyToolbarNavigationIcon(iconUrl: Int) {
        if (emptyView != null) {
            val toolbar = emptyView!!.findViewById<Toolbar>(R.id.library_tb_item)
            toolbar.navigationIcon = resources.getDrawable(iconUrl)
        }
    }

    private fun initFrameLayout() {
        val ctx = ContextThemeWrapper(this, R.style.library_AppTheme)
        emptyView = LayoutInflater.from(ctx)
                .inflate(R.layout.library_base_activity_empty, null)

        loadingView = View.inflate(MyApplication.context,
                R.layout.library_base_fragment_loading, null)
        val sdvLoading = loadingView!!.findViewById<View>(R.id.library_sdv_process)
                as SimpleDraweeView
        val imageRequest = ImageRequestBuilder
                .newBuilderWithResourceId(R.mipmap.library_loading)
                .build()
        val controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageRequest.sourceUri)
                .setAutoPlayAnimations(true)
                .build()
        sdvLoading.controller = controller
        frameLayout!!.addView(loadingView)

        errorView = View.inflate(MyApplication.context,
                R.layout.library_base_fragment_error, null)
        val retry = errorView!!.findViewById<View>(R.id.library_container_main) as RelativeLayout
        retry.setOnClickListener {
            errorViewOnClick()
            setLoadingViewVisible()
        }
        val sdvError = errorView!!.findViewById<View>(R.id.library_sdv_error) as SimpleDraweeView
        UIUtils.setSimpleDraweeView("res://" +
                MyApplication.context!!.packageName +
                "/" + R.drawable.library_base_error, sdvError,
                UIUtils.dp2Px(180), UIUtils.dp2Px(160))
    }

    protected fun setEmptyViewVisible() {
        frameLayout!!.removeAllViews()
        frameLayout!!.addView(emptyView)
    }

    protected fun setErrorViewVisible() {
        frameLayout!!.removeAllViews()
        frameLayout!!.addView(errorView)
    }

    protected fun setLoadingViewVisible() {
        frameLayout!!.removeAllViews()
        frameLayout!!.addView(loadingView)
    }

    @JvmOverloads protected fun replaceSuccessView(savedInstanceState: Bundle? = null) {
        successView = initView(savedInstanceState)
        frameLayout!!.removeAllViews()
        frameLayout!!.addView(successView)
    }

    @JvmOverloads protected fun setSuccessViewVisible(savedInstanceState: Bundle? = null) {
        if (successView == null) {
            successView = initView(savedInstanceState)
        }
        if (successView != null) {
            frameLayout!!.removeAllViews()
            frameLayout!!.addView(successView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val refWatcher = MyApplication.getRefWatcher(MyApplication.context!!)
        refWatcher!!.watch(this)
    }
}

