

#import "SkyBoxShaderManager.h"

/**
 * Manager of the shader files that are going to be load to render the 3D
 */
@implementation SkyBoxShaderManager

{
@private
    
    // Uniform index.
    enum
    {
        PROJECTION_MATRIX,
        VIEW_MATRIX,
        NUM_UNIFORMS
    };
    GLint uniforms[NUM_UNIFORMS];
    
    // Attribute index.
    enum
    {
        ATTRIB_VERTEX,
        ATTRIB_NORMAL,
        NUM_ATTRIBUTES
    };
}

/**
 * Initializor of the game shader where the vertex and fragment shader of
 * the game engine are loaded
 *
 */
- (id)init {
    self = [super init: @"sky_box_vertex_shader" : @"sky_box_fragment_shader"];
    return self;
}

/**
 * Called to bind the attributes to the program shader
 */
- (void) bindAttributes {
    [super bindAttribute : LOCATION_ATTR_ID: "position"];
}

/**
 * Called to ensure that all the shader managers get their uniform locations
 */
- (void) getAllUniformLocations {
    @autoreleasepool {
        NSDictionary * uniformsDic = [NSDictionary dictionaryWithObjectsAndKeys:
                                      @"projectionMatrix", [NSNumber numberWithInt:PROJECTION_MATRIX],
                                      @"viewMatrix", [NSNumber numberWithInt: VIEW_MATRIX],
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

@end
