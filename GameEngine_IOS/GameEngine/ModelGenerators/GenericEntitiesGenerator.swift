import Foundation

/**
 * Provide generic methods to generate entities
 */
open class GenericEntitiesGenerator {
    
    
    /**
     * Load a textured model
     *
     * @param loader
     *            the loader of the texture
     * @param objName
     *            The name of the waveFront file without extension
     * @param hasTransparency
     *            Flag that indicates if has transparency or not
     * @param normalsPointingUp
     *            Indicates that all the normals of the object are pointing up
     *
     * @return the textured model loaded
     */
    internal static func getTexturedObj(_ loader : Loader, objName : String, hasTransparency : Bool, normalsPointingUp : Bool) -> Dictionary<String, MaterialGroup> {
        let shapes : [IShape] = OBJLoader.loadObjModel(objName);
        
        var groupsOfMaterials : Dictionary<String, MaterialGroup> = Dictionary<String, MaterialGroup>();
        
        for shape in shapes {
            let model : RawModel = loader.loadToVAO(shape);
            let material : Material? = loader.loadMaterial(shape);
            if(material != nil)
            {
                material!.shineDamper = 10.0
                material!.reflectivity = 1.0
                material!.hasTransparency = hasTransparency
                material!.normalsPointingUp = normalsPointingUp
            }
            let texturedModel : RawModelMaterial = RawModelMaterial(rawModel: model, material: material)
            
            var materials : [RawModelMaterial] = [RawModelMaterial]()
            materials.append(texturedModel)
            let materialGroup : MaterialGroup = MaterialGroup(materials)
            groupsOfMaterials[shape.getGroupName()!] = materialGroup
        }
        
        return groupsOfMaterials
    }
}
