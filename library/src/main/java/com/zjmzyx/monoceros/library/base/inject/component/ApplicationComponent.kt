package com.zjmzyx.monoceros.library.base.inject.component

import android.content.Context

import com.zjmzyx.monoceros.library.base.inject.module.ApplicationModule
import com.zjmzyx.monoceros.library.base.inject.scope.PerApplication
import com.zjmzyx.monoceros.library.base.inject.scope.PerContext

import dagger.Component

/**
 *
 * @author zjm
 * @date 2017/8/29
 */

@PerApplication
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    @get:PerContext("Application")
    val applicationContext: Context?
}
