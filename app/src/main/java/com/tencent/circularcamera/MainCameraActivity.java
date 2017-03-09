package com.tencent.circularcamera;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

public class MainCameraActivity extends AppCompatActivity {

    CircularCameraSurfaceView glSurfaceView = null;
    ImageButton shutterBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_camera);
        glSurfaceView = (CircularCameraSurfaceView)findViewById(R.id.camera_textureview);
//        shutterBtn = (ImageButton)findViewById(R.id.btn_shutter);
//        shutterBtn.setOnClickListener(new BtnListeners());
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }



//    private class BtnListeners implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            switch(v.getId()){
//                case R.id.btn_shutter:
//                    CameraInterface.getInstance().doTakePicture();
//                    break;
//                default:break;
//            }
//        }
//    }


}
