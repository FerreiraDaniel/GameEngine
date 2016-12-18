package com.dferreira.commons.waveFront;

/**
 * Set of all the prefixes supported by the material parser
 */
public class MtlPrefix {

	/**
	 * The prefix of a comment
	 */
	public final static String COMMENT = "#";
	
	/**
	 * The prefix of a new material
	 */
	public final static String NEW_MATERIAL = "newmtl";

	/**
	 * The prefix of weight of specular color
	 */
	public final static String WEIGHT_SPECULAR_COLOR = "Ns";

	/**
	 * The prefix of specular color
	 */
	public final static String SPECULAR_COLOR = "Ks";

	/**
	 * The prefix of ambient color
	 */
	public final static String AMBIENT_COLOR = "Ka";

	/**
	 * The prefix of diffuse color
	 */
	public final static String DIFFUSE_COLOR = "Kd";

	/**
	 * The prefix of emissive color
	 */
	public final static String EMISSIVE_COLOR = "Ke";

	/**
	 * The prefix of optical density
	 */
	public final static String OPTICAL_DENSITY = "Ni";

	/**
	 * The prefix of dissolve factor
	 */
	public final static String DISSOLVE_FACTOR = "d";

	/**
	 * The prefix of inverse of dissolve factor
	 */
	public final static String DISSOLVE_FACTOR_INVERTED = "Tr";

	/**
	 * The prefix of illumination model
	 */
	public final static String ILLUMINATION_MODEL = "illum";

	/**
	 * The prefix of transmission filter
	 */
	public final static String TRANSMISSION_FILTER = "Tf";

	/**
	 * The prefix of diffuse color texture map
	 */
	public final static String COLOR_TEXTURE_MAP = "map_Kd";

	/**
	 * The prefix of specular color texture map
	 */
	public final static String SPECULAR_COLOR_TEXTURE_MAP = "map_Ks";

	/**
	 * The prefix of ambient color texture map
	 */
	public final static String AMBIENT_COLOR_TEXTURE_MAP = "map_Ka";

	/**
	 * The prefix of bump texture map
	 */
	public final static String BUMP_TEXTURE_MAP = "map_Bump";

	/**
	 * The prefix of bump texture map
	 */
	public final static String BUMP_TEXTURE_MAP_V2 = "bump";

	/**
	 * The prefix of bump texture map
	 */
	public final static String BUMP_TEXTURE_MAP_V3 = "map_Disp";

	/**
	 * The prefix of opacity texture map
	 */
	public final static String OPACITY_MAP = "map_d";

}
