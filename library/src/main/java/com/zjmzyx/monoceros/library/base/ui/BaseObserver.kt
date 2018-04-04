package com.zjmzyx.monoceros.library.base.ui


import com.zjmzyx.monoceros.library.test.EspressoIdlingManager
import com.zjmzyx.monoceros.library.util.ServiceUtil

import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 *
 * @author zjm
 * @date 2017/11/24
 */

abstract class BaseObserver<T> : Observer<T> {

    private var compositeDisposable: CompositeDisposable? = null

    constructor() {
        EspressoIdlingManager.increment()
    }

    constructor(compositeDisposable: CompositeDisposable) {
        EspressoIdlingManager.increment()
        this.compositeDisposable = compositeDisposable
    }

    abstract fun onBaseComplete()

    override fun onSubscribe(disposable: Disposable) {
        compositeDisposable?.add(disposable)
    }

    override fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        ServiceUtil.onError(throwable.message)
    }

    override fun onComplete() {
        onBaseComplete()
        if (!EspressoIdlingManager.idlingResource.isIdleNow()) {
            EspressoIdlingManager.decrement()
        }
    }
}
