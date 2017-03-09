package com.tencent.circularcamera;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * Created by maxpengli on 2017/3/9.
 */
public class CircularCamerManager {
    Context context;

    //定义浮动窗口布局
    RelativeLayout mFloatLayout;

    WindowManager mWindowManager;
    //创建浮动窗口设置布局参数的对象
    WindowManager.LayoutParams wmParams;

    CircularCameraSurfaceView glSurfaceView = null;

    private volatile static CircularCamerManager instance = null;


    private boolean isshow = false;

    PermissionsChecker permissionsChecker = null;



    //最终接口   调起摄像头悬浮窗
    public static void callCircular(Context context){
        CircularCamerManager view = CircularCamerManager.getInstance();
        view.init(context);
        view.show();
    }
    //最终接口  关闭摄像头悬浮窗
    public static void hideCircular(){
        CircularCamerManager view2  = CircularCamerManager.getInstance();
        view2.hide();
    }




    private CircularCamerManager() {

    }

    private static CircularCamerManager getInstance(){
        if(instance == null){
            synchronized (CircularCamerManager.class){
                if(instance == null){
                    instance = new CircularCamerManager();
                }
            }
        }
        return instance;

    }


    private void init(Context context){
        if (isshow){
            return;
        }
        permissionsChecker = new PermissionsChecker(context);
        if(permissionsChecker.lacksPermissions())//缺少权限
        {
            Intent  intent = new Intent();
            intent.setClass(context,PermissionActivity.class);
            context.startActivity(intent);
        }else {
            initWindow(context);
        }

    }


    private void initWindow(Context context) {
        mWindowManager = (WindowManager)context.getApplicationContext().getSystemService(context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();

        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.CENTER;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = 0;
        wmParams.y = 0;
        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        LayoutInflater inflater = LayoutInflater.from(context);
        //获取浮动窗口视图所在布局
        mFloatLayout = (RelativeLayout) inflater.inflate(R.layout.activity_main_camera, null);

        glSurfaceView =(CircularCameraSurfaceView) mFloatLayout.findViewById(R.id.camera_textureview);
        //添加mFloatLayout
    }

    private void show(){
        if (isshow){
            return;
        }
        glSurfaceView.onResume();
        mWindowManager.addView(mFloatLayout, wmParams);
        isshow = true;

    }

    private void hide(){
        if (!isshow){
            return;
        }
        glSurfaceView.onPause();
        mWindowManager.removeView(mFloatLayout);
        isshow = false;
    }


}
