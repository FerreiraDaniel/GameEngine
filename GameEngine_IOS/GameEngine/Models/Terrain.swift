import Foundation

/**
** The model to the terrain entity
*/
public class Terrain : NSObject {
    
    private let SIZE : Float = 500
    
    /**
    * Position of the terrain in the x-axle
    */
    var x : Float;
    
    /**
    * Position of the terrain in the y-axle
    */
    var y : Float;
    
    /**
    * Position of the terrain in the z-axle
    */
    var z : Float;
    
    /**
    * RawModel of the terrain
    */
    var model : RawModel;
    
    /**
    * The different textures of the terrain
    */
    var texturePack : TerrainTexturesPack;
    
    /**
    * The constructor of the terrain entity
    *
    * @param texturePack		The identifiers of the textures to the terrain
    * @param rawModel			The model of the terrain
    * @param position			Position where the terrain will be put in
    */
    public init(texturePack : TerrainTexturesPack, rawModel : RawModel, position : Vector3f) {
        self.texturePack = texturePack;
        self.model = rawModel;
        self.x = position.x * SIZE;
        self.y = position.y * SIZE;
        self.z = position.z * SIZE;

    }
}
