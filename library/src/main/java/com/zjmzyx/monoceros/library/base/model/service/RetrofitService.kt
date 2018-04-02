package com.zjmzyx.monoceros.library.base.model.service

import android.os.Build
import android.text.TextUtils

import com.orhanobut.logger.Logger
import com.zjmzyx.monoceros.cons.Constants
import com.zjmzyx.monoceros.library.base.MyApplication
import com.zjmzyx.monoceros.library.base.model.bean.base.BaseBean
import com.zjmzyx.monoceros.library.util.BaseUtil

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.util.ArrayList
import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author zjm
 * @date 2017/12/13
 */

class RetrofitService {

    private val retrofit: Retrofit

    init {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                            .addHeader("User-Agent",
                                    BaseUtil.getVersionName(MyApplication.context!!) + "/" +
                                            BaseUtil.getVersionCode(MyApplication.context!!) +
                                            " (Android:" + Build.VERSION.SDK_INT + ")")
                            .build()
                    val response = chain.proceed(request)
                    Logger.i("请求 ： url：" + response.request().url())
                    val mediaType = response.body()!!.contentType()
                    val content = response.body()!!.string()
                    if (content.length < 1000 && PRINT_JSON) {
                        printJson(content)
                    } else {
                        Logger.i("响应 ： 已获得服务器返回值")
                    }
                    response.newBuilder()
                            .body(ResponseBody.create(mediaType, content))
                            .build()
                }
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()
        retrofit = Retrofit.Builder()
                .baseUrl(Constants.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    //logger的Logger.d方法在2.1.1版本有bug，故重写该方法
    private fun printJson(json: String) {
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

    companion object {

        private val PRINT_JSON = true

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
                    //                        if (baseBean.getErrcode() != null && baseBean.getMsg() != null &&
                    //                                "0".equals(baseBean.getErrcode()) && "ok".equals(baseBean.getMsg())) {
                    //                            return true;
                    //                        } else {
                    //                            onError(baseBean.getErrcode() + " " + baseBean.getMsg(), baseBean.getMsg());
                    //                            return false;
                    //                        }
                    false
                }
            }
        }

        fun <T> applyBeanConvert(clazz: Class<*>, vararg types: String): ObservableTransformer<BaseBean<T>, BaseBean<T>> {
            return ObservableTransformer { upstream ->
                upstream.map { objectBaseBean ->
                    //                        if (objectBaseBean.getData().getClass() == clazz) {
                    //                            if (clazz == ArrayList.class) {
                    //                                List<Object> list = (List<Object>) objectBaseBean.getData();
                    //                                for (Object object : list) {
                    //                                    if (object instanceof BaseBeanConvert) {
                    //                                        object = ((BaseBeanConvert) object)
                    //                                                .convert(object, types);
                    //                                    }
                    //                                }
                    //                                objectBaseBean.setData((T) list);
                    //                            } else if (objectBaseBean.getData() instanceof BaseBeanConvert) {
                    //                                Object object = ((BaseBeanConvert) objectBaseBean.getData())
                    //                                        .convert(objectBaseBean.getData(), types);
                    //                                objectBaseBean.setData((T) object);
                    //                            }
                    //                        }
                    objectBaseBean
                }
            }
        }
    }
}
