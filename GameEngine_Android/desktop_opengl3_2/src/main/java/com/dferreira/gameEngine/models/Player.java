package com.dferreira.gameEngine.models;

import com.dferreira.commons.Vector3f;
import com.dferreira.gameEngine.models.complexEntities.Entity;
import com.dferreira.gameEngine.models.complexEntities.GenericEntity;

import org.lwjgl.input.Keyboard;

/**
 * Player that is going to be used in the scene
 */
public class Player extends Entity {

    /* Distance that the user is going to run in one second */
    private static final float RUN_SPEED = 60.0f;
    /* Angle that the user will turn in one second */
    private static final float TURN_SPEED = 160.0f;
    /* The gravity that is going to push the player back to the ground */
    private static final float GRAVITIY = -0.5f;
    /* Power that the player is going to be push up when is jumping */
    private static final float JUMP_POWER = 0.3f;

    /* The current speed of the player */
    private float currentSpeed = 0.0f;

    /* The current speed turning */
    private float currentTurnSpeed = 0.0f;

    /* The speed which the player is going up */
    private float upwardsSpeed = 0.0f;

    /* Indicates if the player is jumping or not */
    private boolean isJumping;

    /**
     * Constructor of the player to be render in the 3D world
     *
     * @param genericEntity reference to a generic entity with the description of the
     *                      model
     * @param position      position where the model should be render
     * @param rotX          Rotation of the model in the X axle
     * @param rotY          Rotation of the model in the Y axle
     * @param rotZ          Rotation of the model in the Z axle
     * @param scale         Scale of the model
     */
    public Player(GenericEntity genericEntity, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(genericEntity, position, rotX, rotY, rotZ, scale);
        this.isJumping = false;
    }

    /**
     * Check the input of the user in order to update the current values of the
     * movement
     *
     * @param terrain Reference to the terrain
     */
    private void checkInputs(Terrain terrain) {
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            // Go in front
            this.currentSpeed = RUN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            // Go backwards
            this.currentSpeed = -RUN_SPEED;
        } else {
            // Stay where it is
            this.currentSpeed = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            // Rotate counterclockwise
            this.currentTurnSpeed = -TURN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            // Rotate clockwise
            this.currentTurnSpeed = TURN_SPEED;
        } else {
            // Do not rotate nothing
            this.currentTurnSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            this.jump(terrain);
        }
    }

    /**
     * Move the player and rotate it around the scene
     *
     * @param timeToRender The time that took to render the last frame in seconds like
     *                     that the movement of the player is frame rate independent
     */
    private void moveAndRotate(float timeToRender) {
        float turnByFrame = currentTurnSpeed * timeToRender;
        this.increaseRotation(0.0f, turnByFrame, 0.0f);
        float distance = currentSpeed * timeToRender;
        double angleRadians = Math.toRadians(super.getRotY());
        float dx = -(float) (distance * Math.sin(angleRadians));
        float dz = (float) (distance * Math.cos(angleRadians));
        super.increasePosition(dx, 0.0f, dz);
    }

    /**
     * When the player is above the terrain height make it to fall down
     *
     * @param timeToRender The time that took to render the last frame in seconds like
     *                     that the movement of the player is frame rate independent
     * @param terrain      Reference to the terrain to compute where is going to fall
     */
    private void fallDown(float timeToRender, Terrain terrain) {
        float terrainHeight = terrain.getHeightOfTerrain(getPosition().x, getPosition().z);
        if (((getPosition().y > terrainHeight) || (upwardsSpeed > 0)) && isJumping) {
            upwardsSpeed += GRAVITIY * timeToRender;
            super.increasePosition(0.0f, upwardsSpeed, 0.0f);
        } else {
            super.getPosition().y = terrainHeight;
        }

        // Set the flag of jump to false
        if (super.getPosition().y <= terrainHeight) {
            this.isJumping = false;
        }
    }

    /**
     * Set the upward speed of the player in order make it jump
     *
     * @param terrain Reference to the terrain in order to compute the height that
     *                is going to jump
     */
    private void jump(Terrain terrain) {
        float terrainHeight = terrain.getHeightOfTerrain(getPosition().x, getPosition().z);
        if (super.getPosition().y <= terrainHeight) {
            upwardsSpeed = terrainHeight + JUMP_POWER;
            isJumping = true;
        }
    }

    /**
     * Move the player due the the keys that are pressed in the keyBoard
     *
     * @param timeToRender The time that took to render the last frame in seconds like
     *                     that the movement of the player is frame rate independent
     * @param terrain      Terrain used to determine the height where the player is going
     *                     to fall/stay
     */
    public void move(float timeToRender, Terrain terrain) {
        checkInputs(terrain);
        moveAndRotate(timeToRender);
        fallDown(timeToRender, terrain);
    }

    /**
     * @return the current speed of the player
     */
    public float getCurrentSpeed() {
        return currentSpeed;
    }

}