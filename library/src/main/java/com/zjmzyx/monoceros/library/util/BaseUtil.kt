package com.zjmzyx.monoceros.library.util

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.text.TextUtils

import com.orhanobut.logger.Logger
import com.zjmzyx.monoceros.library.base.MyApplication

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

/**
 *
 * @author zjm
 * @date 2017/8/29
 */

object BaseUtil {

    val applicationResources: Resources
        get() = MyApplication.context!!.resources

    val packageName: String
        get() = MyApplication.context!!.packageName

    //we make this look like a valid IMEI
    val deviceId: String
        get() {
            val deviceId = "35" +
                    Build.BOARD.length % 10 +
                    Build.BRAND.length % 10 +
                    Build.CPU_ABI.length % 10 +
                    Build.DEVICE.length % 10 +
                    Build.DISPLAY.length % 10 +
                    Build.HOST.length % 10 +
                    Build.ID.length % 10 +
                    Build.MANUFACTURER.length % 10 +
                    Build.MODEL.length % 10 +
                    Build.PRODUCT.length % 10 +
                    Build.TAGS.length % 10 +
                    Build.TYPE.length % 10 +
                    Build.USER.length % 10
            Logger.i("DeviceId:" + deviceId)
            return deviceId
        }

    /**
     * 获取手机品牌
     *
     * @return
     */
    val phoneBrand: String
        get() = Build.BRAND

    /**
     * 获取手机型号
     *
     * @return
     */
    val phoneModel: String
        get() = Build.MODEL

    /**
     * 获取手机Android API等级（22、23 ...）
     *
     * @return
     */
    val buildLevel: Int
        get() = Build.VERSION.SDK_INT

    fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                if (reader != null) {
                    reader.close()
                }
            } catch (exception: IOException) {
                exception.printStackTrace()
            }

        }
        return null
    }

    fun getMetaData(context: Context, key: String): String? {
        try {
            val appInfo = context.packageManager.getApplicationInfo(
                    context.packageName, PackageManager.GET_META_DATA)
            if (appInfo.metaData != null) {
                return appInfo.metaData.getString(key)
            }
        } catch (ignored: PackageManager.NameNotFoundException) {

        }

        return null
    }

    fun getMaxElem(arr: IntArray): Int {
        var maxVal = Integer.MIN_VALUE
        for (anArr in arr) {
            if (anArr > maxVal) {
                maxVal = anArr
            }
        }
        return maxVal
    }

    fun getApplicationString(resId: Int): String {
        return applicationResources.getString(resId)
    }

    fun getApplicationStrings(resId: Int): Array<String> {
        return applicationResources.getStringArray(resId)
    }

    /**
     * 判断包是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    fun isInstalled(context: Context, packageName: String): Boolean {
        val manager = context.packageManager
        try {
            manager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)

            return true
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }

    }

    /**
     * 安装应用程序
     *
     * @param context
     * @param apkFile
     */
    fun installApp(context: Context, apkFile: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
        context.startActivity(intent)
    }

    /**
     * 打开应用程序
     *
     * @param context
     * @param packageName
     */
    fun openApp(context: Context, packageName: String) {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)


    }

    /**
     * get App versionCode
     *
     * @param context
     * @return
     */
    fun getVersionCode(context: Context): String {
        val packageManager = context.packageManager
        val packageInfo: PackageInfo
        var versionCode = ""
        try {
            packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            versionCode = packageInfo.versionCode.toString() + ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return versionCode
    }

    /**
     * get App versionName
     *
     * @param context
     * @return
     */
    fun getVersionName(context: Context): String {
        val packageManager = context.packageManager
        val packageInfo: PackageInfo
        var versionName = ""
        try {
            packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            versionName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return versionName
    }
}
