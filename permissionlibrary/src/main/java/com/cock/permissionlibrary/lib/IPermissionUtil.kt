package com.guagua.live.permissionlibrary.lib


import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment

/**
 * 此模板为easypermission的模板，各个方法用途可以查阅easypermission的方法说明
 */
interface IPermissionUtil {
    /**
     * 是否已授权permissions权限
     */
    fun hasPermission(context: Context?, vararg permissions: String): Boolean

    /**
     * 申请权限
     * @param 第一个参数为主体
     * @param rationale 申请权限之前的dialog内容
     * @param requestCode 访问标记，identifier
     * @param permission 权限列表
     */
    fun requestPermissions(activity: Activity, rationale: String, requestCode: Int, vararg permissions: String)

    fun requestPermissions(fragment: Fragment, rationale: String, requestCode: Int, vararg permissions: String)
    fun requestPermissions(fragment: android.app.Fragment, rationale: String, requestCode: Int, vararg permissions: String)

    /**
     * 权限申请回调
     * 只需在ActivityCompat.OnRequestPermissionsResultCallback或Fragment的onRequestPermissionsResult中实现此方法即可
     */
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)

    /**
     * 查询permission数组（@code deniedPermissions）权限是否被永久拒绝（勾选不再询问后点击拒绝）
     */
    fun somePermissionPermanentlyDenied(host: Activity, deniedPermissions: List<String>): Boolean

    fun somePermissionPermanentlyDenied(host: Fragment, deniedPermissions: List<String>): Boolean
    fun somePermissionPermanentlyDenied(host: android.app.Fragment, deniedPermissions: List<String>): Boolean

    /**
     * 查询单个permission（@code deniedPermission）权限是否被永久拒绝（勾选不再询问后点击拒绝）
     */
    fun permissionPermanentlyDenied(host: Activity, deniedPermission: String): Boolean

    fun permissionPermanentlyDenied(host: Fragment, deniedPermission: String): Boolean
    fun permissionPermanentlyDenied(host: android.app.Fragment, deniedPermission: String): Boolean

    /**
     * see if some denied permission has been permanently denied (@code perms)，
     * if true should show a rationale,false otherwise
     */
    fun somePermissionDenied(host: Activity, vararg perms: String): Boolean

    fun somePermissionDenied(host: Fragment, vararg perms: String): Boolean
    fun somePermissionDenied(host: android.app.Fragment, vararg perms: String): Boolean
}