import Foundation

/**
 * Provide generic methods to generate entities
 */
public class GenericEntitiesGenerator {
    /**
     * Load a textured model
     *
     * @param loader
     *            the loader of the texture
     *
     * @return the textured model of the dragon
     */
    public static func getTexturedObj(loader : Loader, objName : String, textureName : String, hasTransparency : Bool, normalsPointingUp : Bool) -> TexturedModel  {
        let shape : IShape = OBJLoader.loadObjModel(objName);
        
        let rawModel : RawModel = loader.loadToVAO(shape);
        
        let textureId : Int = loader.loadTexture(textureName)
        
        let texture : ModelTexture = ModelTexture(textureId);
        texture.shineDamper = 10.0
        texture.reflectivity = 1.0
        
        //TexturedModel
        let texturedModel : TexturedModel = TexturedModel(rawModel: rawModel, texture: texture, hasTransparency: hasTransparency, normalsPointingUp: normalsPointingUp);
        
        return texturedModel;
    }
}
