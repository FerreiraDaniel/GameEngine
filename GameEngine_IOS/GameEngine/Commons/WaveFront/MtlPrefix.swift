/**
 * Set of all the prefixes supported by the material parser
 */
public class MtlPrefix {
    
    /**
     * The prefix of a comment
     */
    public static let COMMENT = "#";
    
    /**
     * The prefix of a new material
     */
    public static let NEW_MATERIAL = "newmtl";
    
    /**
     * The prefix of weight of specular color
     */
    public static let WEIGHT_SPECULAR_COLOR = "Ns";
    
    /**
     * The prefix of specular color
     */
    public static let SPECULAR_COLOR = "Ks";
    
    /**
     * The prefix of ambient color
     */
    public static let AMBIENT_COLOR = "Ka";
    
    /**
     * The prefix of diffuse color
     */
    public static let DIFFUSE_COLOR = "Kd";
    
    /**
     * The prefix of emissive color
     */
    public static let EMISSIVE_COLOR = "Ke";
    
    /**
     * The prefix of optical density
     */
    public static let OPTICAL_DENSITY = "Ni";
    
    /**
     * The prefix of dissolve factor
     */
    public static let DISSOLVE_FACTOR = "d";
    
    /**
     * The prefix of inverse of dissolve factor
     */
    public static let DISSOLVE_FACTOR_INVERTED = "Tr";
    
    /**
     * The prefix of illumination model
     */
    public static let ILLUMINATION_MODEL = "illum";
    
    /**
     * The prefix of transmission filter
     */
    public static let TRANSMISSION_FILTER = "Tf";
    
    /**
     * The prefix of diffuse color texture map
     */
    public static let COLOR_TEXTURE_MAP = "map_Kd";
    
    /**
     * The prefix of specular color texture map
     */
    public static let SPECULAR_COLOR_TEXTURE_MAP = "map_Ks";
    
    /**
     * The prefix of ambient color texture map
     */
    public static let AMBIENT_COLOR_TEXTURE_MAP = "map_Ka";
    
    /**
     * The prefix of bump texture map
     */
    public static let BUMP_TEXTURE_MAP = "map_Bump";
    
    /**
     * The prefix of bump texture map
     */
    public static let BUMP_TEXTURE_MAP_V2 = "bump";
    
    /**
     * The prefix of bump texture map
     */
    public static let BUMP_TEXTURE_MAP_V3 = "map_Disp";
    
    /**
     * The prefix of opacity texture map
     */
    public static let OPACITY_MAP = "map_d";
    
}
