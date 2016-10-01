import Foundation

/**
* Responsible for creating the multiple terrains of the 3D world
*/
public class WorldTerrainsGenerator : NSObject {

    /**
    * Load the textures of the terrain
    *
    * @param loader
    *            the loader of the texture
    *
    * @return the textures package of the terrain
    */
    private static func getTexturedTerrain(loader : Loader) -> TerrainTexturesPack {
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
    *
    * @param texturedTerrain
    *            Model of the terrain to render
    * @param position
    *            Position where is to put the terrain
    
    * @return the terrain loaded
    */
    private static func getTerrain(texturedTerrain : TerrainTexturesPack, terrainModel : RawModel, position : Vector3f) -> Terrain {
        let terrain : Terrain = Terrain(texturePack: texturedTerrain, rawModel : terrainModel, position : position);
        return terrain;
    }
    
    
/**
* The terrains of the 3D scene
*
* @param loader The loader in charge of loading the textures of the terrains
*
* @return list of terrains of the scene
*/
    public static func getTerrains(loader : Loader) -> Array<Terrain> {
        let texturedTerrain : TerrainTexturesPack = getTexturedTerrain(loader);
        let terrain : IShape = TerrainShape();
        let model : RawModel = loader.loadToVAO(terrain);
        
        let terrainPosition1 : Vector3f = Vector3f(0.0, 0.0, -0.1);
        let terrain1 = getTerrain(texturedTerrain, terrainModel : model, position : terrainPosition1);

        let terrainPosition2 : Vector3f = Vector3f(1.0, 0.0, -0.1);
        let terrain2 = getTerrain(texturedTerrain, terrainModel : model, position : terrainPosition2);
        
        let terrains : Array<Terrain> = [terrain1, terrain2];
        
        return terrains;
    }
    
}
