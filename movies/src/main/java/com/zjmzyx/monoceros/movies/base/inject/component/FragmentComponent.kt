package com.zjmzyx.monoceros.movies.base.inject.component

import android.app.Activity
import android.content.Context
import com.zjmzyx.monoceros.library.base.inject.component.ApplicationComponent

import com.zjmzyx.monoceros.library.base.inject.scope.PerContext
import com.zjmzyx.monoceros.library.base.inject.scope.PerFragment
import com.zjmzyx.monoceros.movies.base.inject.module.FragmentModule
import com.zjmzyx.monoceros.movies.module.home.fragment.MoviesListFragment

import dagger.Component

/**
 *
 * @author zjm
 * @date 2017/8/29
 */

@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(FragmentModule::class))
interface FragmentComponent {
    @get:PerContext("Activity")
    val activityContext: Context?

    @get:PerContext("Application")
    val applicationContext: Context?

    val activity: Activity?

    fun inject(moviesListFragment: MoviesListFragment)
}
