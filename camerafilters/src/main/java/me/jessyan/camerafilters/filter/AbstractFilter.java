package me.jessyan.camerafilters.filter;

import android.content.Context;

import java.nio.FloatBuffer;

/**
 * Created by jerikc on 16/2/23.
 */
public abstract class AbstractFilter {
    protected abstract int createProgram(Context applicationContext);

    protected abstract void getGLSLValues();

    protected abstract void useProgram();

    protected abstract void bindTexture(int textureId);

    protected abstract void bindGLSLValues(float[] mvpMatrix,
                                           FloatBuffer vertexBuffer,
                                           float[] texMatrix,
                                           FloatBuffer texBuffer);

    protected abstract void drawArrays();

    protected abstract void unbindGLSLValues();

    protected abstract void unbindTexture();

    protected abstract void disuseProgram();
}
