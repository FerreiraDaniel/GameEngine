#import "TerrainShaderManager.h"

@implementation TerrainShaderManager
{
@private
    
    // Uniform index.
    enum
    {
        TRANSFORMATION_MATRIX,
        VIEW_MATRIX,
        PROJECTION_MATRIX,
        
        LIGHT_POSITION,
        LIGHT_COLOR,
        SHINE_DAMPER,
        REFLECTIVITY,
        SKY_COLOR,
        BACKGROUND_TEXTURE,
        MUD_TEXTURE,
        GRASS_TEXTURE,
        PATH_TEXTURE,
        WEIGHT_TEXTURE,
        NUM_UNIFORMS
    };
    GLint uniforms[NUM_UNIFORMS];
    
}

/**
 * Initializor of the game shader where the vertex and fragment shader of
 * the game engine are loaded
 *
 */
- (id)init {
    self = [super init: @"terrain_vertex_shader" : @"terrain_fragment_shader"];
    return self;
}

/**
 * Called to bind the attributes to the program shader
 */
- (void) bindAttributes {
    [super bindAttribute : LOCATION_ATTR_ID: "position"];
    [super bindAttribute : TEX_COORDINATE_ATTR_ID: "textureCoords"];
    [super bindAttribute : NORMAL_VECTOR_ATTR_ID: "normal"];
}

/**
 * Called to ensure that all the shader managers get their uniform locations
 */
- (void) getAllUniformLocations {
    @autoreleasepool {
        NSDictionary * uniformsDic = [NSDictionary dictionaryWithObjectsAndKeys:
                                      @"projectionMatrix", [NSNumber numberWithInt:PROJECTION_MATRIX],
                                      @"viewMatrix", [NSNumber numberWithInt: VIEW_MATRIX],
                                      @"transformationMatrix", [NSNumber numberWithInt: TRANSFORMATION_MATRIX],
                                      @"lightPosition", [NSNumber numberWithInt:LIGHT_POSITION],
                                      @"lightColor", [NSNumber numberWithInt:LIGHT_COLOR],
                                      @"shineDamper", [NSNumber numberWithInt:SHINE_DAMPER],
                                      @"reflectivity", [NSNumber numberWithInt:REFLECTIVITY],
                                      @"skyColor", [NSNumber numberWithInt:SKY_COLOR],
                                      @"backgroundTexture", [NSNumber numberWithInt: BACKGROUND_TEXTURE],
                                      @"mudTexture",[NSNumber numberWithInt: MUD_TEXTURE],
                                      @"grassTexture",[NSNumber numberWithInt: GRASS_TEXTURE],
                                      @"pathTexture",[NSNumber numberWithInt: PATH_TEXTURE],
                                      @"weightMapTexture",[NSNumber numberWithInt: WEIGHT_TEXTURE],
                                      nil];
        for(id key in uniformsDic) {
            int iKey = [key intValue];
            const char *shaderVariable = [(uniformsDic[key]) UTF8String];
            uniforms[iKey] = [super getUniformLocation: shaderVariable];
            if(uniforms[iKey] < 0) {
                NSLog(@"Problems getting %@'s location in the shader", uniformsDic[key]);
            }
        }
    }
}

- (void) connectTextureUnits {
    [super loadInt: uniforms[BACKGROUND_TEXTURE] : TEXTURE_UNIT0];
    [super loadInt: uniforms[MUD_TEXTURE] : TEXTURE_UNIT1];
    [super loadInt: uniforms[GRASS_TEXTURE] : TEXTURE_UNIT2];
    [super loadInt: uniforms[PATH_TEXTURE] : TEXTURE_UNIT3];
    [super loadInt: uniforms[WEIGHT_TEXTURE] : TEXTURE_UNIT4];
    
}

/**
 * Load the projection matrix
 *
 * @param matrix
 *            the matrix to be loaded
 */
- (void) loadProjectionMatrix : (GLTransformation*) matrix {
    [super loadMatrix:  uniforms[PROJECTION_MATRIX]: matrix];
}

/**
 * Load the view matrix
 *
 * @param matrix
 *            the matrix to be loaded
 */
- (void) loadViewMatrix : (GLTransformation*) matrix {
    [super loadMatrix:  uniforms[VIEW_MATRIX]: matrix];
}


/**
 * Load the transformation matrix
 *
 * @param matrix
 *            the matrix to be loaded
 */
- (void) loadTransformationMatrix : (GLTransformation*) matrix {
    [super loadMatrix:  uniforms[TRANSFORMATION_MATRIX]: matrix];
}

/**
 * Put passes the information of the light to the
 * Shader program
 *
 * @param light the light to load in the shader program
 */
- (void) loadLight : (Light*) light {
    [super loadVector: uniforms[LIGHT_POSITION] : [light position]];
    [super loadVector: uniforms[LIGHT_COLOR] : [light color]];
}

/**
 * Load the values of the specular light in the fragment shader
 *
 * @param damper		The damper of the specular lighting
 * @param reflectivity	The reflectivity of the material
 */
- (void) loadShineVariables : (float) damper : (float) reflectivity {
    [super loadFloat : uniforms[SHINE_DAMPER] : damper];
    [super loadFloat : uniforms[REFLECTIVITY] : reflectivity];
}


/**
 * Load the color of the sky in order to simulate fog
 *
 * @param skyColor
 * 			Color of the sky
 */
- (void)  loadSkyColor : (Vector3f*) skyColor {
    [super loadVector: uniforms[SKY_COLOR] : skyColor];
}

@end
