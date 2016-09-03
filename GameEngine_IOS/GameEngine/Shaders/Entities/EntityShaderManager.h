#import <Foundation/Foundation.h>
#import "ShaderManager.h"
#import "EntityShaderConstants.h"
#import <OpenGLES/ES2/glext.h>
#import "Light.h"


@interface EntityShaderManager : ShaderManager


- (void) loadProjectionMatrix : (GLTransformation*) matrix;
- (void) loadViewMatrix : (GLTransformation*) matrix;
- (void) loadTransformationMatrix: (GLTransformation*) matrix;

- (void) loadLight : (Light*) light;
- (void) loadShineVariables : (float) damper : (float) reflectivity;
- (void)  loadNormalsPointingUp : (Boolean) normalsPointingUp;
- (void)  loadSkyColor : (Vector3f*) skyColor;


@end

