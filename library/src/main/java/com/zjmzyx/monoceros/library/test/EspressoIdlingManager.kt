package com.zjmzyx.monoceros.library.test

import android.support.test.espresso.IdlingResource

import com.zjmzyx.monoceros.library.test.idlingresource.RxCountingIdling


/**
 *
 * @author zjm
 * @date 2017/11/24
 */

object EspressoIdlingManager {

    private val RESOURCE = "Rx"

    private val mCountingIdlingResource = RxCountingIdling(RESOURCE)

    val idlingResource: IdlingResource
        get() = mCountingIdlingResource

    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        mCountingIdlingResource.decrement()
    }
}
