package com.dferreira.gameEngine.models.complexEntities;

import com.dferreira.commons.Vector3f;

/**
 * Represents one entity in the 3D world
 */
public class Entity {

	/**
	 * Keys: Have the name of the group The name of the material group for
	 * instance harm
	 */
	private final GenericEntity genericEntity;

	/* Position where the entity is */
	private Vector3f position;

	/* Rotation of the 3D model */
	private float rotX, rotY, rotZ;

	/* Scale of the model */
	private float scale;

	/**
	 * 
	 * Constructor of the entity to be render in the 3D world
	 * 
	 * @param genericEntity
	 *            reference to a generic entity with the description of the
	 *            model
	 * @param position
	 *            position where the model should be render
	 * @param rotX
	 *            Rotation of the model in the X axle
	 * @param rotY
	 *            Rotation of the model in the Y axle
	 * @param rotZ
	 *            Rotation of the model in the Z axle
	 * @param scale
	 *            Scale of the model
	 * 
	 */
	public Entity(GenericEntity genericEntity, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super();
		this.genericEntity = genericEntity;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}

	/**
	 * Increases the position of the model using for that the specified
	 * components
	 * 
	 * @param dx
	 *            X component to be increase
	 * @param dy
	 *            Y component to be increase
	 * @param dz
	 *            Z component to be increase
	 */
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	/**
	 * Increases the rotation of the model using for that the specified
	 * components
	 * 
	 * @param dx
	 *            X component to be increase
	 * @param dy
	 *            Y component to be increase
	 * @param dz
	 *            Z component to be increase
	 */
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	/**
	 * @return the position of the model
	 */
	public Vector3f getPosition() {
		return position;
	}

	/**
	 * 
	 * @param position
	 *            The position to be set
	 */
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	/**
	 * @return the rotation of the entity in the X axle
	 */
	public float getRotX() {
		return rotX;
	}

	/**
	 * @param rotX
	 *            the rotation of the entity in the X axle
	 */
	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	/**
	 * @return the rotation of the entity in the Y axle
	 */
	public float getRotY() {
		return rotY;
	}

	/**
	 * @param rotY
	 *            the rotation of the entity in the Y axle
	 */
	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	/**
	 * @return the rotation of the entity in the Z axle
	 */
	public float getRotZ() {
		return rotZ;
	}

	/**
	 * @param rotZ
	 *            the rotation of the entity in the Z axle
	 */
	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	/**
	 * @return the scale the scale of the entity
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}

	/**
	 * @return the genericEntity
	 */
	public GenericEntity getGenericEntity() {
		return genericEntity;
	}
}
