package jp.co.cyberagent.android.gpuimage;

/**
 * Created by maxpengli on 2017/4/5.
 */

public class GPUImageDirectFilter extends GPUImageFilterGroup {

    public static final String fragmentShaderCode =
            "#extension GL_OES_EGL_image_external : require\n"+
            "precision mediump float;" +
            "varying vec2 textureCoordinate;\n" +
            "uniform samplerExternalOES inputImageTexture;\n" +
            "void main() {\n" +
            "vec2 coord = textureCoordinate - vec2(0.5, 0.5);\n" +
            "float factor=0.49;\n"+
            "float scale = 1.0/(0.5-factor);\n"+
            "float radius = length(coord);\n"+
            "vec4 color = texture2D( inputImageTexture, vec2(0.75*textureCoordinate.x,textureCoordinate.y) );\n"+
            "float stepA = 1.0-step(0.5, radius);\n"+
            "float stepB = 1.0-step(factor, radius);\n"+
            "vec4 innerColor = stepB * color;\n"+
            "vec4 midColor = (stepA-stepB) * (1.0-(radius-factor) * scale) * color;\n"+
            "gl_FragColor = innerColor + midColor;\n" +
            "}";


    public GPUImageDirectFilter() {
        super();
        addFilter(new GPUImageFilter(NO_FILTER_VERTEX_SHADER,fragmentShaderCode));
        addFilter(new GPUImageGrayscaleFilter());
    }
}
