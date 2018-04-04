package com.zjmzyx.monoceros.library.util

import android.text.TextUtils
import com.orhanobut.logger.Logger
import com.zjmzyx.monoceros.library.base.model.bean.base.BaseBean
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

/**
 *
 * @author zjm
 * @date 2018/4/4
 */
object ServiceUtil {

    val PRINT_JSON = true

    //切换线程
    fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun checkQueryMap(queryMap: MutableMap<String, String>): Map<String, String> {
        val removeList = ArrayList<String>()
        for ((key, value) in queryMap) {
            if (value == null) {
                removeList.add(key)
            }
        }
        for (key in removeList) {
            queryMap.remove(key)
        }
        return queryMap
    }

    fun onError(error: String?) {
        Logger.e(error)
    }

    fun reportCrash(error: String?) {
        Logger.e(error)
    }

    fun onError(error: String?, msg: String?) {
        Logger.e(error)
    }

    //初步过滤
    fun <T> applyBaseBeanFilter(): ObservableTransformer<BaseBean<T>, BaseBean<T>> {
        return ObservableTransformer { upstream ->
            upstream.filter {
                true
            }
        }
    }

    fun <T> applyBeanConvert(clazz: Class<*>, vararg types: String): ObservableTransformer<BaseBean<T>, BaseBean<T>> {
        return ObservableTransformer { upstream ->
            upstream.map { objectBaseBean ->
                objectBaseBean
            }
        }
    }

    //logger的Logger.d方法在2.1.1版本有bug，故重写该方法
    fun printJson(json: String) {
        if (TextUtils.isEmpty(json)) {
            Logger.i("Empty/Null json content")
        } else {
            try {
                val message: String
                if (json.startsWith("{")) {
                    val jsonObject = JSONObject(json)
                    message = jsonObject.toString(2)
                    Logger.i(message)
                    return
                }

                if (json.startsWith("[")) {
                    val jsonArray = JSONArray(json)
                    message = jsonArray.toString(2)
                    Logger.i(message)
                    return
                }
                Logger.e("Invalid Json", *arrayOfNulls(0))
            } catch (var4: JSONException) {
                Logger.e("Invalid Json", *arrayOfNulls(0))
            }

        }
    }
}