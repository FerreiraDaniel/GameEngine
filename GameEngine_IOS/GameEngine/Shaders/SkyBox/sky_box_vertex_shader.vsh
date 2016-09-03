/*Variable with position of the sky box*/
attribute vec3 position;
/*Variable with coordinates of the textures of the sky box Notice that they are 3D vector*/
varying lowp vec3 pass_textureCoords;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void){
    
    gl_Position = projectionMatrix * viewMatrix * vec4(position, 1.0);
    pass_textureCoords = position;
    
}