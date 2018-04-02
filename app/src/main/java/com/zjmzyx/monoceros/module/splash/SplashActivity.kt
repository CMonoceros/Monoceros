package com.zjmzyx.monoceros.module.splash

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager

import com.zjmzyx.monoceros.R
import com.zjmzyx.monoceros.databinding.ActivitySplashBinding
import com.zjmzyx.monoceros.library.base.ui.activity.BaseActivity

import javax.inject.Inject

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * @author zjm
 */
class SplashActivity : BaseActivity() {

    private var compositeDisposable: CompositeDisposable? = null
    private var binding: ActivitySplashBinding? = null

    override fun errorViewOnClick() {

    }

    override fun initView(savedInstanceState: Bundle?): View {
        val view = View.inflate(this, R.layout.activity_splash, null)
        binding = DataBindingUtil.bind(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (successView == null) {
            setSuccessViewVisible()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (compositeDisposable != null) {
            compositeDisposable!!.dispose()
        }
    }
}
