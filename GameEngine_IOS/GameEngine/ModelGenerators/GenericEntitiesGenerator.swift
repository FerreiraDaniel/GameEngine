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
     *
     * @return the textured model loaded
     */
    public static func getTexturedObj(loader : Loader, objName : String, textureName : String, hasTransparency : Bool, normalsPointingUp : Bool) -> RawModelMaterial  {
        let shape : IShape = OBJLoader.loadObjModel(objName);
        
        let rawModel : RawModel = loader.loadToVAO(shape);
        
        let textureId : Int = loader.loadTexture(textureName)
        
        let material : Material = Material(textureId);
        material.shineDamper = 10.0
        material.reflectivity = 1.0
        material.hasTransparency = hasTransparency
        material.normalsPointingUp = normalsPointingUp
        
        //TexturedModel
        let texturedModel : RawModelMaterial = RawModelMaterial(rawModel: rawModel, material: material);
        
        return texturedModel;
    }
}
