package com.dferreira.gameEngine.al_player;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.generic_player.IListener;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;

/**
 * Has methods used to start and update the player
 */
public class ALListener implements IListener {

    /**
     * Before using any openAL methods we need to initialize openAL
     *
     * @return False -> One or more errors occurred True -> Everything was all
     * right
     */
    @Override
    public boolean init() {
        try {
            // Setup all openAL stuff that we are going to be using
            AL.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        return (AL10.alGetError() == AL10.AL_NO_ERROR);
    }

    /**
     * Puts the player in a certain position
     *
     * @param position the new position of the player
     */
    @Override
    public void positioning(Vector3f position) {
        AL10.alListener3f(AL10.AL_POSITION, position.x, position.y, position.z);
        // Second sets the velocity of the listener (for the moment idle
        Vector3f listenerVel = new Vector3f(0.0f, 0.0f, 0.0f);
        AL10.alListener3f(AL10.AL_VELOCITY, listenerVel.x, listenerVel.y, listenerVel.z);
    }

    /**
     * Releases the resources used by openAL
     */
    @Override
    public void dispose() {
        AL.destroy();
    }
}
