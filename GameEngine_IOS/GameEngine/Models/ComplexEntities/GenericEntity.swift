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
     * The type of the object that the model is supporting
     */
    let objectType : TEntity;
    
    /**
     * Initiator of the entity to be render in the 3D world
     *
     * @param groupsOfMaterials
     *            HashMap with groups of materials to use in entity
     * @param objectType
     *            The type of the object that the model is supporting
     */
    public init(_ groupsOfMaterials : Dictionary<String, MaterialGroup>, _ objectType : TEntity) {
        self.id = String(GenericEntity.newId);
        GenericEntity.newId += 1;
        self.groupsOfMaterials = groupsOfMaterials;
        self.objectType = objectType;
    }
}
