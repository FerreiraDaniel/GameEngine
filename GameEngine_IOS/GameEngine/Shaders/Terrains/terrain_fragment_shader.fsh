precision mediump float;

varying lowp vec4 colorVarying;

/*The coordinates of the texture as input*/
varying mediump vec2 pass_textureCoords;

/*The vector normal to the surface as input*/
varying lowp vec3 surfaceNormal;

/*The vector that indicates where the light is in relation to the object*/
varying lowp vec3 toLightVector;

/* vertex from the vertex to the camera*/
varying lowp vec3 toCameraVector;

/*The visibility of the vertice in order to simulate fog*/
varying lowp float visibility;

/*The background texture*/
uniform sampler2D backgroundTexture;

/*The mud texture*/
uniform sampler2D mudTexture;

/*The grass texture*/
uniform sampler2D grassTexture;

/*The path texture*/
uniform sampler2D pathTexture;

/*The weight map texture*/
uniform sampler2D weightMapTexture;

/*Composition of the light of the screen*/
uniform vec3 lightColor;

/*The damper of the specular light*/
uniform float shineDamper;

/*The reflectivity of the specular light*/
uniform float reflectivity;

/*Color of the sky in order to simulate fog*/
uniform vec4 skyColor;

void main()
{
    /*Using the blend map texture is going to attribute a certain weight to the different textures*/
    vec4 weightMapColor = texture2D(weightMapTexture, pass_textureCoords);
    
    /*The weight of the background texture will */
    float backgroundTextureWeight = 1.0 - (weightMapColor.r + weightMapColor.g + weightMapColor.b);
    
    /*Makes the tiling possible*/
    vec2 tiledCoordinates = pass_textureCoords * 400.0;
    
    /*Compute the background color*/
    vec4 backgroundTextureColor = backgroundTextureWeight * texture2D(backgroundTexture, tiledCoordinates);
    
    /*Compute the mud color*/
    vec4 mudTextureColor = weightMapColor.r * texture2D(mudTexture, tiledCoordinates);
    
    /*Compute the grass color*/
    vec4 grassTextureColor = weightMapColor.g * texture2D(grassTexture, tiledCoordinates);
    
    /*Compute the path color*/
    vec4 pathTextureColor = weightMapColor.b * texture2D(pathTexture, tiledCoordinates);
    
    /*The end result of the assembly of color*/
    vec4 totalColor = backgroundTextureColor + mudTextureColor + grassTextureColor + pathTextureColor;
    
    /*Normalize the surface normal*/
    vec3 unitNormal = normalize(surfaceNormal);
    
    /*Normalize the light color*/
    vec3 unitLightVector = normalize(toLightVector);
    
    /*Dot product between light vector and normal of the surface*/
    float nDot = dot(unitNormal, unitLightVector);
    
    /*We ignore scalar products smaller that 0*/
    float brightness = max(nDot, 0.2);
    
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
    
    
    gl_FragColor = vec4(diffuse, 1.0) * totalColor + vec4(finalSpecular, 1.0);
    
    /*Is going to recompute the out color but now taking in account the fog effect*/
    gl_FragColor = mix(skyColor, gl_FragColor, visibility);

}
