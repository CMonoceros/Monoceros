package com.zjmzyx.monoceros.library.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zjmzyx.monoceros.base.inject.component.FragmentComponent
import com.zjmzyx.monoceros.base.inject.module.FragmentModule

import com.zjmzyx.monoceros.library.base.MyApplication

/**
 *
 * @author zjm
 * @date 2017/9/8
 */

abstract class BaseDaggerFragment : BaseFragment() {

    protected var myFragmentComponent: FragmentComponent? = null

    abstract fun setupInjector()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (myFragmentComponent == null) {
            setupFragmentComponent()
            setupInjector()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun setupFragmentComponent() {
        myFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent((activity!!.application as MyApplication).applicationComponent)
                .fragmentModule(FragmentModule(this))
                .build()
    }
}
