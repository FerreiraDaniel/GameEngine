package com.dferreira.gameEngine.models;

import com.dferreira.commons.Vector3f;

/**
 * Represents the the camera used by the user to see the 3D World
 */
public class Camera {

	private Vector3f position;

	/**
	 * Pitch is up and down (rotation around X-axis)
	 */
	private float pitch;

	/**
	 * Yaw is the angle when moving the head left and right (rotation around
	 * Y-axis)
	 */
	private float yaw;

	/**
	 * Roll, which we usually don't experience is when you tilt your head
	 * (rotation around Z-axis)
	 */
	private float roll;

	
	/**
	 * Initializer of the camera of the scene
	 */
	public Camera() {
		this.position = new Vector3f(0, 2.5f, 0);
	}


	/**
	 * @return the position position where the camera is
	 */
	public Vector3f getPosition() {
		return position;
	}

	/**
	 * @return the pitch (rotation around X-axis)
	 */
	public float getPitch() {
		return pitch;
	}
	
	/**
	 * Set the value of the pitch
	 * 
	 * @param pitch The rotation around X-axis
	 */
	protected void setPitch(float pitch) {
		this.pitch = pitch;
	}

	/**
	 * @return the yaw (rotation around Y-axis)
	 */
	public float getYaw() {
		return yaw;
	}
	
	/**
	 * Set the yaw of the camera
	 * 
	 * @param yaw (rotation around Y-axis)
	 */
	protected void setYaw(float yaw) {
		this.yaw = yaw;
	}

	/**
	 * @return the roll (rotation around Z-axis)
	 */
	public float getRoll() {
		return roll;
	}

}
