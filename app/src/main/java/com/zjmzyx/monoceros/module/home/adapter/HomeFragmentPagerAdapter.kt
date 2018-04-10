package com.zjmzyx.monoceros.module.home.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by zjm on 2018/4/6.
 */
class HomeFragmentPagerAdapter(private var fm: FragmentManager,
                               private var fragmentList: MutableList<Fragment>,
                               private var titleList: MutableList<String>)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
}