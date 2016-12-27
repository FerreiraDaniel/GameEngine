precision mediump float;

/*Coordinates of the texture notice that they are 3D*/
/*The coordinates of the texture as input*/
varying vec3 textureCoords;

uniform samplerCube cubeMap;

void main(void){
    /*The color that is going to be the output of the fragment shader*/
	gl_FragColor = textureCube(cubeMap, textureCoords);
}