#import <Foundation/Foundation.h>
#import "Camera.h"
#import "GLTransformation.h"
#import "Light.h"
#import "TerrainShaderManager.h"
#import "SkyBox.h"
#import "TexturedModel.h"
#import "Entity.h"
#import "GameEngine-Swift.h"

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

/**
 * Put the terrains to process in the list of terrains to process
 *
 * @param lTerrains
 *            list of terrains to process
 */
- (void) processTerrains : (NSMutableArray*) lTerrains;

- (void) processSkyBox : (SkyBox*) aSkyBox;

/**
 * Render the entire scene (Called by each frame)
 *
 * @param sun
 *            Sun of the scene
 */
- (void) render : (Light*) sun;
@end
