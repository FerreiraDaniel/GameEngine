precision mediump float;

varying lowp vec4 colorVarying;

/*The coordinates of the texture as input*/
varying lowp vec2 pass_textureCoords;
/*The vector normal to the surface as input*/
varying lowp vec3 surfaceNormal;
/*The vector that indicates where the light is in relation to the object*/
varying lowp vec3 toLightVector;
/* vertex from the vertex to the camera*/
varying lowp vec3 toCameraVector;
/*The visibility of the vertice in order to simulate fog*/
varying lowp float visibility;

uniform sampler2D textureSampler;

/*Composition of the light of the screen*/
uniform vec3 lightColor;

/*The damper of the specular light*/
uniform float shineDamper;
/*The reflectivity of the specular light*/
uniform float reflectivity;
/*Color of the sky in order to simulate fog*/
uniform vec3 skyColor;

void main()
{
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
    
    /*Is going to get which should be the texture result using the coordinates*/
    vec4 textureColor = texture2D(textureSampler,pass_textureCoords);
    
    /*Check if the half if less that 0.5 if is ignore the element*/
    if(textureColor.a < 0.5) {
        discard;
    }
    
    gl_FragColor = vec4(diffuse, 1.0) * textureColor + vec4(finalSpecular, 1.0);
    /*Is going to recomput the out color but now taking in account the fog effect*/
    gl_FragColor = mix(vec4(skyColor, 1.0), gl_FragColor, visibility);
}
