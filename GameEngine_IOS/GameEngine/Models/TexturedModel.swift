import Foundation

/**
* Wrapper that besides of have the raw model also has the texture to put in the model
*/
public class TexturedModel : NSObject {
    
    private static var newId : Int = 0
    
    /**
    * Identifier of the textured model
    */
    var id : Int;
    
    /**
    * Raw model of the entity
    */
    var rawModel : RawModel;
    
    /**
    * Reference to the texture of the entity
    */
    var texture : ModelTexture;
    
    /**
    * Indicates if the model has transparency or not
    */
    var hasTransparency : Bool;
    
    
    /**
    * Indicate that all our normals of the object are going to point up (in the same
    * direction
    */
    var normalsPointingUp : Bool;
    
    /**
    * Initiator of the textured model
    *
    * @param aRawModel			Raw model of the entity
    * @param aTexture			Reference to the texture of the entity
    * @param aHasTransparency	Indicates if the model has transparency or not not
    * @param aNormalsPointingUp	Indicates if the normals of the model should point up
    */
    public init(rawModel : RawModel, texture : ModelTexture, hasTransparency : Bool, normalsPointingUp : Bool) {
        self.id = TexturedModel.newId++;
        self.rawModel = rawModel;
        self.texture = texture
        self.hasTransparency = hasTransparency
        self.normalsPointingUp = normalsPointingUp
    }
    
    /**
    * Initiator of the textured model
    
    * @param aRawModel	Raw model of the entity
    * @param aTexture	Reference to the texture of the entity
    */
    public init(rawModel : RawModel, texture : ModelTexture) {
        self.id = TexturedModel.newId++;
        self.rawModel = rawModel;
        self.texture = texture
        self.hasTransparency = false
        self.normalsPointingUp = false
    }
}
