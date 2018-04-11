package com.zjmzyx.monoceros.movies.module.home.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zjmzyx.monoceros.R
import com.zjmzyx.monoceros.databinding.MoviesHomeFragmentListBinding
import com.zjmzyx.monoceros.movies.base.ui.fragment.BaseDaggerFragment
import com.zjmzyx.monoceros.movies.module.home.contract.MoviesListContract
import com.zjmzyx.monoceros.movies.module.home.presenter.MoviesListPresenter
import javax.inject.Inject

/**
 * Created by zjm on 2018/4/6.
 */
class MoviesListFragment
    : BaseDaggerFragment(), MoviesListContract.View {

    @Inject
    lateinit var moviesListPresenter: MoviesListPresenter

    private var binding: MoviesHomeFragmentListBinding? = null

    override fun setupInjector() {
        myFragmentComponent!!.inject(this)
    }

    override fun errorViewOnClick() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?)
            : View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if (moviesListPresenter != null) {
            moviesListPresenter.attachView(this)
        }
        if (successView == null) {
            setLoadingViewVisible()
            setSuccessViewVisible()
        }
        return view
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?,
                          savedInstanceState: Bundle?)
            : View {
        val view = View.inflate(activity, R.layout.movies_home_fragment_list, null)
        binding = DataBindingUtil.bind(view)
        return view!!
    }

    companion object {
        fun newInstance(): MoviesListFragment {
            return MoviesListFragment()
        }
    }
}