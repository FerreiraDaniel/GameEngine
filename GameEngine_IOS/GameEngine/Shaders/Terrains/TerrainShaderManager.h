#import <Foundation/Foundation.h>
#import "ShaderManager.h"
#import "Light.h"
#import "TerrainShaderConstants.h"
#import <OpenGLES/ES2/glext.h>

/**
 * Texture unit that was bind with glBindTexture GL_TEXTURE0
 */
static const int TEXTURE_UNIT0 = 0;

/**
 * Texture unit that was bind with glBindTexture GL_TEXTURE1
 */
static const int TEXTURE_UNIT1 = 1;

/**
 * Texture unit that was bind with glBindTexture GL_TEXTURE3
 */
static const int TEXTURE_UNIT2 = 2;

/**
 * Texture unit that was bind with glBindTexture GL_TEXTURE3
 */
static const int TEXTURE_UNIT3 = 3;

/**
 * Texture unit that was bind with glBindTexture GL_TEXTURE4
 */
static const int TEXTURE_UNIT4 = 4;

@interface TerrainShaderManager : ShaderManager

/**
 * Associate the shader variables with textures that were defined in the
 * bind of textures
 */
- (void) connectTextureUnits;

- (void) loadProjectionMatrix : (GLTransformation*) matrix;
- (void) loadViewMatrix : (GLTransformation*) matrix;
- (void) loadTransformationMatrix: (GLTransformation*) matrix;

- (void) loadLight : (Light*) light;
- (void) loadShineVariables : (float) damper : (float) reflectivity;

- (void)  loadSkyColor : (Vector3f*) skyColor;



@end
