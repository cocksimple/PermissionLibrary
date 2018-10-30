package com.guagua.live.permissionlibrary.lib

interface PermissionCallback {
    fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>)

    fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>)

}
