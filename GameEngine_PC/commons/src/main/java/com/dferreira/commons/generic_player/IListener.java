package com.dferreira.commons.generic_player;

import com.dferreira.commons.Vector3f;

/**
 * Describes some methods used to update the player
 */
public interface IListener {
	
	/**
	 * Before using any methods of the listener needs to initialize it
	 * 
	 * @return False -> One or more errors occurred True -> Everything was all
	 *         right
	 */
	boolean init();

	/**
	 * Puts the player in a certain position
	 * 
	 * @param position the new position of the player
	 */
	void positioning(Vector3f position);
	
    /**
     * Disposes all the information used by the audio listener
     */
    void dispose();
}