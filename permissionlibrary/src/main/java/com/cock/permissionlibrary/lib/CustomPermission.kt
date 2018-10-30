package com.guagua.live.permissionlibrary.lib

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment

class CustomPermission(var mCallBack: PermissionCallback) : IPermissionUtil {
    override fun hasPermission(context: Context?, vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(context!!, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    override fun requestPermissions(activity: Activity, rationale: String, requestCode: Int, vararg permissions: String) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    override fun requestPermissions(fragment: Fragment, rationale: String, requestCode: Int, vararg permissions: String) {
        fragment.requestPermissions(permissions, requestCode)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun requestPermissions(fragment: android.app.Fragment, rationale: String, requestCode: Int, vararg permissions: String) {
        fragment.requestPermissions(permissions, requestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        var granted = true
        val deniedPermission = mutableListOf<String>()
        for (i in 0 until permissions.size) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                granted = false
                deniedPermission.add(permissions[i])
            }
        }
        if (granted) {
            mCallBack.onPermissionsGranted(requestCode, permissions.toMutableList())
        } else {
            mCallBack.onPermissionsDenied(requestCode, deniedPermission)
        }
    }

    override fun somePermissionPermanentlyDenied(host: Activity, deniedPermissions: List<String>): Boolean {
        deniedPermissions.forEach {
            if (ActivityCompat.shouldShowRequestPermissionRationale(host, it)) {
                return true
            }
        }
        return false
    }

    override fun somePermissionPermanentlyDenied(host: Fragment, deniedPermissions: List<String>): Boolean {
        deniedPermissions.forEach {
            if (host.shouldShowRequestPermissionRationale(it)) {
                return true
            }
        }
        return false
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun somePermissionPermanentlyDenied(host: android.app.Fragment, deniedPermissions: List<String>): Boolean {
        deniedPermissions.forEach {
            if (host.shouldShowRequestPermissionRationale(it)) {
                return true
            }
        }
        return false
    }

    override fun permissionPermanentlyDenied(host: Activity, deniedPermission: String): Boolean {
        if (ActivityCompat.shouldShowRequestPermissionRationale(host, deniedPermission)) {
            return true
        }
        return false
    }

    override fun permissionPermanentlyDenied(host: Fragment, deniedPermission: String): Boolean {
        host.activity?.let {
            if (host.shouldShowRequestPermissionRationale(deniedPermission)) {
                return true
            }
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun permissionPermanentlyDenied(host: android.app.Fragment, deniedPermission: String): Boolean {
        host.activity?.let {
            if (host.shouldShowRequestPermissionRationale(deniedPermission)) {
                return true
            }
        }
        return false
    }

    override fun somePermissionDenied(host: Activity, vararg perms: String): Boolean {
        return false
    }

    override fun somePermissionDenied(host: Fragment, vararg perms: String): Boolean {
        return false
    }

    override fun somePermissionDenied(host: android.app.Fragment, vararg perms: String): Boolean {
        return false
    }

}