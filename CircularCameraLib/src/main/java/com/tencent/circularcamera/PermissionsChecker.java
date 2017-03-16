package com.tencent.circularcamera;

import android.Manifest;
import android.content.Context;

/**
 * Created by maxpengli on 2017/3/8.
 */
public class PermissionsChecker {
    private final Context mContext;


    // 所需的全部权限
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA
    };




    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions() {
        for (String permission : PERMISSIONS) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
//        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED;
        return false;
    }
}
