#import "ShaderManager.h"
#import "LoadUtils.h"
#import "ShaderProgram.h"
#import "GLSLUtils.h"



@implementation ShaderManager {
@private
    ShaderProgram * shaderProgram;
}


/**
 * Initialize of the program shader manager
 *
 * @param vertexFile   File with vertex description
 * @param fragmentFile File with fragment description
 */
- (id)init: (NSString*) vertexFile :(NSString*) fragmentFile{
    self = [super init];
    if (self) {
        NSString *vertShaderPathname, *fragShaderPathname;
        const char *vertexShaderSrc, *fragShaderSrc;
        
        //Read the vertex file to one variable
        vertShaderPathname = [[NSBundle mainBundle] pathForResource:vertexFile ofType:@"vsh"];
        vertexShaderSrc = [LoadUtils readTextFromRawResource: vertShaderPathname];
        
        //Read the fragment shader file to one variable
        fragShaderPathname = [[NSBundle mainBundle] pathForResource:fragmentFile ofType:@"fsh"];
        fragShaderSrc = [LoadUtils readTextFromRawResource: fragShaderPathname];
        
        shaderProgram = [GLSLUtils loadProgram :vertexShaderSrc : fragShaderSrc ];
        
        if (shaderProgram == nil) {
            return nil;
        }
        
        [self bindAttributes];
        
        // Link program.
        if ([GLSLUtils linkProgram: shaderProgram]) {
            [self getAllUniformLocations];
        } else {
            NSLog(@"Failed to link program: %d", shaderProgram.programId);
            
            if (shaderProgram.vertexShaderId) {
                glDeleteShader(shaderProgram.vertexShaderId);
                [shaderProgram setVertexShaderId: 0];
            }
            if (shaderProgram.fragmentShaderId) {
                glDeleteShader(shaderProgram.fragmentShaderId);
                [shaderProgram setFragmentShaderId: 0];
            }
            if (shaderProgram.programId) {
                glDeleteProgram(shaderProgram.programId);
                [shaderProgram setProgramId: 0];
            }
            
            return nil;
        }
    }
    return self;
}

/**
 * Called to bind the attributes to the program shader
 */
- (void) bindAttributes {
    [NSException raise:@"Invoked abstract method" format: @"Invoked abstract method"];
}

/**
 * Called to ensure that all the shader managers get their uniform locations
 */
- (void) getAllUniformLocations {
    [NSException raise:@"Invoked abstract method" format: @"Invoked abstract method"];
}

/**
 * Bind one attribute
 *
 * @param attributeIndex Index of the attribute to bind
 * @param variableName   Name of the attribute to bind
 */
- (void) bindAttribute: (int) attributeIndex : (char*) variableName {
    glBindAttribLocation(shaderProgram.programId, attributeIndex, variableName);
}


/**
 * Get the position of one uniform variable in the program shader
 *
 * @param uniformName the name of the uniform variable as appears in the shader code
 * @return the position of the uniform variable in program shader
 */
- (int) getUniformLocation: (const char*) uniformName {
    return glGetUniformLocation(shaderProgram.programId, uniformName);
}

/**
 * Load a integer value to be used in the shader script
 *
 * @param location
 *            location of the shader variable in the script
 * @param value
 *            The value to load
 */
- (void) loadInt : (int) location : (int) value {
    glUniform1i(location, value);
}

/**
 * Load a float value to be used in the shader script
 *
 * @param location location of the shader variable in the script
 * @param value    value to load
 */
- (void) loadFloat : (int) location : (float) value {
    glUniform1f(location, value);
}

/**
 * Load a vector to be used in the shader script
 *
 * @param location location of the shader variable in the script
 * @param vector The vector to load
 */
- (void) loadVector : (int) location : (Vector3f*) vector; {
     glUniform3f(location, vector.x, vector.y, vector.z);
 }

/**
 * Load a boolean value to be used in the shader script
 *
 * @param location The location of the shader variable in the script
 * @param value    value to load
 */
- (void) loadBoolean :(int) location : (BOOL) value {
    float toLoad = value ? 1 : 0;
    glUniform1f(location, toLoad);
}

/**
 * Load a matrix to be used in the shader script
 *
 * @param location The location of the shader variable in the script
 * @param matrix   Matrix to load
 */
- (void) loadMatrix :(int) location : (GLTransformation*) matrix {
    glUniformMatrix4fv(location, 1, 0, matrix.getMatrix);
}

/**
 * Indicates that should start to use a certain program shader
 */
- (void) start {
    if (shaderProgram != nil) {
        glUseProgram(shaderProgram.programId);
    }
}

/**
 * Indicate that should not use a certain program no more
 */
- (void) stop {
    glUseProgram(0);
}

/**
 * Clean the program shader from memory
 */
- (void) dealloc {
    if (shaderProgram.programId) {
        glDeleteProgram(shaderProgram.programId);
        [shaderProgram setProgramId: 0];
    }
    shaderProgram = nil;
}

@end
