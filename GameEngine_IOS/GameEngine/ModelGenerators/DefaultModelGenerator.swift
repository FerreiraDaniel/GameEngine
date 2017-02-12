import Foundation

/**
 * Define the default properties that the element should obey
 */
public class DefaultModelGenerator : NSObject {
    
    /**
     * The name of the .obj that represents the model
     */
    var objectType : TEntity;
    
    /**
     * The name of the .obj that represents the model
     */
    var objectName : String;
    
    
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
    
    
    public init(objectType : TEntity,objectName : String, scale : Float, hasTransparency : Bool, normalsPointingUp : Bool) {
        self.objectType = objectType
        self.objectName = objectName;
        self.scale = scale
        self.hasTransparency = hasTransparency
        self.normalsPointingUp = normalsPointingUp
    }
}
