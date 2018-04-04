package com.zjmzyx.monoceros.module.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.zjmzyx.monoceros.R
import com.zjmzyx.monoceros.library.base.ui.activity.BaseActivity

/**
 *
 * @author zjm
 * @date 2018/4/4
 */
class HomeActivity : BaseActivity() {
    override fun errorViewOnClick() {
    }

    override fun initView(savedInstanceState: Bundle?): View {
        val view = View.inflate(this, R.layout.activity_splash, null)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (successView == null) {
            setSuccessViewVisible()
        }
    }
}