#version 400 core

/*The coordinates of the texture as input*/
in vec2 pass_textureCoords;

/*The vector normal to the surface as input*/
in vec3 surfaceNormal;

/*The vector that indicates where the light is in relation to the object*/
in vec3 toLightVector;

/* vertex from the vertex to the camera*/
in vec3 toCameraVector;

/*The visibility of the vertice in order to simulate fog*/
in float visibility;

out vec4 out_Color;

uniform sampler2D textureSampler;

/*Composition of the light of the screen*/
uniform vec3 lightColor;

/*The damper of the specular light*/
uniform float shineDamper;
/*The reflectivity of the specular light*/
uniform float reflectivity;

/*Color of the sky in order to simulate fog*/
uniform vec4 skyColor;

/* Weight of the texture in the final ambient color to be render*/
uniform float textureWeight;

/* Color that the material is going to get if there is not texture*/
uniform vec4 diffuseColor;

void main(void) {
	/*Is going to get which should be the texture result using the coordinates*/
	vec4 textureColor = texture(textureSampler,pass_textureCoords);

	/*Check if the half if less that 0.5 if is ignore the element*/
	if ((textureColor.a < 0.5) && (textureWeight == 1.0)) {
		discard;
	}
	
	//Computes the diffuse component of the color of the object
	vec4 objectDiffuse = (textureWeight * textureColor) + diffuseColor;

	/*Normalize the surface normal*/
	vec3 unitNormal = normalize(surfaceNormal);
	/*Normalize the light color*/
	vec3 unitLightVector = normalize(toLightVector);
	
	/*Dot product between light vector and normal of the surface*/
	float nDot = dot(unitNormal, unitLightVector);
	/*We ignore scalar products smaller that 0*/
	float brightness = max(nDot, 0.0);
	/*Final diffuse lighting component*/
	vec3 diffuse = brightness * lightColor;
	
	/*Specular lighting*/
	vec3 unitToCameraVector = normalize(toCameraVector);
	vec3 lightDirection = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
	float specularFactor = dot(reflectedLightDirection, unitToCameraVector);
	specularFactor = max(specularFactor, 0.0);
	float dampedFactor = pow(specularFactor, shineDamper);
	vec3 finalSpecular = dampedFactor * reflectivity * lightColor;
	

	//Final mix effect
	out_Color = vec4(diffuse, 1.0) * objectDiffuse + vec4(finalSpecular, 1.0);
	/*Is going to recompute the out color but now taking in account the fog effect*/
	out_Color = mix(skyColor, out_Color, visibility);
}