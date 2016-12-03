import Foundation

/**
* Wrapper that besides of have the raw model also has the texture to put in the model
*/
public class RawModelMaterial : NSObject {
    

    
    /**
    * Raw model of the entity
    */
    var rawModel : RawModel;
    
    /**
    * Reference to the texture of the entity
    */
    var material : Material;
    
    
    /**
    * Initiator of the textured model
    
    * @param rawModel	Raw model of the entity
    * @param material	Reference to the texture of the entity
    */
    public init(rawModel : RawModel, material : Material) {
        self.rawModel = rawModel;
        self.material = material
    }
}
