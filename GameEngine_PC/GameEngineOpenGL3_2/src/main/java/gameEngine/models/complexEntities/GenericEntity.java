package gameEngine.models.complexEntities;

import java.util.HashMap;

/**
 * Generic entity without any specific of a determined entity
 */
public class GenericEntity {

	/**
	 * Keys: Have the name of the group The name of the material group for
	 * instance harm
	 */
	private final HashMap<String, MaterialGroup> groupsOfMaterials;

	/**
	 * The type of the object that the model is supporting
	 */
	private TEntity objectType;

	/**
	 * 
	 * Constructor of the generic entity to be render in the 3D world
	 * 
	 * @param groupsOfMaterials
	 *            HashMap with groups of materials to use in entity
	 * @param objectType
	 *            The type of the object that the model is supporting
	 */
	public GenericEntity(HashMap<String, MaterialGroup> groupsOfMaterials, TEntity objectType) {
		super();
		this.groupsOfMaterials = groupsOfMaterials;
		this.objectType = objectType;
	}

	/**
	 * @return the groups of materials
	 */
	public HashMap<String, MaterialGroup> getGroupsOfMaterials() {
		return groupsOfMaterials;
	}

	/**
	 * @return The type of the object that the model is supporting
	 */
	public TEntity getObjectType() {
		return objectType;
	}

	
}