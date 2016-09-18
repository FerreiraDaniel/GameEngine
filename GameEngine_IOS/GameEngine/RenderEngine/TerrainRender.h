#import <Foundation/Foundation.h>
#import "GLTransformation.h"
#import "TerrainShaderManager.h"
#import "Terrain.h"

@interface TerrainRender : NSObject

- (id)init: (TerrainShaderManager*) aShader : (GLTransformation*) projectionMatrix;

/**
 * Render the terrains in the scene
 *
 * @param skyColor
 * 			Color of the sky
 * @param sun
 *            The source of light of the scene
 * @param viewMatrix
 *            View matrix to render the scene
 * @param terrains
 *            List of terrains of the scene
 */
- (void) render : (Vector3f*) skyColor : (Light*) sun : (GLTransformation*) viewMatrix : (NSMutableArray*) terrains;

@end
