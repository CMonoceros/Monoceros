package com.zjmzyx.monoceros.library.base.presenter


import com.zjmzyx.monoceros.library.base.ui.BaseView

/**
 *
 * @author zjm
 * @date 2017/8/29
 */

interface BasePresenter<in T : BaseView> {

    fun attachView(baseView: T)

    fun detachView()
}
