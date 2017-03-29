package me.jessyan.camerafilters.filter;

import java.nio.FloatBuffer;

/**
 * Created by jerikc on 16/2/23.
 */
public interface IFilter {
    int getTextureTarget();

    void setTextureSize(int width, int height);

    void onDraw(float[] mvpMatrix,
                FloatBuffer vertexBuffer,
                float[] texMatrix,
                FloatBuffer texBuffer,
                int textureId);

    void releaseProgram();
}
