package com.cock.permissionlibrary.lib;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.guagua.live.permissionlibrary.lib.IPermissionUtil;
import com.guagua.live.permissionlibrary.lib.PermissionCallback;
import com.guagua.live.permissionlibrary.lib.PermissionOps;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.List;

public class LowerPermissionUtil implements IPermissionUtil {
    private final PermissionCallback mCallBack;

    public LowerPermissionUtil(PermissionCallback callBack) {
        mCallBack = callBack;
    }

    /**
     * 部分手机会在查询后，如果没有该权限自动申请权限
     *
     * @param context
     * @param permissions
     * @return
     */
    @Override
    public boolean hasPermission(@Nullable Context context, @NotNull String... permissions) {
        if (context == null) {
            return false;
        }
        for (String permission : permissions) {
            if (!checkSelfPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSelfPermission(Context context, String permission) {
        return (context.getPackageManager().checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) &&
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) &&
                (checkAppOpsPermission(context, permission) == PackageManager.PERMISSION_GRANTED);
    }

    private int checkAppOpsPermission(Context context, String perm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int op = -1;
            AppOpsManager appOpsManager = (AppOpsManager) context.
                    getSystemService(Context.APP_OPS_SERVICE);
            if (appOpsManager != null) {
                try {
                    op = PermissionOps.INSTANCE.permissionToOp(perm);
                    Method method = AppOpsManager.class.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                    int property = (int) method.invoke(appOpsManager, op, Binder.getCallingUid(), context.getPackageName());
                    if (property == AppOpsManager.MODE_ALLOWED) {
                        return PackageManager.PERMISSION_GRANTED;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return PackageManager.PERMISSION_GRANTED;
                }
            }
        }
        return PackageManager.PERMISSION_DENIED;
    }

    // 弹出dialog确认是否跳转授权，点击确定后跳转设置界面，使用onActivityResult接收授权回调
    @Override
    public void requestPermissions(@NotNull final Activity activity, @NotNull String rationale, final int requestCode, @NotNull final String... permission) {
    }

    @Override
    public void requestPermissions(@NotNull final Fragment fragment, @NotNull String rationale, final int requestCode, @NotNull final String... permission) {
    }

    @Override
    public void requestPermissions(@NotNull final android.app.Fragment fragment, @NotNull String rationale, final int requestCode, @NotNull final String... permission) {
//        LowerRationaleDialog dialog = showDialog(fragment.getActivity(), rationale, requestCode, permission);
//        dialog.setClickListener(new Dialog.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (which == Dialog.BUTTON_POSITIVE) {
//                    MobileRomUtil.INSTANCE.gotoAppPermission(fragment, requestCode);
//                } else {
//                    if (mCallBack != null) {
//                        mCallBack.onPermissionsDenied(requestCode, Arrays.asList(permission));
//                    }
//                }
//            }
//        });
//        android.app.Fragment mFragment = fragment.getFragmentManager().findFragmentByTag(LowerRationaleDialog.Companion.getTAG());
//        if (mFragment instanceof LowerRationaleDialog) {
//            return;
//        }
//        dialog.showAllowingStateLoss(fragment.getFragmentManager(), LowerRationaleDialog.Companion.getTAG());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        //6.0以下无此方法
    }

    @Override
    public boolean somePermissionPermanentlyDenied(@NotNull Activity host, @NotNull List<String> deniedPermissions) {

        return false;
    }

    @Override
    public boolean somePermissionPermanentlyDenied(@NotNull Fragment host, @NotNull List<String> deniedPermissions) {

        return false;
    }

    @Override
    public boolean somePermissionPermanentlyDenied(@NotNull android.app.Fragment host, @NotNull List<String> deniedPermissions) {
        return false;
    }

    @Override
    public boolean permissionPermanentlyDenied(@NotNull Activity host, @NotNull String deniedPermission) {
        return false;
    }

    @Override
    public boolean permissionPermanentlyDenied(@NotNull Fragment host, @NotNull String deniedPermission) {
        return false;
    }

    @Override
    public boolean permissionPermanentlyDenied(@NotNull android.app.Fragment host, @NotNull String deniedPermission) {
        return false;
    }

    @Override
    public boolean somePermissionDenied(@NotNull Activity host, @NotNull String... perms) {
        return false;
    }

    @Override
    public boolean somePermissionDenied(@NotNull Fragment host, @NotNull String... perms) {
        return false;
    }

    @Override
    public boolean somePermissionDenied(@NotNull android.app.Fragment host, @NotNull String... perms) {
        return false;
    }

}