package com.dferreira.game_engine.models;

import com.dferreira.commons.Vector3f;

/**
 * Represents the the camera used by the user to see the
 * 3D World
 */
public class Camera {

    /**
     * Position where the camera is in the 3D world
     */
    private final Vector3f position;

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
    @SuppressWarnings("unused")
    private float roll;

    /**
     * Initializer of the camera of the scene
     */
    public Camera() {
        this.position = new Vector3f(0.0f, 2.5f, 0.0f);
    }

    /**
     * Read the keyboard keys pressed by the used and updates the
     * position of the camera
     */
    /*
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
	}*/

    /**
     * Rotate the camera that the user sees
     */
	/*
	public void rotate() {
		double pitchR = Math.toRadians(pitch);
		double yawR = Math.toRadians(yaw);

		//Accelerates
		//if(GamePad.isKeyDown(GamePad.KEY_X)) {
			position.x += (-STEP * Math.sin(yawR));
			position.y += (+STEP * Math.sin(pitchR));
			position.z += (-STEP * Math.sin(pitchR)) + (-STEP * Math.cos(yawR));
		//}
		

		if(GamePad.isKeyDown(GamePad.KEY_TRIANGLE)) {
		}

		if(GamePad.isKeyDown(GamePad.KEY_SQUARE)) {
		}
		
		//Go backwards
		//if(GamePad.isKeyDown(GamePad.KEY_CIRCLE)) {
			position.x += (+STEP * Math.sin(yawR));
			position.y += (-STEP * Math.sin(pitchR));
			position.z += (+STEP * Math.sin(pitchR)) + (+STEP * Math.cos(yawR));
		//}
	}*/


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
    @SuppressWarnings("unused")
    public float getRoll() {
        return roll;
    }


}
