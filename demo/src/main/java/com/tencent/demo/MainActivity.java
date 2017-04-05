package com.tencent.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tencent.circularcamera.CamerManager;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("xp","mainActivity ondestroy");
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show:
                CamerManager.show(this);
                break;
            case R.id.hide:
                CamerManager.hide();
                break;
            default:
                break;
        }
    }

}
