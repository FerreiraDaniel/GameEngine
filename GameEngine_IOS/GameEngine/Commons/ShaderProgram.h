#import <Foundation/Foundation.h>
#import <OpenGLES/ES2/glext.h>

@interface ShaderProgram : NSObject

/**
 * The id of the program
 */
@property GLuint programId;

/**
 * The id of the vertex shader
 */
@property GLuint vertexShaderId;

/**
 * The id of the fragment shader
 */
@property GLuint fragmentShaderId;

@end
