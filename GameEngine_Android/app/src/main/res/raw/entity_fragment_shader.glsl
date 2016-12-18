precision mediump float;

/*The coordinates of the texture as input*/
varying vec2 pass_textureCoords;

/*The vector normal to the surface as input*/
varying vec3 surfaceNormal;

/*The vector that indicates where the light is in relation to the object*/
varying vec3 toLightVector;

/* vertex from the vertex to the camera*/
varying vec3 toCameraVector;

/*The visibility of the vertice in order to simulate fog*/
varying float visibility;


/*Sampler of the texture of the entity*/
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
    vec4 textureColor = texture2D(textureSampler,pass_textureCoords) ;

    /*Check if the half if less that 0.5 if is ignore the element*/
    if(textureColor.a < 0.5) {
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
	/*We ignore scalar products smaller that 0.2 like this we get always a minimum color*/
	float brightness = max(nDot, 0.2);
	vec3 diffuse = brightness * lightColor;

	/*Specular lighting*/
	vec3 unitToCameraVector = normalize(toCameraVector);
	vec3 lightDirection = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
	float specularFactor = dot(reflectedLightDirection, unitToCameraVector);
	specularFactor = max(specularFactor, 0.0);
	float dampedFactor = pow(specularFactor, shineDamper);
	vec3 finalSpecular = dampedFactor * reflectivity * lightColor;



	//Multiply the light component by the texture color
	vec4 baseColor = vec4(diffuse, 1.0) * objectDiffuse + vec4(finalSpecular, 1.0) ;

	/*Is going to recompute the out color but now taking in account the fog effect*/
	baseColor = mix(skyColor, baseColor, visibility);

	gl_FragColor = baseColor;
}