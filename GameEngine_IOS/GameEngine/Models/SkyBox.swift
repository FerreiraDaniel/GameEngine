import Foundation

/**
* Represents a box with sky textures
*/
public class SkyBox  {
    
    /**
    * RawModel of the skyBox
    */
    var model : RawModel
    
    /**
    * The identifier of the sky box cubic texture
    */
    var textureId : Int
    
    /**
    * The initiator of the skyBox
    *
    * @param aTextureId
    *            the Identifier of the texture of the sky
    * @param aModel
    *            The model of the sky box
    */
    public init(textureId : Int, model : RawModel) {
        self.textureId = textureId;
        self.model = model;
    }
}
