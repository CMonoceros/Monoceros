package com.zjmzyx.monoceros.library.test.idlingresource

import android.support.test.espresso.IdlingResource

import java.util.concurrent.atomic.AtomicInteger

/**
 *
 * @author zjm
 * @date 2017/11/24
 */

class RxCountingIdling(private val mResourceName: String) : IdlingResource {

    //这个counter值就像一个标记，默认为0
    private val counter = AtomicInteger(0)

    @Volatile private var resourceCallback: IdlingResource.ResourceCallback? = null

    val count: Int
        get() = counter.get()

    override fun getName(): String {
        return mResourceName
    }

    override fun isIdleNow(): Boolean {
        return counter.get() == 0
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.resourceCallback = resourceCallback
    }

    //每当我们开始异步请求，把counter值+1
    fun increment() {
        counter.getAndIncrement()
    }

    //当我们获取到网络数据后，counter值-1；
    fun decrement() {
        val counterVal = counter.decrementAndGet()
        //如果这时counter == 0，说明异步结束，执行回调。
        if (counterVal == 0) {
            if (null != resourceCallback) {
                resourceCallback!!.onTransitionToIdle()
            }
        }
        if (counterVal < 0) {
            //如果小于0，抛出异常
            throw IllegalArgumentException("Counter has been corrupted!")
        }
    }
}
