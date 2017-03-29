package com.tencent.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.tencent.circularcamera.CircularCamerManager;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show:
                CircularCamerManager.callCircular(MainActivity.this);
                break;
            case R.id.hide:
                CircularCamerManager.hideCircular();
                break;
            case R.id.bt_none:
                CircularCamerManager.changeNoneFilter();
                break;
            case R.id.bt_inner:
                CircularCamerManager.changeInnerFilter();
                break;
            case R.id.bt_extension:
                CircularCamerManager.changeExtensionFilter();
                break;
        }
    }


}
