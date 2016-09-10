package com.dferreira.game_engine.models;

import com.dferreira.commons.Vector3f;
import com.dferreira.game_controller.GamePad;

/**
 * Represents the the camera used by the user to see the
 * 3D World
 */
public class Camera {

	private final static float  ANGLE_OF_ROTATION = 0.4f;
	private final static float  STEP = 0.4f;

	/**
	 * Position where the camera is in the 3D world
	 */
	private Vector3f position;
	
	/**
	 * Pitch is up and down (rotation around X-axis)
	 */
	private float pitch;
	
	/**
	 * Yaw is the angle when moving the head left and right (rotation around Y-axis)
	 */
	private float yaw;
	
	/**
	 * Roll, which we usually don't experience is when you tilt your head (rotation around Z-axis)
	 */
	private float roll;
	
	/**
	 * 
	 */
	public Camera(float initX, float initY, float initZ) {
		this.position = new Vector3f(initX, initY, initZ);
	}
	
	/**
	 * Read the keyboard keys pressed by the used and updates the 
	 * position of the camera
	 */
	public void move() {

		//Looks up
		if(GamePad.isKeyDown(GamePad.KEY_UP)) {
			pitch += ANGLE_OF_ROTATION;
		}

		//Looks down
		if(GamePad.isKeyDown(GamePad.KEY_DOWN)) {
			pitch -= ANGLE_OF_ROTATION;
		}

		//Looks left
		if(GamePad.isKeyDown(GamePad.KEY_LEFT)) {
			yaw += ANGLE_OF_ROTATION;
		}

		//Looks right
		if(GamePad.isKeyDown(GamePad.KEY_RIGHT)) {
			yaw -= ANGLE_OF_ROTATION;
		}
	}
	
	/**
	 * Rotate the camera that the user sees
	 */
	public void rotate() {
		double pitchR = Math.toRadians(pitch);
		double yawR = Math.toRadians(yaw);

		//Accelerates
		if(GamePad.isKeyDown(GamePad.KEY_X)) {
			position.x += (-STEP * Math.sin(yawR));
			position.y += (+STEP * Math.sin(pitchR));
			position.z += (-STEP * Math.sin(pitchR)) + (-STEP * Math.cos(yawR));
		}
		
		/*
		if(GamePad.isKeyDown(GamePad.KEY_TRIANGLE)) {
		}

		if(GamePad.isKeyDown(GamePad.KEY_SQUARE)) {
		}*/
		
		//Go backwards
		if(GamePad.isKeyDown(GamePad.KEY_CIRCLE)) {
			position.x += (+STEP * Math.sin(yawR));
			position.y += (-STEP * Math.sin(pitchR));
			position.z += (+STEP * Math.sin(pitchR)) + (+STEP * Math.cos(yawR));
		}
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
	 * @return the yaw (rotation around Y-axis)
	 */
	public float getYaw() {
		return yaw;
	}

	/**
	 * @return the roll (rotation around Z-axis)
	 */
	public float getRoll() {
		return roll;
	}
	
	
}
