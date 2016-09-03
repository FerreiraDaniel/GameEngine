#import <Foundation/Foundation.h>
#import "SkyBoxShaderManager.h"
#import "SkyBox.h"

@interface SkyBoxRender : NSObject

- (id)init: (SkyBoxShaderManager*) aShader : (GLTransformation*) projectionMatrix;
- (void) render : (GLTransformation*) viewMatrix : (SkyBox*) skyBox;

@end
