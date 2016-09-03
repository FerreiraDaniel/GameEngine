#import <Foundation/Foundation.h>
#import "ShaderManager.h"
#import "SkyBoxShaderConstants.h"
#import <OpenGLES/ES2/glext.h>

@interface SkyBoxShaderManager  : ShaderManager

- (void) loadProjectionMatrix : (GLTransformation*) matrix;
- (void) loadViewMatrix : (GLTransformation*) matrix;


@end
