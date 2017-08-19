package com.dferreira.gameEngine.audioEngine;

import java.util.HashMap;
import java.util.List;

import com.dferreira.commons.generic_player.IAudioDescription;
import com.dferreira.commons.generic_player.IAudioSource;
import com.dferreira.commons.generic_player.IListener;
import com.dferreira.commons.generic_resources.AudioEnum;
import com.dferreira.gameEngine.models.Player;
import com.dferreira.gameEngine.models.complexEntities.Entity;

/**
 * Play all the different sound used by the audio engine
 */
public class MasterPlayer {

	/**
	 * Reference to the render of the entities
	 */
	private final EntityPlayer entityPlayer;

	/**
	 * The listener of the audio engine
	 */
	private IListener listener;
	
	/**
	 * Entities of the world that are going to be rendered
	 */
	private Entity[] entities;

	
	/**
	 * The player that is going to be show in the scene
	 */
	private Player player;

	
	/**
	 * Constructor of the master player
	 * 
	 * @param sourcesAvailable	Pool of sources to be used by the players
	 */
	public MasterPlayer(List<IAudioSource> sourcesAvailable) {
		this.entityPlayer = new EntityPlayer(sourcesAvailable);
	}
	
	/**
	 * Set the listener of the audio engine
	 * 
	 * @param listener the listener to set
	 */
	public void setListener(IListener listener) {
		this.listener = listener;
	}
	
	/**
	 * Set the entities to get sounds of it
	 * 
	 * @param lEntities
	 *            list of entities to get render in the next frame
	 */
	public void setEntities(Entity[] lEntities) {
		this.entities = lEntities;
	}
	
	/**
	 * Set the player that is going to use during the render
	 * 
	 * @param player The player that is going to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * 
	 * @param library	Set of sounds that the players can use to 
	 * give audio feedback to the user
	 */
	public void play(HashMap<AudioEnum, IAudioDescription> library) {
		this.entityPlayer.play(library, this.listener, this.entities, this.player);
	}

	/**
	 * Clean up because we need to clean up when we finish the program
	 */
	public void dispose() {
		this.entityPlayer.dispose();
	}
}
