package com.zjmzyx.monoceros.movies.base.inject.module

import android.app.Activity
import android.content.Context

import com.zjmzyx.monoceros.library.base.inject.scope.PerActivity
import com.zjmzyx.monoceros.library.base.inject.scope.PerContext

import dagger.Module
import dagger.Provides

/**
 *
 * @author zjm
 * @date 2017/8/29
 */

@Module
class ActivityModule(private val myActivity: Activity) {

    @Provides
    @PerActivity
    @PerContext("Activity")
    fun provideActivityContext(): Context {
        return myActivity
    }

    @Provides
    @PerActivity
    fun provideActivity(): Activity {
        return myActivity
    }
}
