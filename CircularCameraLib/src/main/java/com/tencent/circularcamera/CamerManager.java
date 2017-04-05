package com.tencent.circularcamera;

import android.content.Context;

/**
 * Created by maxpengli on 2017/4/5.
 */

public class CamerManager {


    //最终接口   调起摄像头悬浮窗
    public static void show(Context context){
        CircularCamerFloat manager = CircularCamerFloat.getInstance();
        manager.init(context);
        manager.show();
    }
    //最终接口  关闭摄像头悬浮窗
    public static void hide(){
        CircularCamerFloat manager  = CircularCamerFloat.getInstance();
        manager.hide();
    }

}
