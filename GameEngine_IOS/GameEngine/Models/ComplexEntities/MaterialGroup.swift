import Foundation

/**
 * Contains a group of materials to be render in the scene As part of the the
 */
public class MaterialGroup {
    
    /**
     * List of rawModel materials that compose the group
     */
    let materials : Array<RawModelMaterial>;
    
    /**
     * @param materials List of the materials that make part of the group
     */
    public init(_ materials : Array<RawModelMaterial>) {
        self.materials = materials;
    }
}
