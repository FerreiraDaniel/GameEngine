/**
 * Set of all the prefixes supported by the material parser
 */
open class MtlPrefix {
    
    /**
     * The prefix of a comment
     */
    open static let COMMENT = "#";
    
    /**
     * The prefix of a new material
     */
    open static let NEW_MATERIAL = "newmtl";
    
    /**
     * The prefix of weight of specular color
     */
    open static let WEIGHT_SPECULAR_COLOR = "Ns";
    
    /**
     * The prefix of specular color
     */
    open static let SPECULAR_COLOR = "Ks";
    
    /**
     * The prefix of ambient color
     */
    open static let AMBIENT_COLOR = "Ka";
    
    /**
     * The prefix of diffuse color
     */
    open static let DIFFUSE_COLOR = "Kd";
    
    /**
     * The prefix of emissive color
     */
    open static let EMISSIVE_COLOR = "Ke";
    
    /**
     * The prefix of optical density
     */
    open static let OPTICAL_DENSITY = "Ni";
    
    /**
     * The prefix of dissolve factor
     */
    open static let DISSOLVE_FACTOR = "d";
    
    /**
     * The prefix of inverse of dissolve factor
     */
    open static let DISSOLVE_FACTOR_INVERTED = "Tr";
    
    /**
     * The prefix of illumination model
     */
    open static let ILLUMINATION_MODEL = "illum";
    
    /**
     * The prefix of transmission filter
     */
    open static let TRANSMISSION_FILTER = "Tf";
    
    /**
     * The prefix of diffuse color texture map
     */
    open static let COLOR_TEXTURE_MAP = "map_Kd";
    
    /**
     * The prefix of specular color texture map
     */
    open static let SPECULAR_COLOR_TEXTURE_MAP = "map_Ks";
    
    /**
     * The prefix of ambient color texture map
     */
    open static let AMBIENT_COLOR_TEXTURE_MAP = "map_Ka";
    
    /**
     * The prefix of bump texture map
     */
    open static let BUMP_TEXTURE_MAP = "map_Bump";
    
    /**
     * The prefix of bump texture map
     */
    open static let BUMP_TEXTURE_MAP_V2 = "bump";
    
    /**
     * The prefix of bump texture map
     */
    open static let BUMP_TEXTURE_MAP_V3 = "map_Disp";
    
    /**
     * The prefix of opacity texture map
     */
    open static let OPACITY_MAP = "map_d";
    
}
