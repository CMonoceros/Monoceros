package com.zjmzyx.monoceros.library.base.ui.activity


import android.os.Bundle

import com.zjmzyx.monoceros.library.base.MyApplication
import com.zjmzyx.monoceros.library.base.inject.component.ActivityComponent
import com.zjmzyx.monoceros.library.base.inject.component.DaggerActivityComponent
import com.zjmzyx.monoceros.library.base.inject.module.ActivityModule

/**
 *
 * @author zjm
 * @date 2017/7/28
 */

abstract class BaseDaggerActivity : BaseActivity() {

    protected var myActivityComponent: ActivityComponent? = null

    abstract fun setupInjector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivityComponent()
        setupInjector()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun setupActivityComponent() {
        myActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent((application as MyApplication).applicationComponent)
                .activityModule(ActivityModule(this))
                .build()
    }
}

