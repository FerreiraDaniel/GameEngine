package gameEngine.models.complexEntities;

import gameEngine.models.RawModel;

/**
 * Wrapper that besides of have the raw model also has the material to put in
 * the model
 */
public class RawModelMaterial {

	/**
	 * Raw model of the entity
	 */
	private RawModel rawModel;

	/**
	 * Reference to the material of the collection of faces
	 */
	private Material material;

	/**
	 * Constructor of the textured model
	 * 
	 * @param rawModel
	 *            Raw model of the entity
	 * @param material
	 *            Reference to the texture of the entity
	 */
	public RawModelMaterial(RawModel rawModel, Material material) {
		super();
		this.rawModel = rawModel;
		this.material = material;
	}

	/**
	 * @return the raw model of the entity
	 */
	public RawModel getRawModel() {
		return rawModel;
	}

	/**
	 * @return the description of the texture of the rawModel
	 */
	public Material getMaterial() {
		return material;
	}
}
