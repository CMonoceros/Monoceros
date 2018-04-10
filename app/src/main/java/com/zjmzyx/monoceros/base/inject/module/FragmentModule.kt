package com.zjmzyx.monoceros.base.inject.module

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment

import com.zjmzyx.monoceros.library.base.inject.scope.PerContext
import com.zjmzyx.monoceros.library.base.inject.scope.PerFragment

import dagger.Module
import dagger.Provides

/**
 *
 * @author zjm
 * @date 2017/8/29
 */

@Module
class FragmentModule(private val myFragment: Fragment) {

    @Provides
    @PerFragment
    @PerContext("Activity")
    fun provideActivityContext(): Context? {
        return myFragment.activity
    }

    @Provides
    @PerFragment
    fun provideActivity(): Activity? {
        return myFragment.activity
    }

    @Provides
    @PerFragment
    fun provideFragment(): Fragment {
        return myFragment
    }
}
