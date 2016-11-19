#version 400 core

/*Variable with position of the entity*/
in vec3 position;

/*Variable with coordinates of the textures of the entity*/
in vec2 textureCoords;

/*Variable with normals of the entity*/
in vec3 normal;

/*Outputs because we are sending them to the fragment shader*/
/*The coordinates of the texture as output*/
out vec2 pass_textureCoords;

/*The vector normal to the surface as output*/
out vec3 surfaceNormal;

/*The vector that indicates where the light is in relation to the object*/
out vec3 toLightVector;

/* vertex from the vertex to the camera*/
out vec3 toCameraVector;

/*The visibility of the vertice in order to simulate fog*/
out float visibility;

uniform mat4 transformationMatrix;

uniform mat4 viewMatrix;

uniform mat4 projectionMatrix;

/*Position where the light of the scene is*/
uniform vec3 lightPosition;

/*If the entity should have the normals pointing up*/
uniform float normalsPointingUp;

/*Atlas factor in case that the entity have several textures in the same image */
uniform float atlasFactor;

/*Offset in case that the entity have several textures in the same image*/
uniform vec2 textureOffset;

/*Density of fog*/
const float fog_density = 0.007;

/*Gradient of fog*/
const float fog_gradient = 1.5;

void main(void) {
	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	
	/*Position of the element relative to the camera*/
	vec4 positionRelativeToCam = viewMatrix * worldPosition;
	gl_Position = projectionMatrix * positionRelativeToCam;
	
	/*Compute the texture coordinate taking in account the atlas textures*/
	pass_textureCoords = (textureCoords/atlasFactor) + textureOffset;
	
	vec3 actualNormal = (normalsPointingUp == 1.0) ? vec3(0.0, 1.0, 0.0) : normal;
	
	surfaceNormal = (transformationMatrix * vec4(actualNormal, 0.0)).xyz;
	toLightVector = lightPosition - worldPosition.xyz;
	
	/*used for the specular light, first get the position of the camera. second subtract the position of the vertex */
	toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0, 1.0)).xyz - worldPosition.xyz;
	
	/*Distance from the vertice to the camera*/
	float distance = length(positionRelativeToCam.xyz);
	
	/*Compute visibility of the vertice taking in account the fog effect*/
	visibility = exp(-pow((distance * fog_density), fog_gradient));
	visibility = clamp(visibility, 0.0, 1.0);
}