package com.tencent.circularcamera;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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

    private CircularCameraSurfaceView glSurfaceView = null;

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

    public static void changeNoneFilter(){
        CircularCamerManager view = CircularCamerManager.getInstance();
        view.getGLSurface().changeNoneFilter();
    }

    public static void changeInnerFilter(){
        CircularCamerManager view = CircularCamerManager.getInstance();
        view.getGLSurface().changeInnerFilter();
    }

    public static void changeExtensionFilter(){
        CircularCamerManager view = CircularCamerManager.getInstance();
        view.getGLSurface().changeExtensionFilter();
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
        this.context = context;
        if (isshow){
            return;
        }
//        permissionsChecker = new PermissionsChecker(context);
//        if(permissionsChecker.lacksPermissions())//缺少权限
//        {
//            Intent  intent = new Intent();
//            intent.setClass(context,PermissionActivity.class);
//            context.startActivity(intent);
//        }else {
//            initWindow(context);
//        }
        initWindow(context);
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
        wmParams.gravity = Gravity.TOP|Gravity.LEFT;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = 0;
        wmParams.y = 0;
        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        LayoutInflater inflater = LayoutInflater.from(context);
        //获取浮动窗口视图所在布局
        mFloatLayout = (RelativeLayout) inflater.inflate(R.layout.activity_main_camera, null);
        glSurfaceView = (CircularCameraSurfaceView) mFloatLayout.findViewById(R.id.camera_textureview);
        mFloatLayout.setOnTouchListener(new View.OnTouchListener() {
            private float mTouchStartX;
            private float mTouchStartY;
            private float x;
            private float y;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //获取相对屏幕的坐标，即以屏幕左上角为原点
                x = event.getRawX();
                y = event.getRawY() - 25;   //25是系统状态栏的高度
//                Log.i("currP", "currX"+x+"====currY"+y);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:    //捕获手指触摸按下动作
                        //获取相对View的坐标，即以此View左上角为原点
                        mTouchStartX = event.getX();
                        mTouchStartY = event.getY();
//                        Log.i("startP", "startX" + mTouchStartX + "====startY" + mTouchStartY);
                        break;
                    case MotionEvent.ACTION_MOVE:   //捕获手指触摸移动动作
                        //更新浮动窗口位置参数
                        wmParams.x = (int) (x - mTouchStartX);
                        wmParams.y = (int) (y - mTouchStartY);
                        mWindowManager.updateViewLayout(view, wmParams);  //刷新显示
                        break;
                    case MotionEvent.ACTION_UP:    //捕获手指触摸离开动作
                        //更新浮动窗口位置参数
                        wmParams.x = (int) (x - mTouchStartX);
                        wmParams.y = (int) (y - mTouchStartY);
                        mWindowManager.updateViewLayout(view, wmParams);  //刷新显示
                        mTouchStartX = mTouchStartY = 0;
                        break;
                }
                return true;
            }
        });
    }

    private CircularCameraSurfaceView getGLSurface(){
        return glSurfaceView;
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
