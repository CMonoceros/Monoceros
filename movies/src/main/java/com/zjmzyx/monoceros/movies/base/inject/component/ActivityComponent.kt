package com.zjmzyx.monoceros.movies.base.inject.component

import android.app.Activity
import android.content.Context
import com.zjmzyx.monoceros.library.base.inject.component.ApplicationComponent

import com.zjmzyx.monoceros.movies.base.inject.module.ActivityModule
import com.zjmzyx.monoceros.library.base.inject.scope.PerActivity
import com.zjmzyx.monoceros.library.base.inject.scope.PerContext

import dagger.Component

/**
 *
 * @author zjm
 * @date 2017/8/29
 */

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    @get:PerContext("Activity")
    val activityContext: Context?

    @get:PerContext("Application")
    val applicationContext: Context?

    val activity: Activity?
}
