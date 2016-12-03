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
	 * 
	 * Constructor of the generic entity to be render in the 3D world
	 * 
	 * @param groupsOfMaterials
	 *            HashMap with groups of materials to use in entity
	 */
	public GenericEntity(HashMap<String, MaterialGroup> groupsOfMaterials) {
		super();
		this.groupsOfMaterials = groupsOfMaterials;
	}

	/**
	 * @return the groupsOfMaterials
	 */
	public HashMap<String, MaterialGroup> getGroupsOfMaterials() {
		return groupsOfMaterials;
	}

}