/**
 * Set of all the prefixes supported by the object parser
 */
public class OBJPrefix {
    
    /**
     * The prefix of a comment
     */
    public static let COMMENT = "#";
    
    /**
     * Material to the wavefront entity
     */
    public static let MATERIALS = "mtllib";
    
    /**
     * Uses a certain material to the faces
     */
    public static let USE_MATERIAL = "usemtl";
    
    /**
     * Object names
     */
    public static let OBJECT_NAME = "o";
    
    /**
     * Polygon groups
     */
    public static let GROUP = "g";
    
    /**
     * Smooth shading
     */
    public static let SMOOTH_SHADING = "s";
    
    /**
     * Vertices positions in the model
     */
    public static let VERTEX = "v";
    
    /**
     * Normal vertices
     */
    public static let NORMAL = "vn";
    
    /**
     * Vertices texture coordinates in our model
     */
    public static let TEXTURE = "vt";
    
    /**
     * Face each face represents one triangle
     */
    public static let FACE = "f";
}
