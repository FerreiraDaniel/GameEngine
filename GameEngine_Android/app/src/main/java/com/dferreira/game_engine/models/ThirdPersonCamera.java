package com.dferreira.game_engine.models;

/**
 * Represents a third person camera used by the user to see the 3D World
 */
public class ThirdPersonCamera extends Camera {

    /**
     * Distance between camera and the player_mtl controlled by the user
     */
    private final float distanceFromPlayer;

    /**
     * Angle around the player_mtl
     */
    private final float angleAroundPlayer;

    /**
     * Initializer of the camera of the scene
     */
    public ThirdPersonCamera() {
        super();
        this.distanceFromPlayer = 25.0f;
        this.angleAroundPlayer = 0.0f;
    }

    /**
     * Uses the position of the player_mtl to update the position of the camera
     *
     * @param player Reference to that the camera is going to follow
     * @param terrain			The camera needs to be above the terrain otherwise gets flick
     */
    public void update(Player player, Terrain terrain) {
        float horizontalDistance = getHorizontalDistance();
        float verticalDistance = getVerticalDistance();
        this.calculateCameraPosition(horizontalDistance, verticalDistance, player, terrain);
        this.setYaw(180 - player.getRotY() + angleAroundPlayer);
    }

    /**
     * Compute the position where the camera should be to follow the player_mtl
     *
     * @param horizontalDistance Horizontal distance
     * @param verticalDistance   Vertical distance
     * @param player             Player of the scene
     * @param terrain            The camera needs to be above the terrain otherwise gets flick
     */
    private void calculateCameraPosition(float horizontalDistance, float verticalDistance, Player player, Terrain terrain) {
        float theta = player.getRotY() + angleAroundPlayer;
        double rTheta = Math.toRadians(theta);
        float offsetX = (float) (horizontalDistance * Math.sin(rTheta));
        float offsetZ = (float) (horizontalDistance * Math.cos(rTheta));
        getPosition().x = 1.0f * player.getPosition().x + offsetX;
        getPosition().z = 1.0f * player.getPosition().z - offsetZ;
        getPosition().y = (player.getPosition().y + 10.0f) + verticalDistance;
        float terrainHeight = terrain.getHeightOfTerrain(getPosition().x, getPosition().z);
        if (getPosition().y < terrainHeight) {
            getPosition().y = (player.getPosition().y + 10.0f + terrainHeight +
                    verticalDistance);
        }
    }


    /**
     * @return Horizontal distance from the camera to the player_mtl
     */
    private float getHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(getPitch())));
    }

    /**
     * @return Vertical distance from the camera to the player_mtl
     */
    private float getVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(getPitch())));
    }
}
