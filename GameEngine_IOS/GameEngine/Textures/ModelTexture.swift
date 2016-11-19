import Foundation

/**
* Has all the required parameters to render one textured entity
*/
public class ModelTexture {
    
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
    
    /*
     * The atlas factor the end object will have
     * atlasFactor * atlasFactor = number of textures
     */
    var atlasFactor : Int;
    
    
    /**
    *Initiator of model texture
    *
    * @param textureId Identifier of the texture
    *
    */
    public init(_ textureId : Int) {
        self.textureId = textureId;
        self.shineDamper = 1.0;
        self.reflectivity = 0.0;
        self.atlasFactor = 1;	//By default will be one meaning that the object only have one texture
    }
    
}
