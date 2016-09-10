package gameEngine.models;

import org.lwjgl.input.Keyboard;

import com.dferreira.commons.Vector3f;

/**
 * Represents the the camera used by the user to see the 3D World
 */
public class Camera {

	private final static float ANGLE_OF_ROTATION = 0.4f;
	private final static float STEP = 0.2f;

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
	 * 
	 */
	public Camera() {
		this.position = new Vector3f(0, 1.0f, 0);
	}

	/**
	 * Read the keyboard keys pressed by the used and updates the position of
	 * the camera
	 */
	public void move() {
		double pitchR = Math.toRadians(pitch);
		double yawR = Math.toRadians(yaw);

		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			position.x += (-STEP * Math.sin(yawR));
			position.y += (+STEP * Math.sin(pitchR));
			position.z += (-STEP * Math.sin(pitchR)) + (-STEP * Math.cos(yawR));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			position.x += (+STEP * Math.sin(yawR));
			position.y += (-STEP * Math.sin(pitchR));
			position.z += (+STEP * Math.sin(pitchR)) + (+STEP * Math.cos(yawR));
		}
	}

	/**
	 * Rotate the camera that the user sees
	 */
	public void rotate() {
		/* Looks down */
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			pitch -= ANGLE_OF_ROTATION;
		}

		/* Looks up */
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			pitch += ANGLE_OF_ROTATION;
		}

		/* Looks left */
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			yaw += ANGLE_OF_ROTATION;
		}

		/* Looks right */
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			yaw -= ANGLE_OF_ROTATION;
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
