#import <Foundation/Foundation.h>
#import "RawModel.h"
#import "TerrainTexturesPack.h"
#import "Vector3f.h"
#import "TerrainShape.h"

/**
 * The model to the terrain entity
 */
@interface Terrain : NSObject
/**
 * Position of the terrain in the x-axle
 */
@property (readonly) float x;

/**
 * Position of the terrain in the y-axle
 */
@property (readonly)  float y;

/**
 * Position of the terrain in the z-axle
 */
@property  (readonly) float z;

/**
 * RawModel of the terrain
 */
@property  (readonly) RawModel *model;

/**
 * The different textures of the terrain
 */
@property  (readonly) TerrainTexturesPack *texturePack;

/**
 * The constructor of the terrain entity
 *
 * @param aTexturePack		The identifiers of the textures to the terrain
 * @param aRawModel			The model of the terrain
 * @param aPosition			Position where the terrain will be put in
 */
- (id)init : (TerrainTexturesPack*) aTexturePack : (RawModel*) aRawModel : (Vector3f*) aPosition;
@end
