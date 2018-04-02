package com.zjmzyx.monoceros.library.base.inject.module

import android.content.Context

import com.zjmzyx.monoceros.library.base.MyApplication
import com.zjmzyx.monoceros.library.base.inject.scope.PerApplication
import com.zjmzyx.monoceros.library.base.inject.scope.PerContext

import dagger.Module
import dagger.Provides

/**
 *
 * @author zjm
 * @date 2017/8/29
 */

@Module
class ApplicationModule(private val myApplication: MyApplication) {

    @Provides
    @PerApplication
    @PerContext("Application")
    fun provideMyApplicationContext(): Context {
        return myApplication.applicationContext
    }
}
