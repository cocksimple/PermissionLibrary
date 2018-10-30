package com.guagua.live.permissionlibrary.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.guagua.live.permissionlibrary.lib.PermissionManager

/**
 * 暂时搁置，如有需要后续添加
 */
class PermissionHelperActivity : Activity() {
    // 默认无效requestCode
    private val INVALID_REQUEST_CODE = -1

    private var mPendingRequestCode = INVALID_REQUEST_CODE

    companion object {
        // 需要请求的权限
        val EXTRA_PERMISSIONS = "permissions"
        // 请求权限的requestCode
        val EXTRA_PERMISSION_REQUEST_CODE = "request_code"
        //rationale
        val EXTRA_PERMISSION_RATIONALE = "rationale"
        //dialog data
        val EXTRA_SETTING_DIALOG_DATA = "setting_dialog_data"
        //Action
        val EXTRA_PERMISSION_ACTION = "permissin_action"
        val ACTION_REQUEST_PERMISSION = 0x1
        val ACTION_SHOW_SETTING_DIALOG = 0x1 shl 1
        val ACTION_SOME_PERMISSION_PERMANTLY_DENIED = 0x1 shl 2
        val ACTION_PERMISSION_PERMANTLY_DENIED = 0x1 shl 3

        fun requestPermissions(context: Context, rationale: String, requestCode: Int, vararg permissionStrings: String) {
            val intent = Intent(context.getApplicationContext(), PermissionHelperActivity::class.java)
            intent.putExtra(EXTRA_PERMISSION_REQUEST_CODE, requestCode)
            intent.putExtra(EXTRA_PERMISSION_RATIONALE, rationale)
            intent.putExtra(EXTRA_PERMISSIONS, permissionStrings)
            intent.putExtra(EXTRA_PERMISSION_ACTION, ACTION_REQUEST_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            context.startActivity(intent)
        }

//        fun showSettingDialog(context: Context, info: SettingDialogInfo) {
//            val intent = Intent(context.getApplicationContext(), PermissionHelperActivity::class.java)
//            intent.putExtra(EXTRA_SETTING_DIALOG_DATA, info)
//            intent.putExtra(EXTRA_PERMISSION_ACTION, ACTION_SHOW_SETTING_DIALOG)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
//            context.startActivity(intent)
//        }

//        fun permissionPermanentlyDenied(context: Context, vararg permissionStrings: String) {
//            val intent = Intent(context.getApplicationContext(), PermissionHelperActivity::class.java)
//            intent.putExtra(EXTRA_PERMISSIONS, permissionStrings)
//            intent.putExtra(EXTRA_PERMISSION_ACTION,
//                    if (permissionStrings.size == 1) ACTION_PERMISSION_PERMANTLY_DENIED
//                    else ACTION_SOME_PERMISSION_PERMANTLY_DENIED)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
//            context.startActivity(intent)
//        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPendingRequestCode = savedInstanceState?.getInt(EXTRA_PERMISSION_REQUEST_CODE, INVALID_REQUEST_CODE) ?: INVALID_REQUEST_CODE
        if (mPendingRequestCode == INVALID_REQUEST_CODE) {
            mPendingRequestCode++
            val action = intent.getIntExtra(EXTRA_PERMISSION_ACTION, 0)
            if ((action and ACTION_REQUEST_PERMISSION) == ACTION_REQUEST_PERMISSION) {
                requestPermission()
            }
            if ((action and ACTION_SHOW_SETTING_DIALOG) == ACTION_SHOW_SETTING_DIALOG) {
//                showSettingDialog()
            }
            if ((action and ACTION_SOME_PERMISSION_PERMANTLY_DENIED) == ACTION_SOME_PERMISSION_PERMANTLY_DENIED) {
                somePermissionPermanentlyDenied()
            }
            if ((action and ACTION_PERMISSION_PERMANTLY_DENIED) == ACTION_PERMISSION_PERMANTLY_DENIED) {
                permissionPermanentlyDenied()
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(EXTRA_PERMISSION_REQUEST_CODE, mPendingRequestCode)
    }

    fun requestPermission() {
        if (PermissionManager.somePermissionPermanentlyDenied(this, intent.getStringArrayExtra(EXTRA_PERMISSIONS).asList())) {
//            PermissionManager.showSettingDialog(this, intent.getStringArrayExtra(EXTRA_PERMISSIONS).asList())
        } else {
            PermissionManager.requestPermission(this,
                    intent.getStringExtra(EXTRA_PERMISSION_RATIONALE),
                    intent.getIntExtra(EXTRA_PERMISSION_REQUEST_CODE, 0),
                    false,
                    *intent.getStringArrayExtra(EXTRA_PERMISSIONS))
        }
    }


//    fun showSettingDialog() =
//            PermissionManager.showSettingDialog(this, intent.getParcelableExtra<SettingDialogInfo>(EXTRA_SETTING_DIALOG_DATA))


    fun somePermissionPermanentlyDenied() =
            PermissionManager.somePermissionPermanentlyDenied(this, intent.getStringArrayExtra(EXTRA_PERMISSIONS).asList())


    fun permissionPermanentlyDenied() =
            PermissionManager.permissionPermanentlyDenied(this, intent.getStringArrayExtra(EXTRA_PERMISSIONS)[0])


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mPendingRequestCode = INVALID_REQUEST_CODE
    }

}
