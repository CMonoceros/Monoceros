package com.zjmzyx.monoceros.module.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zjmzyx.monoceros.library.base.ui.fragment.BaseDaggerFragment
import com.zjmzyx.monoceros.module.home.contract.MoviesListContract

/**
 * Created by zjm on 2018/4/6.
 */
class MoviesListFragment
    : BaseDaggerFragment(), MoviesListContract.View {

    override fun setupInjector() {
        myFragmentComponent!!.inject(this)
    }

    override fun errorViewOnClick() {

    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?,
                          savedInstanceState: Bundle?)
            : View {
        val view = super.onCreateView(inflater!!, container, savedInstanceState)
        return view!!
    }

    companion object {
        fun newInstance(): MoviesListFragment {
            return MoviesListFragment()
        }
    }
}