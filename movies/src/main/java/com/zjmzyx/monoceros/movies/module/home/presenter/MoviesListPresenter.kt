package com.zjmzyx.monoceros.movies.module.home.presenter

import com.zjmzyx.monoceros.movies.module.home.contract.MoviesListContract
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by zjm on 2018/4/10.
 */
class MoviesListPresenter @Inject constructor()
    : MoviesListContract.Presenter {

    private var myView: MoviesListContract.View? = null
    private var compositeDisposable: CompositeDisposable? = null
    private var page = 1
    private var canLoadMore = true

    override fun attachView(baseView: MoviesListContract.View) {
        myView = baseView
        compositeDisposable = CompositeDisposable()
    }

    override fun detachView() {
        if (compositeDisposable != null) {
            compositeDisposable!!.dispose()
        }
    }
}