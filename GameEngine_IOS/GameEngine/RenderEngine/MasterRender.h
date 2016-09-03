#import <Foundation/Foundation.h>
#import "EntityRender.h"
#import "EntityShaderManager.h"
#import "Camera.h"
#import "GLTransformation.h"
#import "Light.h"
#import "TerrainShaderManager.h"
#import "SkyBoxShaderManager.h"
#import "SkyBoxRender.h"

/**
 * Groups the entities in a hash map like this
 * the same entity will be just put in different positions
 */
@interface MasterRender : NSObject


/**
 Initiator of the master render
 */
- (id)init : (int) width : (int) height;

- (GLTransformation*) createProjectionMatrix;
- (GLTransformation*) updateCamera;

/**
 * Put the entities to process in the hash map dedicated to process entities
 * by group
 *
 * @param lEntities
 *            list of entities to get render in the next frame
 */
- (void) processEntities : (NSMutableArray*) lEntities;

- (void) processSkyBox : (SkyBox*) aSkyBox;

/**
 * Render the entire scene (Called by each frame)
 *
 * @param sun
 *            Sun of the scene
 */
- (void) render : (Light*) sun;
@end
