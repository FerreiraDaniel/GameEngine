package gameEngine.audioEngine;

import java.util.HashMap;
import java.util.List;

import gameEngine.models.AudioBuffer;
import gameEngine.models.AudioSource;
import gameEngine.models.Player;
import gameEngine.models.complexEntities.Entity;

/**
 * Play all the different sound used by the audio engine
 */
public class MasterPlayer {

	/**
	 * Reference to the render of the entities
	 */
	private final EntityPlayer entityPlayer;
	
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
	public MasterPlayer(List<AudioSource> sourcesAvailable) {
		this.entityPlayer = new EntityPlayer(sourcesAvailable);
	}
	
	/**
	 * Put the entities to get sounds of it
	 * 
	 * @param lEntities
	 *            list of entities to get render in the next frame
	 */
	public void processEntities(Entity[] lEntities) {
		this.entities = lEntities;
	}
	
	/**
	 * Set the player that is going to use during the render
	 * 
	 * @param player The player that is going to set
	 */
	public void processPlayer(Player player) {
		this.player = player;
	}

	/**
	 * 
	 * @param audioLibrary	Set of sounds that the players can use to 
	 * give audio feedback to the user
	 */
	public void play(HashMap<TAudioEnum, AudioBuffer> audioLibrary) {
		this.entityPlayer.play(audioLibrary, entities, player);
	}

	/**
	 * Clean up because we need to clean up when we finish the program
	 */
	public void cleanUp() {
		this.entityPlayer.cleanUp();
	}
}
