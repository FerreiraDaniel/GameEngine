#version 400

/*Coordinates of the texture notice that they are 3D*/
in vec3 textureCoords;

/*The color that is going to be the output of the fragment shader*/
out vec4 out_Color;

uniform samplerCube cubeMap;

void main(void){
    out_Color = texture(cubeMap, textureCoords);
}