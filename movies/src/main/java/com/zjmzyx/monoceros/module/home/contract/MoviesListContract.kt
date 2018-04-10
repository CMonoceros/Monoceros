package com.zjmzyx.monoceros.module.home.contract

import com.zjmzyx.monoceros.library.base.presenter.BasePresenter
import com.zjmzyx.monoceros.library.base.ui.BaseView

/**
 * Created by zjm on 2018/4/10.
 */
interface MoviesListContract {
    interface View : BaseView {

    }

    interface Presenter : BasePresenter<MoviesListContract.View> {

    }
}