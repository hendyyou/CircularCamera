package com.tencent.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tencent.circularcamera.CircularCamerManager;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button)findViewById(R.id.show);
        Button buttonHide = (Button)findViewById(R.id.hide);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircularCamerManager.callCircular(MainActivity.this);
            }
        });
        buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircularCamerManager.hideCircular();

            }
        });

    }


}
