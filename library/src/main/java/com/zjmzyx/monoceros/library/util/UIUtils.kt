package com.zjmzyx.monoceros.library.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.backends.pipeline.PipelineDraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.orhanobut.logger.Logger
import com.zjmzyx.monoceros.library.base.MyApplication

import java.lang.reflect.Field

/**
 *
 * @author zjm
 * @date 2016/12/8
 */
object UIUtils {

    val CREATE_GROUP = 1
    val NO_GROUP = 3
    val JOIN_GROUP = 2

    val screenWidth: Int
        get() {
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)
            return dm.widthPixels
        }

    val screenHeight: Int
        get() {
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)
            return dm.heightPixels
        }

    private val windowManager: WindowManager
        get() = MyApplication.context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    fun dp2Px(dip: Int): Int {
        val density = BaseUtil.applicationResources.getDisplayMetrics().density
        return (dip * density + 0.5f).toInt()
    }

    fun px2Dp(px: Int): Int {
        val density = BaseUtil.applicationResources.getDisplayMetrics().density
        return (px / density + 0.5f).toInt()
    }

    fun px2sp(pxValue: Float): Int {
        val fontScale = BaseUtil.applicationResources.getDisplayMetrics().scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun sp2px(spValue: Float): Int {
        val fontScale = BaseUtil.applicationResources.getDisplayMetrics().scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    fun getPercentScreenWidth(x: Double): Int {
        return (UIUtils.screenWidth * x).toInt()
    }

    fun getPercentScreenHeight(y: Double): Int {
        return (UIUtils.screenHeight * y).toInt()
    }

    fun setSimpleDraweeView(url: String?, view: SimpleDraweeView, targetWidth: Int, targetHeight: Int) {
        if (url != null) {
            val uri = Uri.parse(url)
            val request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(ResizeOptions(targetWidth, targetHeight))
                    .build()
            val controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(view.controller)
                    .setImageRequest(request)
                    .build() as PipelineDraweeController
            view.controller = controller
        }
    }


    fun setSimpleDraweeView(url: String?, view: SimpleDraweeView) {
        if (url != null) {
            val uri = Uri.parse(url)
            val layoutParams = view.layoutParams
            val request = ImageRequestBuilder.newBuilderWithSource(uri)
                    //图片渐近式加载
                    .setProgressiveRenderingEnabled(true)
                    //解码前修改内存中图片的大小，与downsampling结合使用可处理所有类型图片，否则只能处理jpg
                    .setResizeOptions(ResizeOptions(layoutParams.width, layoutParams.height))
                    .build()
            val controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(view.controller)
                    .setImageRequest(request)
                    .build() as PipelineDraweeController
            view.controller = controller
        }
    }

}
