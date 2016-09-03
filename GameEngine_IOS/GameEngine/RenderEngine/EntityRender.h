#import <Foundation/Foundation.h>
#import "EntityShaderManager.h"
#import "Entity.h"
#import "RawModel.h"
#import "GLTransformation.h"


@interface EntityRender : NSObject



- (id)init: (EntityShaderManager*) aShader : (GLTransformation*) projectionMatrix;

/**
 * Render the entities in the scene
 *
 * @param skyColor
 * 			Color of the sky
 *
 * @param sun
 *            The source of light of the scene
 * @param viewMatrix
 *            View matrix to render the scene
 * @param entities
 *            List of entities of the scene
 */
- (void) render : (Vector3f*) skyColor : (Light*) sun : (GLTransformation*) viewMatrix : (NSMutableDictionary*) entities;


@end
