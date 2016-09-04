#import <Foundation/Foundation.h>
#import "IShape.h"
#import "RenderConstants.h"

#define SIZE   800.0f

/* Number of vertices in each side of the terrain */
static const int  TERRAIN_SHAPE_VERTEX_COUNT = 128;
static const int  TERRAIN_SHAPE_COUNT = 16384; //count * count
static const int  TERRAIN_VERTICES_LENGTH = 49152; //3 * count * count
static const int  TERRAIN_NORMALS_LENGTH = 49152; //3 * count * count
static const int  TERRAIN_TEXTURES_LENGTH = 32768; //2 *count * count
static const int  TERRAIN_INDICES_LENGTH = 96774; //6 * (count -1 ) * (count -1)



/**
 * Represents one terrain in the 3D world
 */
@interface TerrainShape   : NSObject <IShape>

/**
 * Initiator of the terrain shape
 */
- (id)init;


@end
