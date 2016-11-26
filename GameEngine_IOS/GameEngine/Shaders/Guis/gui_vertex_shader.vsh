/*Variable with position of the gui*/
attribute vec2 position;

/*Variable with coordinates of the textures of the gui*/
varying mediump vec2  textureCoords;

uniform mat4 transformationMatrix;


void main(void) {
	gl_Position = transformationMatrix * vec4(position, 0.0, 1.0);
		
	/*Compute the texture coordinates*/
	textureCoords = vec2((position.x+1.0)/2.0, 1.0 - (position.y+1.0)/2.0);
}
