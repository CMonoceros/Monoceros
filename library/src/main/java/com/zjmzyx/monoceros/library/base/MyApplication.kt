package com.zjmzyx.monoceros.library.base

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Process
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.text.TextUtils

import com.facebook.common.internal.Supplier
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.BuildConfig
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.zjmzyx.monoceros.library.base.inject.component.ApplicationComponent
import com.zjmzyx.monoceros.library.base.inject.component.DaggerApplicationComponent
import com.zjmzyx.monoceros.library.base.inject.module.ApplicationModule

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class MyApplication : MultiDexApplication() {

    private var refWatcher: RefWatcher? = null
    var applicationComponent: ApplicationComponent? = null
        private set

    private val maxCacheSize: Int
        get() {
            val mActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val maxMemory = Math.min(mActivityManager.memoryClass * ByteConstants.MB, Integer.MAX_VALUE)
            return if (maxMemory < 32 * ByteConstants.MB) {
                8 * ByteConstants.MB
            } else if (maxMemory < 64 * ByteConstants.MB) {
                12 * ByteConstants.MB
            } else {
                maxMemory / 4
            }
        }

    override fun onCreate() {
        super.onCreate()
        setupBaseVariable()
        setupLeakCanary()
        setupLogger()
        setupDagger()
        setupFresco()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun setupBaseVariable() {
        context = applicationContext
        //获取主线程id
        myTid = Process.myTid()
    }

    private fun setupLogger() {
        val formatStrategy: FormatStrategy
        if (BuildConfig.DEBUG) {
            formatStrategy = PrettyFormatStrategy.newBuilder()
                    // (Optional) Whether to show thread info or not. Default true
                    .showThreadInfo(true)
                    // (Optional) How many method line to show. Default 2
                    .methodCount(10)
                    // (Optional) Hides internal method calls up to offset. Default 5
                    .methodOffset(0)
                    // (Optional) Global tag for every log. Default PRETTY_LOGGER
                    .tag("JoJoToo-Debug")
                    .build()
        } else {
            formatStrategy = PrettyFormatStrategy.newBuilder()
                    // (Optional) Whether to show thread info or not. Default true
                    .showThreadInfo(false)
                    // (Optional) How many method line to show. Default 2
                    .methodCount(5)
                    // (Optional) Hides internal method calls up to offset. Default 5
                    .methodOffset(0)
                    // (Optional) Global tag for every log. Default PRETTY_LOGGER
                    .tag("JoJoToo-Release")
                    .build()

        }
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return !(!BuildConfig.DEBUG && priority < Logger.WARN)
            }
        })
    }

    private fun setupDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    private fun setupLeakCanary() {
        refWatcher = LeakCanary.install(this)
    }

    private fun setupFresco() {
        val mSupplierMemoryCacheParams = Supplier {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                MemoryCacheParams(maxCacheSize, 256,
                        12 * ByteConstants.MB, 24,
                        ByteConstants.MB)
            } else {
                MemoryCacheParams(maxCacheSize, 512,
                        12 * ByteConstants.MB, 24,
                        ByteConstants.MB)
            }
        }
        //初始化fresco
        val config = ImagePipelineConfig.newBuilder(context!!)
                .setDownsampleEnabled(true)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)
                .build()

        Fresco.initialize(context!!, config)
    }

    companion object {

        var context: Context? = null
            private set
        private var myTid: Int = 0

        fun getRefWatcher(context: Context): RefWatcher? {
            val application = context.applicationContext as MyApplication
            return application.refWatcher
        }
    }
}
