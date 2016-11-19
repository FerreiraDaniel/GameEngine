import Foundation

/**
* Define the default properties that the element should obey
*/
public class DefaultModelGenerator : NSObject {
    
    /**
    * The name of the .obj that represents the model
    */
    var objectName : String;
    
    /**
    * The name of the .png that represents the texture of the model
    */
    var textureName : String;
    
    /**
    * The scale of the model
    */
    var scale : Float;
    
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
     * Atlas factor if bigger that one means that the object as multiple textures in the same image
     */
    var atlasFactor : Int;
    
    public init(objectName : String, textureName : String, scale : Float, hasTransparency : Bool, normalsPointingUp : Bool, atlasFactor : Int) {
        self.objectName = objectName;
        self.textureName = textureName
        self.scale = scale
        self.hasTransparency = hasTransparency
        self.normalsPointingUp = normalsPointingUp
        self.atlasFactor = atlasFactor
    }
}
