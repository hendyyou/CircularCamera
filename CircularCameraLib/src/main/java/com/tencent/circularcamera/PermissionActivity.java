package com.tencent.circularcamera;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by maxpengli on 2017/3/8.
 */
public class PermissionActivity extends AppCompatActivity {
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private static final int REQUEST_CODE = 0; // 请求码




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermissionsChecker = new PermissionsChecker(this);
        Log.i("xp", "PermissionActivity oncreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("xp", "PermissionActivity onResume");
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions()) {
            Log.i("xp", "request permission");
            ActivityCompat.requestPermissions(this, PermissionsChecker.PERMISSIONS, REQUEST_CODE);//自定义的code
        }
        else{
            startMainActivity();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.i("xp", "onRequestPermissionResult");
        //可在此继续其他操作。
        if (requestCode == REQUEST_CODE)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){//授权成功
                Log.i("xp", "get permission success");
                startMainActivity();
            }
            else {
                Log.i("xp", "get permission failed");
                finish();
            }
        }
    }

    private void startMainActivity() {
        CircularCamerManager.callCircular(this);
        finish();
    }

}
