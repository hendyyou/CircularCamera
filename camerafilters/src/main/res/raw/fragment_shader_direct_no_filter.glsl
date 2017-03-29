#extension GL_OES_EGL_image_external : require

precision mediump float;

varying vec2 vTextureCoord;
uniform samplerExternalOES uTexture;

void main() {
     vec2 coord = vTextureCoord - vec2(0.5, 0.5);
     float factor=0.49;
     float scale = 1.0/(0.5-factor);
     float radius = length(coord);
     vec4 color = texture2D( uTexture, vec2(0.75*vTextureCoord.x,vTextureCoord.y) );
     float stepA = 1.0-step(0.5, radius);
     float stepB = 1.0-step(factor, radius);
     vec4 innerColor = stepB * color;
     vec4 midColor = (stepA-stepB) * (1.0-(radius-factor) * scale) * color;
     gl_FragColor = innerColor + midColor;
}