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
     * @param objName
     *            The name of the waveFront file without extension
     * @param texureName
     *            The name of the image file without extension
     * @param hasTransparency
     *            Flag that indicates if has transparency or not
     * @param normalsPointingUp
     *            Indicates that all the normals of the object are pointing up
     * @param atlasFactor
     * 			The atlas factor of the texture
     *
     * @return the textured model of the dragon
     */
    public static func getTexturedObj(loader : Loader, objName : String, textureName : String, hasTransparency : Bool, normalsPointingUp : Bool, atlasFactor : Int) -> TexturedModel  {
        let shape : IShape = OBJLoader.loadObjModel(objName);
        
        let rawModel : RawModel = loader.loadToVAO(shape);
        
        let textureId : Int = loader.loadTexture(textureName)
        
        let texture : ModelTexture = ModelTexture(textureId);
        texture.shineDamper = 10.0
        texture.reflectivity = 1.0
        texture.atlasFactor = atlasFactor
        
        //TexturedModel
        let texturedModel : TexturedModel = TexturedModel(rawModel: rawModel, texture: texture, hasTransparency: hasTransparency, normalsPointingUp: normalsPointingUp);
        
        return texturedModel;
    }
}
