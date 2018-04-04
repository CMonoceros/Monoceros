package com.zjmzyx.monoceros.library.base.model

import com.zjmzyx.monoceros.library.base.model.service.TimeService


/**
 * @author zjm
 */
class DataManager private constructor() {

    val timeService: TimeService = TimeService()
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
