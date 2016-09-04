#import <Foundation/Foundation.h>
#import "TerrainTexturesPack.h"
#import "Loader.h"
#import "Terrain.h"

/**
 * Responsible for creating the multiple terrains of the 3D world
 */
@interface WorldTerrainsGenerator : NSObject

/**
 * The terrains of the 3D scene
 *
 * @param loader The loader in charge of loading the textures of the terrains
 *
 * @return list of terrains of the scene
 */
+ (NSMutableArray*) getTerrains : (Loader*) loader;

@end
