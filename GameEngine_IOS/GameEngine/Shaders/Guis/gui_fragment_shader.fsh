precision mediump float;

/*The coordinates of the texture as input*/
varying mediump vec2 textureCoords;

uniform sampler2D textureSampler;

void main(void) {
    gl_FragColor = texture2D(textureSampler,textureCoords);
}
