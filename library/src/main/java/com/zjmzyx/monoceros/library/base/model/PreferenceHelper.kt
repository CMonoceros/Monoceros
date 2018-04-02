package com.zjmzyx.monoceros.library.base.model

import android.content.Context
import android.content.SharedPreferences

import com.zjmzyx.monoceros.library.base.MyApplication


/**
 *
 * @author zjm
 * @date 2017/12/13
 */

class PreferenceHelper {

    private val SharedPreferencesName = "config"

    private val sharedPreferences: SharedPreferences
        get() = MyApplication.context!!
                .getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE)

    private val sharedPreferencesEditor: SharedPreferences.Editor
        get() = sharedPreferences.edit()
}
