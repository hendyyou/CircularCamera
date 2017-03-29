package com.tencent.circularcamera;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import me.jessyan.camerafilters.base.FilterManager;
import me.jessyan.camerafilters.entity.FilterInfo;

public class CircularCameraSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {

    private static final int DESIRED_WIDTH = 350;
    private static final int DESIRED_HEIGHT = 350;
    Context mContext;
    SurfaceTexture mSurface;
    int mTextureID = -1;
    private FilterManager mFilterManager;
    private int mInnerIndex = 1;

    public CircularCameraSurfaceView(Context context) {
        super(context);
        mContext = context;
        setZOrderOnTop(true);
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        setRenderer(this);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    public CircularCameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setZOrderOnTop(true);
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        setRenderer(this);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        if (mFilterManager == null) {
            mFilterManager = FilterManager.initFilterManager(mContext);//初始化
        }

        mFilterManager.initialize();
        mTextureID = mFilterManager.createTexture();

//        mTextureID = createTextureID();
        mSurface = new SurfaceTexture(mTextureID);
        mSurface.setOnFrameAvailableListener(this);
//        mDirectDrawer = new DirectDrawer(mTextureID);
        //setZOrderOnTop(true);
        CameraInterface.getInstance().doOpenCamera(null);

    }
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, DESIRED_WIDTH, DESIRED_HEIGHT);
        if(!CameraInterface.getInstance().isPreviewing()){
            CameraInterface.getInstance().doStartPreview(mSurface, 1.33f);
        }


    }
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        mSurface.updateTexImage();
        float[] mtx = new float[16];
        mSurface.getTransformMatrix(mtx);
//        boolean isLandScreen = false;
//        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
//            isLandScreen = true;
//        } else {
//            isLandScreen = false;
//        }
//        mDirectDrawer.draw(mtx, isLandScreen);
        mFilterManager.drawFrame(mTextureID, mtx, DESIRED_WIDTH, DESIRED_HEIGHT);
    }


    public void changeNoneFilter() {
        mFilterManager.changeFilter(new FilterInfo(false, 0));
    }

    public void changeInnerFilter() {
        if (mInnerIndex > 13) {
            mInnerIndex = 1;
        }
        mFilterManager.changeFilter(new FilterInfo(false, mInnerIndex));
        mInnerIndex++;
    }

    public void changeExtensionFilter() {
        mFilterManager.changeFilter(new FilterInfo(true, 0));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        CameraInterface.getInstance().doStopCamera();
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.requestRender();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = DESIRED_WIDTH;
        int desiredHeight = DESIRED_HEIGHT;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }
}