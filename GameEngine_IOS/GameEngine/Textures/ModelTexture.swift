import Foundation

/**
* Has all the required parameters to render one textured entity
*/
public class ModelTexture : NSObject {
    
    /**
    * The identifier of the texture
    */
    var textureId : Int
    
    /**
    * How damped the shine is
    */
    var shineDamper : Float
    
    /**
    * How reflective the model is
    */
    var reflectivity : Float
    
    
    /**
    Initiator of model texture
    *
    * @param textureId Identifier of the texture
    *
    */
    public init(_ textureId : Int) {
        self.textureId = textureId;
        self.shineDamper = 1.0;
        self.reflectivity = 0.0;
    }
    
}
