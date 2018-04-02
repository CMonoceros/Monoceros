package com.zjmzyx.monoceros.library.base.model


import com.zjmzyx.monoceros.library.base.model.service.RetrofitService

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function

/**
 * @author zjm
 */
class DataManager private constructor() {

    val retrofitService: RetrofitService = RetrofitService()
    val preferenceHelper: PreferenceHelper = PreferenceHelper()

    companion object {

        @Volatile private var dataManager: DataManager? = null

        val instance: DataManager?
            get() {
                if (dataManager == null) {
                    synchronized(DataManager::class.java) {
                        if (dataManager == null) {
                            dataManager = DataManager()
                        }
                    }
                }
                return dataManager
            }
    }
}
