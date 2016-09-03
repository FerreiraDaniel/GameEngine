/*Coordinates of the texture notice that they are 3D*/
varying lowp vec3 pass_textureCoords;


uniform samplerCube cubeMap;

void main(void){
    /*The color that is going to be the output of the fragment shader*/
    gl_FragColor = textureCube(cubeMap, pass_textureCoords);
}