#version 120

/*Variable with position of the entity*/
attribute vec3 position;
/*Variable with coordinates of the textures of the entity*/
attribute vec2 textureCoords;
/*Variable with normals of the entity*/
attribute vec3 normal;

/*The coordinates of the texture as output*/
varying vec2 pass_textureCoords;
/*The vector normal to the surface as output*/
varying vec3 surfaceNormal;
/*The vector that indicates where the light is in relation to the object*/
varying vec3 toLightVector;
/* vertex from the vertex to the camera*/
varying vec3 toCameraVector;

uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
/*Position where the camera is during the render of the frame*/
uniform vec3 cameraPosition;

/*Position where the light of the scene is*/
uniform vec3 lightPosition;

void main(void) {
	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * viewMatrix * worldPosition;
	pass_textureCoords = textureCoords;
	
	/*Elements for lighting*/
	surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
	toLightVector = lightPosition - worldPosition.xyz;
	
	/*used for the specular light, first get the position of the camera. second subtract the position of the vertex */
	toCameraVector = cameraPosition - worldPosition.xyz;
}