import Foundation

/**
 * Has all the required parameters to render one textured entity
 */
public class Material {
    
    /**
     * The identifier of the texture
     */
    var textureId : Int
    
    /**
     * The weight of the texture for the ambient color of the object
     */
    var textureWeight : Float
    
    /**
     * constant color of the ambient component
     */
    var diffuseColor : ColorRGBA
    
    /**
     * How damped the shine is
     */
    var shineDamper : Float
    
    /**
     * How reflective the model is
     */
    var reflectivity : Float
    
    /**
     * Indicates if the material has transparency or not
     */
    var hasTransparency : Bool;
    
    
    /**
     * Indicate that all the normals of the material are going to point up (in the
     * same direction
     */
    var normalsPointingUp : Bool;
    
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
        self.hasTransparency = false
        self.normalsPointingUp = false
        self.textureWeight = 0.0
        self.diffuseColor = ColorRGBA(r: 1.0, g: 1.0, b: 1.0, a: 0.0)
    }
    
}
