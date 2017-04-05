package com.tencent.circularcamera;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by maxpengli on 2017/3/8.
 */
public class PermissionActivity extends Activity{
    private static final int REQUEST_CODE = 0; // 请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        Log.i("xp", "PermissionActivity oncreate");
        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA },REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("xp", "PermissionActivity onResume");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.i("xp", "onRequestPermissionResult");
        if (requestCode == REQUEST_CODE)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){//授权成功
                Log.i("xp", "get permission success");
                startMainActivity();
                finish();
            }
            else {
                Log.i("xp", "get permission failed");
                finish();
            }
        }
    }

    private void startMainActivity() {
        CamerManager.show(this.getApplicationContext());
    }

}
