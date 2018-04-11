package com.zjmzyx.monoceros.module.home.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.MenuItem
import android.view.View
import com.zjmzyx.monoceros.R
import com.zjmzyx.monoceros.databinding.HomeActivityBinding
import com.zjmzyx.monoceros.library.base.ui.activity.BaseActivity
import com.zjmzyx.monoceros.module.home.adapter.HomeFragmentPagerAdapter
import com.zjmzyx.monoceros.movies.module.home.fragment.MoviesListFragment

/**
 *
 * @author zjm
 * @date 2018/4/4
 */
class HomeActivity : BaseActivity() {

    private var binding: HomeActivityBinding? = null

    private var fragmentList: MutableList<Fragment> = ArrayList()
    private var titleList: MutableList<String> = ArrayList()

    private var fragmentPagerAdapter: HomeFragmentPagerAdapter? = null
    private var menuItem: MenuItem? = null

    override fun errorViewOnClick() {
    }

    override fun initView(savedInstanceState: Bundle?): View {
        val view = View.inflate(this, R.layout.home_activity, null)
        binding = DataBindingUtil.bind(view)
        initViewPager()
        return view
    }

    private fun initViewPager() {
        fragmentList.add(MoviesListFragment.newInstance())
        titleList.add(TAG_MOVIES)
        fragmentPagerAdapter = HomeFragmentPagerAdapter(supportFragmentManager,
                fragmentList, titleList)
        binding!!.vpHome.adapter = fragmentPagerAdapter
        binding!!.vpHome.clearOnPageChangeListeners()
        binding!!.vpHome.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float,
                                        positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (menuItem != null) {
                    menuItem!!.isChecked = false
                } else {
                    binding!!.bnbHome.menu.getItem(0).isChecked = false
                }
                menuItem = binding!!.bnbHome.menu.getItem(position)
                menuItem!!.isCheckable = true
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (successView == null) {
            setSuccessViewVisible()
        }
    }

    companion object {
        val TAG_MOVIES = "movies"
    }
}