package com.zjmzyx.monoceros.module.splash

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.orhanobut.logger.Logger
import com.zjmzyx.monoceros.R

import com.zjmzyx.monoceros.databinding.ActivitySplashBinding
import com.zjmzyx.monoceros.library.base.MyApplication
import com.zjmzyx.monoceros.library.base.ui.activity.BaseActivity
import com.zjmzyx.monoceros.library.util.BaseUtil
import com.zjmzyx.monoceros.library.util.UIUtils
import com.zjmzyx.monoceros.module.home.HomeActivity

import io.reactivex.disposables.CompositeDisposable

/**
 * @author zjm
 */
class SplashActivity : BaseActivity() {

    private var binding: ActivitySplashBinding? = null

    override fun errorViewOnClick() {

    }

    override fun initView(savedInstanceState: Bundle?): View {
        val view = View.inflate(this, R.layout.activity_splash, null)
        binding = DataBindingUtil.bind(view)
        initCover()
        initAnimation()
        return view
    }

    private fun initCover() {
        Logger.e("res://" + R.drawable.splash_cover)
        UIUtils.setSimpleDraweeView("res://" + BaseUtil.packageName + "/"
                + R.drawable.splash_cover, binding!!.sdvItem,
                UIUtils.screenWidth, UIUtils.screenHeight)
    }

    private fun initAnimation() {
        val animation = AlphaAnimation(0f, 1f)
        animation.duration = 2000
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                var intent=Intent(MyApplication.context,HomeActivity::class.java)
            }

        })
        binding!!.sdvItem.startAnimation(animation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (successView == null) {
            setSuccessViewVisible()
        }
    }
}
