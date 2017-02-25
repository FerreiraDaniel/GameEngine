/**
 * Set of all the prefixes supported by the object parser
 */
open class OBJPrefix {
    
    /**
     * The prefix of a comment
     */
    open static let COMMENT = "#";
    
    /**
     * Material to the wavefront entity
     */
    open static let MATERIALS = "mtllib";
    
    /**
     * Uses a certain material to the faces
     */
    open static let USE_MATERIAL = "usemtl";
    
    /**
     * Object names
     */
    open static let OBJECT_NAME = "o";
    
    /**
     * Polygon groups
     */
    open static let GROUP = "g";
    
    /**
     * Smooth shading
     */
    open static let SMOOTH_SHADING = "s";
    
    /**
     * Vertices positions in the model
     */
    open static let VERTEX = "v";
    
    /**
     * Normal vertices
     */
    open static let NORMAL = "vn";
    
    /**
     * Vertices texture coordinates in our model
     */
    open static let TEXTURE = "vt";
    
    /**
     * Face each face represents one triangle
     */
    open static let FACE = "f";
}
