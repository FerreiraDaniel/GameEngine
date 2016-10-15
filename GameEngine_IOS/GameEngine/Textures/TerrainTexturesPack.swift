import Foundation

/**
* Contains the identifiers to the several textures of a terrain
*/
public class TerrainTexturesPack {
    
    /**
    * The identifier of the weight map texture
    */
    var weightMapTextureId : Int;
    
    /**
    * The identifier of the background texture
    */
    var backgroundTextureId : Int;
    
    /**
    * Identifier of the mud texture
    */
    var mudTextureId : Int;
    
    /**
    * Identifier of the grass texture
    */
    var grassTextureId : Int;
    
    /**
    * Identifier of the path texture
    */
    var pathTextureId : Int;
    
    /**
    * Initializes the terrain texture package with all textures with identifier equals to zero
    */
    public init() {
        self.weightMapTextureId = 0;
        self.backgroundTextureId = 0;
        self.mudTextureId = 0;
        self.grassTextureId = 0;
        self.pathTextureId = 0;
    }
    
}