package gameEngine.models;

import org.lwjgl.input.Mouse;


/**
 * Represents a third person camera used by the user to see the 3D World
 */
public class ThirdPersonCamera extends Camera {

	/**
	 * Distance between camera and the player controlled by the user
	 */
	private float distanceFromPlayer;
	
	/**
	 * Angle around the player
	 */
	private float angleAroundPlayer;
	
	/**
	 * Initializer of the camera of the scene
	 */
	public ThirdPersonCamera() {
		super();
		this.distanceFromPlayer = 25.0f;
		this.angleAroundPlayer = 0.0f;
	}
	
	/**
	 * Uses the position of the player to update the position of the camera
	 *
	 * @param player Reference to that the camera is going to follow
	 */
	public void update(Player player) {
		this.checkMouseInputs();
		float horizontalDistance = getHorizontalDistance();
		float verticalDistance = getVerticalDistance();
		this.calculateCameraPosition(horizontalDistance, verticalDistance, player);
		this.setYaw(180 - player.getRotY() + angleAroundPlayer);
	}
	
	
    /**
     * Compute the position where the camera should be to follow the player
     *
     * @param horizontalDistance		Horizontal distance
     * @param verticalDistance	Vertical distance
     * @param player			Player of the scene
     */
    private void calculateCameraPosition(float horizontalDistance, float verticalDistance, Player player) {
        float theta = player.getRotY() + angleAroundPlayer;
        double rTheta = Math.toRadians(theta);
        float offsetX = (float) (horizontalDistance * Math.sin(rTheta));
        float offsetZ = (float) (horizontalDistance * Math.cos(rTheta));
        getPosition().x = 1.0f * player.getPosition().x + offsetX;
        getPosition().z = 1.0f * player.getPosition().z - offsetZ;
        getPosition().y = (player.getPosition().y + 10.0f)  + verticalDistance;
    }
	
	/**
	 * Checks the input introduced by the user and update the distance to the player of the 
	 * scene
	 */
	private void checkZoomInput() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		this.distanceFromPlayer -= zoomLevel;
	}
	
	/**
	 * Checks the input introduced by the user and update the pitch (rotation around X-axis)
	 */
	private void checkPitchInput() {
		if(Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			setPitch(getPitch() - pitchChange);
		}
	}
	
	/**
	 * Checks the input introduced by the user and update the angle around the player
	 */
	private void checkAngleAroundInput() {
		if(Mouse.isButtonDown(0)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
	
	/**
	 * Check the inputs that the user controls with his mouse
	 */
	private void checkMouseInputs() {
		this.checkZoomInput();
		this.checkPitchInput();
		this.checkAngleAroundInput();
	}
	
	/**
	 * 
	 * @return Horizontal distance from the camera to the player
	 */
	private float getHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(getPitch())));
	}
	
	/**
	 * 
	 * @return Vertical distance from the camera to the player
	 */
	private float getVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(getPitch())));
	}
}
