import Foundation

/**
 * Generic entity without any specific of a determined entity
 */
public class GenericEntity  {
    private static var newId : Int = 0
    
    /**
     * Identifier of the generic entity
     */
    let id : String;
    
    /**
     * Keys: Have the name of the group The name of the material group for
     * instance harm
     */
    let groupsOfMaterials : Dictionary<String, MaterialGroup>;
    
    
    /**
     * Initiator of the entity to be render in the 3D world
     *
     * @param model          Textured model
     */
    public init(_ groupsOfMaterials : Dictionary<String, MaterialGroup>) {
        self.id = String(GenericEntity.newId);
        GenericEntity.newId += 1;
        self.groupsOfMaterials = groupsOfMaterials;
    }
}
