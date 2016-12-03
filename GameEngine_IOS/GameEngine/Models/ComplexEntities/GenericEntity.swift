import Foundation

/**
 * Generic entity without any specific of a determined entity
 */
public class GenericEntity  {
    private static var newId : Int = 0
    
    /**
     * Identifier of the generic entity
     */
    var id : Int;
    
    /*3D model to be render*/
    var model : RawModelMaterial
    
    
    /**
     * Initiator of the entity to be render in the 3D world
     *
     * @param model          Textured model
     */
    public init(_ model : RawModelMaterial) {
        self.id = GenericEntity.newId;
        GenericEntity.newId += 1;
        self.model = model;
    }
}
