#import "WorldTerrainsGenerator.h"
#import "Cube.h"

@implementation WorldTerrainsGenerator

const int NUMBER_OF_TERRAINS = 2;

/**
 * Load the textures of the terrain
 *
 * @param loader
 *            the loader of the texture
 *
 * @return the textures package of the terrain
 */
+ (TerrainTexturesPack*) getTexturedTerrain : (Loader*) loader {
    int weightMapTextureId = [loader loadTexture : @"blend_map"];
    int backgroundTextureId = [loader loadTexture : @"terrain"];
    int mudTextureId = [loader loadTexture : @"mud"];
    int grassTextureId = [loader loadTexture : @"terrain_grass"];
    int pathTextureId = [loader loadTexture : @"path" ];
    
    // Create the package
    TerrainTexturesPack* texturesPackage = [[TerrainTexturesPack alloc] init];
                         texturesPackage.weightMapTextureId = weightMapTextureId;
                         texturesPackage.backgroundTextureId = backgroundTextureId;
                         texturesPackage.mudTextureId = mudTextureId;
                         texturesPackage.grassTextureId = grassTextureId;
                         texturesPackage.pathTextureId = pathTextureId;
    
    return texturesPackage;
}

/**
 *
 * @param texturedTerrain
 *            Model of the terrain to render
 * @param position
 *            Position where is to put the terrain
 
 * @return the terrain loaded
 */
+ (Terrain*) getTerrain : (TerrainTexturesPack*) texturedTerrain : (RawModel*) terrainModel : (Vector3f*) position {
    Terrain* terrain = [[Terrain alloc] init : texturedTerrain : terrainModel : position];
    return terrain;
}

/**
 * The terrains of the 3D scene
 *
 * @param loader The loader in charge of loading the textures of the terrains
 *
 * @return list of terrains of the scene
 */
+ (NSMutableArray*) getTerrains : (Loader*) loader {
    TerrainTexturesPack* texturedTerrain = [self getTexturedTerrain : loader];
    id<IShape> terrain = [[TerrainShape alloc] init];
    RawModel* model = [loader loadToVAO: terrain];

    
    NSMutableArray* terrains = [[NSMutableArray alloc] initWithCapacity: NUMBER_OF_TERRAINS];
    
    Vector3f* terrainPosition1 = [[Vector3f alloc] init: 0.0f : 0.0f : -0.1f];
    Terrain* terrain1 = [self getTerrain: texturedTerrain : model : terrainPosition1];
    Vector3f* terrainPosition2 = [[Vector3f alloc] init: 1.0f : 0.0f : -0.1f];
    Terrain* terrain2 = [self getTerrain: texturedTerrain : model : terrainPosition2];
    
    [terrains addObject: terrain1];
    [terrains addObject: terrain2];
    
    return terrains;
}


@end
