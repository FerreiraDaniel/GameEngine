#import <Foundation/Foundation.h>
#import <OpenGLES/ES2/glext.h>
#import "ShaderProgram.h"


@interface GLSLUtils : NSObject

/**
 * Compiles the shader sources and attache them to the program
 *
 * @param vertexShaderSrc
 *            Source code of the vertex shader
 * @param fragShaderSrc
 *            Source code of the fragment shader
 * @return If nil There was an error not 0 -> Id of the program loaded
 */
+ (ShaderProgram*)loadProgram: (const char *)vertexShaderSrc :(const char *)fragShaderSrc;

/**
 * Link the program shader with their vertex shader and fragment shader
 *
 * @param shaderProgram The program shader not linked yet
 *
 * @return False = Not linked True = Linked
 */
+ (BOOL)linkProgram: (ShaderProgram*) shaderProgram;

@end
