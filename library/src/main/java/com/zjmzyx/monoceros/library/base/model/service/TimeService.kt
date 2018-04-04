package com.zjmzyx.monoceros.library.base.model.service

import android.text.TextUtils
import com.orhanobut.logger.Logger
import com.zjmzyx.monoceros.cons.Constants
import com.zjmzyx.monoceros.library.base.model.bean.base.BaseBean
import com.zjmzyx.monoceros.library.util.ServiceUtil
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import java.util.concurrent.TimeUnit

/**
 * @author zjm
 * @date 2018/4/4
 */
class TimeService {

    private val retrofit: Retrofit

    init {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                            .build()
                    val response = chain.proceed(request)
                    Logger.i("请求 ： url：" + response.request().url())
                    val mediaType = response.body()!!.contentType()
                    val content = response.body()!!.string()
                    if (content.length < 1000 && ServiceUtil.PRINT_JSON) {
                        ServiceUtil.printJson(content)
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
                .baseUrl(Constants.TIME_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }
}
