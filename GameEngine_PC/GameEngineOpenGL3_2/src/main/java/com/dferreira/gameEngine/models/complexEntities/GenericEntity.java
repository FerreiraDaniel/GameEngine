package com.dferreira.gameEngine.models.complexEntities;

import java.util.HashMap;

import com.dferreira.commons.generic_resources.ModelEnum;

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
	private final ModelEnum objectType;

	/**
	 * 
	 * Constructor of the generic entity to be render in the 3D world
	 * 
	 * @param groupsOfMaterials
	 *            HashMap with groups of materials to use in entity
	 * @param objectType
	 *            The type of the object that the model is supporting
	 */
	public GenericEntity(HashMap<String, MaterialGroup> groupsOfMaterials, ModelEnum objectType) {
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
	public ModelEnum getObjectType() {
		return objectType;
	}

}