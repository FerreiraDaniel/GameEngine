import Foundation

/**
 * Responsible for creating the multiple terrains of the 3D world
 */
open class WorldTerrainsGenerator : NSObject {
    
    /**
     * Load the textures of the terrain
     *
     * @param loader
     *            the loader of the texture
     *
     * @return the textures package of the terrain
     */
    fileprivate static func getTexturedTerrain(_ loader : Loader) -> TerrainTexturesPack {
        let weightMapTextureId : Int = loader.loadTexture("blend_map");
        let backgroundTextureId : Int = loader.loadTexture("terrain");
        let mudTextureId : Int = loader.loadTexture("mud");
        let grassTextureId : Int = loader.loadTexture("terrain_grass");
        let pathTextureId : Int = loader.loadTexture("path");
        
        // Create the package of textures
        let texturesPackage : TerrainTexturesPack = TerrainTexturesPack();
        texturesPackage.weightMapTextureId = weightMapTextureId;
        texturesPackage.backgroundTextureId = backgroundTextureId;
        texturesPackage.mudTextureId = mudTextureId;
        texturesPackage.grassTextureId = grassTextureId;
        texturesPackage.pathTextureId = pathTextureId;
        
        return texturesPackage;
    }
    
    /**
     * Creates a terrain in a specified position
     *
     * @param texturedTerrain Packages with textures of the terrain
     * @param terrainModel    Model of the terrain to render
     * @param heights         The height of the vertices of the terrain
     * @param position        Position where is to put the terrain
     *
     * @return The terrain in the position specified
     */
    fileprivate static func getTerrain(_ texturedTerrain : TerrainTexturesPack, terrainModel : RawModel, heights : [[Float]], position : Vector3f) -> Terrain {
        let terrain : Terrain = Terrain(texturePack: texturedTerrain, rawModel : terrainModel, heights: heights, position : position);
        return terrain;
    }
    
    
    /**
     * The terrains of the 3D scene
     *
     * @param loader The loader in charge of loading the textures of the terrains
     *
     * @return list of terrains of the scene
     */
    open static func getTerrains(_ loader : Loader) -> Array<Terrain> {
        let texturedTerrain : TerrainTexturesPack = getTexturedTerrain(loader);
        let heightMap : TextureData = loader.getTextureData("heightmap");
        let terrain : TerrainShape = TerrainShape(heightMap: heightMap);
        let model : RawModel = loader.loadToVAO(terrain);
        
        let terrainPosition1 : Vector3f = Vector3f(x: 0.0, y: 0.0, z: -0.1);
        let terrain1 = getTerrain(texturedTerrain, terrainModel : model, heights: terrain.getHeights(), position : terrainPosition1);
        
        
        let terrains : Array<Terrain> = [terrain1];
        
        return terrains;
    }
    
}
