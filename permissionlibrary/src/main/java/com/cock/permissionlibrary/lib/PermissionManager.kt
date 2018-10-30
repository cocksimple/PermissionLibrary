package com.guagua.live.permissionlibrary.lib


import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.v4.app.Fragment
import android.util.SparseArray
import com.cock.permissionlibrary.lib.LowerPermissionUtil
import com.guagua.live.permissionlibrary.helper.PermissionHelperActivity

/**
 * 权限框架，6.0以上走easyPermission的逻辑，
 * 6.0以下查询权限走packageManager、ActivityCompat、AppOpsManager,没有权限弹出dialog后直接跳入设置界面
 */
object PermissionManager : PermissionCallback {

    // 权限回调监听
    private val mRequestIdToCallback = SparseArray<PermissionCallback>()

    private val mRequestIdToPermissions = SparseArray<MutableList<String>>()

    private val mPermissionProduct: IPermissionUtil by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) CustomPermission(this)
        else LowerPermissionUtil(this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        val callback = mRequestIdToCallback.get(requestCode)
        callback?.let {
            callback.onPermissionsDenied(requestCode, perms)
            mRequestIdToCallback.remove(requestCode)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        val callback = mRequestIdToCallback.get(requestCode)
        callback?.let {
            val permissions = mRequestIdToPermissions.get(requestCode)
            if (perms.size == permissions.size) {
                callback.onPermissionsGranted(requestCode, perms)
            } else {
                permissions.removeAll(perms)
                callback.onPermissionsDenied(requestCode, permissions)
            }
            mRequestIdToCallback.remove(requestCode)
        }
    }

    fun hasPermission(context: Context, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return true
        }
        return mPermissionProduct.hasPermission(context, *permissions)
    }

    /**
     * 请求权限
     * @param host 传入的主体，传入的主题如果是android.app.Activity
     * 或android.support.v4.app.Fragment
     * 或android.app.Fragment中的任意一个会使用传入的主体进行请求
     * 如果是android.content.Context则会跳转PermissionHelperActivity进行请求
     *
     * 如果需要回调主体需要实现PermissionCallback
     */
    fun requestPermission(callback: PermissionCallback, host: Any, rationale: String, requestCode: Int, vararg permissions: String) {
        mRequestIdToCallback.put(requestCode, callback)
        requestPermission(host, rationale, requestCode, true, *permissions)
    }

    fun requestPermission(host: Any, rationale: String, requestCode: Int, vararg permissions: String) {
        requestPermission(host, rationale, requestCode, true, *permissions)
    }

    fun requestPermission(host: Any, rationale: String, requestCode: Int, callbackAble: Boolean, vararg permissions: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return
        }
        var permissionGroup = permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            permissionGroup = PermissionDangerousStack.getPermissionGroup(*permissions)
        }
        if (host == null) {
            return
        }
        if (callbackAble && host is PermissionCallback) {
            mRequestIdToCallback.put(requestCode, host)
        }
        mRequestIdToPermissions.put(requestCode, permissions.toMutableList())
        when (host) {
            is Activity ->
                if (!mPermissionProduct.hasPermission(host, *permissions))
                    mPermissionProduct.requestPermissions(host, rationale, requestCode, *permissionGroup)
            is Fragment ->
                if (!mPermissionProduct.hasPermission(host.activity, *permissions))
                    mPermissionProduct.requestPermissions(host, rationale, requestCode, *permissionGroup)
            is android.app.Fragment ->
                if (!mPermissionProduct.hasPermission(host.activity, *permissions))
                    mPermissionProduct.requestPermissions(host, rationale, requestCode, *permissionGroup)
            is Context ->
                if (!mPermissionProduct.hasPermission(host, *permissions))
                    PermissionHelperActivity.requestPermissions(host, rationale, requestCode, *permissionGroup)
        }
    }

    /**
     * 查询某些权限是否被设置为：永久拒绝访问（user clicked "Never ask again"）
     * @param host 传入的主体，暂时只能是android.app.Activity
     * 或android.support.v4.app.Fragment
     * 或android.app.Fragment中的任意一个
     */
    fun somePermissionPermanentlyDenied(host: Any, deniedPermissions: List<String>): Boolean {
        var result = false
        when (host) {
            is Activity -> result = mPermissionProduct.somePermissionPermanentlyDenied(host, deniedPermissions)
            is Fragment -> result = mPermissionProduct.somePermissionPermanentlyDenied(host, deniedPermissions)
            is android.app.Fragment -> result = mPermissionProduct.somePermissionPermanentlyDenied(host, deniedPermissions)
//            is Context -> PermissionHelperActivity.permissionPermanentlyDenied(host, deniedPermissions.toTypedArray())
        }
        return result
    }

    /**
     * 查询某些权限是否被设置为：永久拒绝访问（user clicked "Never ask again"）
     * @param host 传入的主体，暂时只能是android.app.Activity
     * 或android.support.v4.app.Fragment
     * 或android.app.Fragment中的任意一个
     */
    fun permissionPermanentlyDenied(host: Any, deniedPermission: String): Boolean {
        var result = false
        when (host) {
            is Activity -> result = mPermissionProduct.permissionPermanentlyDenied(host, deniedPermission)
            is Fragment -> result = mPermissionProduct.permissionPermanentlyDenied(host, deniedPermission)
            is android.app.Fragment -> result = mPermissionProduct.permissionPermanentlyDenied(host, deniedPermission)
        //            is Context -> PermissionHelperActivity.permissionPermanentlyDenied(host, deniedPermissions.toTypedArray())
        }
        return result
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) =
            mPermissionProduct.onRequestPermissionsResult(requestCode, permissions, grantResults)
}