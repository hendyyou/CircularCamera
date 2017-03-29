package me.jessyan.camerafilters.filter;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;

import me.jessyan.camerafilters.R;
import me.jessyan.camerafilters.util.GlUtil;

/**
 * Created by shengwenhui on 16/3/3.
 */
public class CameraFilterMosaic extends CameraFilter {
    private int muTexsizeLoc;

    private static final float Texsize_array[] = {
            255, 255,
    };

    public CameraFilterMosaic(Context context) {
        super(context);
    }

    @Override
    protected int createProgram(Context applicationContext) {
        return GlUtil.createProgram(applicationContext,R.raw.vertex_shader,
                R.raw.fragment_shader_mosaic);
    }

    @Override
    protected void getGLSLValues() {
        super.getGLSLValues();

        muTexsizeLoc = GLES20.glGetUniformLocation(mProgramHandle, "TexSize");
    }

    @Override
    protected void bindGLSLValues(float[] mvpMatrix, FloatBuffer vertexBuffer, float[] texMatrix, FloatBuffer texBuffer) {
        super.bindGLSLValues(mvpMatrix, vertexBuffer, texMatrix,texBuffer);

        GLES20.glUniform2fv(muTexsizeLoc, 1, Texsize_array, 0);
    }
}