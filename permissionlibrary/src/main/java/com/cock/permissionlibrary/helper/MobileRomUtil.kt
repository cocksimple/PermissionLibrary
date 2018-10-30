package com.guagua.live.permissionlibrary.helper

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.Fragment
import java.io.BufferedReader
import java.io.InputStreamReader

object MobileRomUtil {
    private val REQUEST_SETTING = 12001
    private val MOBILE_HUSWEI = "Huawei"//华为
    private val MEIZU = "Meizu"//魅族
    private val MI = "mi"//小米
    private val SONY = "Sony"//索尼
    private val OPPO = "OPPO"//oppo
    private val LG = "LG"//LG
    private val VIVO = "vivo"//vivo
    private val SAMSUNG = "samsung"//三星
    private val LETV = "Letv"//乐视
    private val ZTE = "ZTE"//中兴
    private val YULONG = "YuLong"//酷派
    private val LENOVO = "LENOVO"//联想

    fun gotoAppPermission(activity: Activity) {
        gotoAppPermission(activity, REQUEST_SETTING)
    }

    fun gotoAppPermission(activity: Activity, requestCode: Int) {
        var locationIntent: Intent = getPermissionIntent(activity)
        try {
            activity.startActivityForResult(locationIntent, requestCode)
        } catch (ex: Exception) {
            ex.printStackTrace()
            activity.startActivityForResult(getAppSettingIntent(), requestCode)
        }
    }

    fun gotoAppPermission(fragment: Fragment) {
        gotoAppPermission(fragment, REQUEST_SETTING)
    }

    fun gotoAppPermission(fragment: Fragment, requestCode: Int) {
        var locationIntent: Intent = getPermissionIntent(fragment.activity!!)
        try {
            fragment.startActivityForResult(locationIntent, requestCode)
        } catch (ex: Exception) {
            ex.printStackTrace()
            fragment.startActivityForResult(getAppSettingIntent(), requestCode)
        }
    }
    fun gotoAppPermission(fragment: android.app.Fragment){
        gotoAppPermission(fragment, REQUEST_SETTING)
    }
    fun gotoAppPermission(fragment: android.app.Fragment, requestCode: Int) {
        var locationIntent: Intent = getPermissionIntent(fragment.activity!!)
        try {
            fragment.startActivityForResult(locationIntent, requestCode)
        } catch (ex: Exception) {
            ex.printStackTrace()
            fragment.startActivityForResult(getAppSettingIntent(), requestCode)
        }
    }

    private fun getPermissionIntent(context: Context): Intent {
        var locationIntent: Intent? = null
        val brand = Build.BRAND
        when {
            brand.contains(MOBILE_HUSWEI) -> locationIntent = getHuaweiPermissionIntent(context)
            brand.contains(MEIZU) -> locationIntent = getMeizuPermissionIntent(context)
            brand.contains(MI) -> locationIntent = getMiuiPermissionIntent(context)
            brand.contains(SONY) -> locationIntent = getSonyPermissionIntent(context)
            brand.contains(OPPO) -> locationIntent = getOppoPermissionIntent(context)
            brand.contains(LG) -> locationIntent = getLGPermissionIntent(context)
            brand.contains(LETV) -> locationIntent = getLetvPermissionIntent(context)
            else -> locationIntent = getAppDetailSettingIntent(context)
        }
        return locationIntent ?: getAppDetailSettingIntent(context)
    }

    /**
     * 跳转到miui的权限管理页面
     */
    private fun getMiuiPermissionIntent(context: Context): Intent {
        val rom = getMiuiVersion()
        var intent: Intent? = null
        if (rom.contains("5")) {
            val packageURI = Uri.parse("package:" + context.getApplicationInfo().packageName);
            intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI)
        } else if (rom.contains("6") || rom.contains("7")) {
            intent = Intent("miui.intent.action.APP_PERM_EDITOR")
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity")
            intent.putExtra("extra_pkgname", context.getPackageName())
        } else if (rom.contains("8")) {
            intent = Intent("miui.intent.action.APP_PERM_EDITOR")
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity")
            intent.putExtra("extra_pkgname", context.getPackageName())
        } else {
            intent = Intent("miui.intent.action.APP_PERM_EDITOR")
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity")
            intent.putExtra("extra_pkgname", context.getPackageName())
        }
        return intent
    }

    private fun getMiuiVersion(): String {
        val miuiName = "ro.miui.ui.version.name"
        var line: String? = null
        var input: BufferedReader? = null
        try {
            val process = Runtime.getRuntime().exec("getprop " + miuiName)
            input = BufferedReader(InputStreamReader(process.inputStream), 1024)
            line = input.readLine()
        } catch (ex: Exception) {
            return ""
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
        return line ?: ""
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private fun getMeizuPermissionIntent(context: Context): Intent {
        val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.putExtra("packageName", context.packageName)
        return intent
    }

    /**
     * 华为的权限管理页面
     */
    private fun getHuaweiPermissionIntent(context: Context): Intent {
        val intent = Intent(context.packageName)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val compontName = ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity")
        intent.setComponent(compontName)
        return intent
    }

    /**
     * 索尼的权限管理界面
     */
    private fun getSonyPermissionIntent(context: Context): Intent {
        val intent = Intent()
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("packageName", context.packageName)
        val comp = ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity")
        intent.setComponent(comp)
        return intent
    }

    /**
     * OPPO的权限管理界面
     */
    private fun getOppoPermissionIntent(context: Context): Intent {
        val intent = Intent()
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("packageName", context.packageName)
        val comp = ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity")
        intent.setComponent(comp)
        return intent
    }

    /**
     * LG的权限管理界面
     */
    private fun getLGPermissionIntent(context: Context): Intent {
        val intent = Intent("android.intent.action.MAIN")
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("packageName", context.packageName)
        val comp = ComponentName("com.android.settings", "com.android.settings.Settings\$AccessLockSummaryActivity")
        intent.setComponent(comp)
        return intent
    }

    /**
     * 乐视的权限管理界面
     */
    private fun getLetvPermissionIntent(context: Context): Intent {
        val intent = Intent()
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("packageName", context.packageName)
        val comp = ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps")
        intent.setComponent(comp)
        return intent
    }

    /**
     * 360的权限管理界面
     */
    private fun getQihooPermissionIntent(context: Context): Intent {
        val intent = Intent("android.intent.action.MAIN")
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("packageName", context.packageName)
        val comp = ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity")
        intent.setComponent(comp)
        return intent
    }

    /**
     * 系统应用详情页面
     *
     * @return
     */
    private fun getAppDetailSettingIntent(context: Context): Intent {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SE0TTINGS")
            intent.setData(Uri.fromParts("package", context.getPackageName(), null))
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.FROYO) {
            intent.setAction(Intent.ACTION_VIEW)
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails")
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName())
        }
        return intent
    }

    private fun getAppSettingIntent() = Intent(Settings.ACTION_SETTINGS)

}