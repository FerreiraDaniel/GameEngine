package com.dferreira.commons.wavefront;

/**
 * Set of all the prefixes supported by the object parser
 */
class OBJPrefix {

    /**
     * The prefix of a comment
     */
    public final static String COMMENT = "#";

    /**
     * Material to the wavefront entity
     */
    public static final String MATERIALS = "mtllib";

    /**
     * Uses a certain material to the faces
     */
    public static final String USE_MATERIAL = "usemtl";

    /**
     * Object names
     */
    public static final String OBJECT_NAME = "o";

    /**
     * Polygon groups
     */
    public static final String GROUP = "g";

    /**
     * Smooth shading
     */
    public static final String SMOOTH_SHADING = "s";

    /**
     * Vertices positions in the model
     */
    public static final String VERTEX = "v";

    /**
     * Normal vertices
     */
    public static final String NORMAL = "vn";

    /**
     * Vertices texture coordinates in our model
     */
    public static final String TEXTURE = "vt";

    /**
     * Face each face represents one triangle
     */
    public static final String FACE = "f";
}
