package com.dferreira.gameEngine.audioEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.generic_player.IAudioDescription;
import com.dferreira.commons.generic_player.IAudioSource;
import com.dferreira.commons.generic_player.IListener;
import com.dferreira.commons.generic_resources.AudioEnum;
import com.dferreira.commons.generic_resources.ModelEnum;
import com.dferreira.commons.utils.Utils;
import com.dferreira.gameEngine.models.Player;
import com.dferreira.gameEngine.models.complexEntities.Entity;

/**
 * Is going to demand the reproduction of audio due to the control of the
 * entities
 */
public class EntityPlayer {

	/**
	 * If the entity is far away from the player more that the threshold is not
	 * going to be listened
	 */
	private float SOUND_THRESHOLD = 50f;

	/**
	 * First if going to make a rough approximation only after is going to check
	 * if exists a crash
	 */
	private float ROUGH_THRESHOLD = 10;
	private float CRASH_THRESHOLD = 2;

	/**
	 * List of sound sources available to the entity player
	 * 
	 */
	private List<IAudioSource> sourcesAvailable;

	/**
	 * The player can always play audio;
	 */
	private IAudioSource playerSource;

	/**
	 * Sources assign
	 */
	private Map<Entity, IAudioSource> sourcesAssign;

	/**
	 * Constructor of the entity player
	 */
	public EntityPlayer(List<IAudioSource> sourcesAvailable) {
		this.sourcesAvailable = sourcesAvailable;
		if (!Utils.isEmpty(this.sourcesAvailable)) {
			this.playerSource = this.sourcesAvailable.remove(this.sourcesAvailable.size() - 1);
		}
		this.sourcesAssign = new HashMap<Entity, IAudioSource>();
	}

	/**
	 * Set the listener position and reproduces the sound of the player
	 * 
	 * @param library
	 *            Set of sounds that can use to provide sound feedback to the
	 *            user
	 * @param listener
	 *            Listener to put in a certain position
	 * @param player
	 *            The player of the scene and also the listener
	 */
	private void playPlayer(HashMap<AudioEnum, IAudioDescription> library, IListener listener, Player player) {
		listener.positioning(player.getPosition());

		// If the player is running plays the sound of the footsteps
		if (player.getCurrentSpeed() == 0) {
			if (playerSource.isPlaying()) {
				playerSource.pause();
			}
		} else {
			float pitch = player.getCurrentSpeed() / 30.0f;
			if (playerSource.isPaused()) {
				playerSource.setPitch(pitch);
				playerSource.continuePlaying();
			} else {
				if (!playerSource.isPlaying()) {
					IAudioDescription footStepsBuffer = library.get(AudioEnum.footsteps);
					playerSource.setPitch(pitch);
					playerSource.setVolume(1.0f);
					playerSource.play(footStepsBuffer);
					playerSource.setLoop(true);
				}
			}
			playerSource.setPosition(player.getPosition());
		}
	}

	/**
	 * 
	 * @param position1
	 *            The position one to take in account
	 * @param position2
	 *            The position two entity to take in account
	 * @param threshold
	 *            The threshold that is going to use to the match
	 * 
	 * @return False = Not passed in the rough approximation True = Passed in
	 *         the rough approximation
	 */
	private boolean roughApproximation(Vector3f position1, Vector3f position2, float threshold) {
		return (Math.abs(position1.x - position2.x) < threshold) && (Math.abs(position1.y - position2.y) < threshold)
				&& (Math.abs(position1.z - position2.z) < threshold);
	}

	/**
	 * 
	 * @param position1
	 *            The position one to take in account
	 * @param position2
	 *            The position two entity to take in account
	 * 
	 * 
	 * @return The square of the distance between two positions
	 */
	private float squareDistance(Vector3f position1, Vector3f position2) {
		float xDiff = Math.abs(position1.x - position2.x);
		float yDiff = Math.abs(position1.y - position2.y);
		float zDiff = Math.abs(position1.z - position2.z);

		return (xDiff * xDiff) + (yDiff * yDiff) + (zDiff * zDiff);
	}

	/**
	 * 
	 * @param pPosition
	 *            The position of player to take in account
	 * @param ePosition
	 *            The position of entity to take in account
	 * 
	 * @return False the player can not listen the entity True the entity can be
	 *         listen by the player
	 */
	private boolean passesThreshold(Vector3f pPosition, Vector3f ePosition) {
		return roughApproximation(pPosition, ePosition, SOUND_THRESHOLD);
	}

	/**
	 * Assign audio sources to the entities	taking in account the position of the player
	 * 
	 * @param player
	 *            The player of the scene and also the listener
	 * @param entities
	 *            Set of entities that should provide sound feedback
	 */
	private void assignSources(Player player, Entity[] entities) {
		if ((player != null) && (!Utils.isEmpty(entities))) {
			Vector3f pPosition = player.getPosition();
			for (Entity entity : entities) {
				if (passesThreshold(pPosition, entity.getPosition())) {
					// Can not assign nothing
					if (sourcesAvailable.isEmpty()) {
						continue;
					}
					// Check is a source was already assign
					if (!sourcesAssign.containsKey(entity)) {
						IAudioSource aSource = sourcesAvailable.remove(sourcesAvailable.size() - 1);
						sourcesAssign.put(entity, aSource);
					}
				} else {
					// Not passed threshold
					if (sourcesAssign.containsKey(entity)) {
						IAudioSource aSource = sourcesAssign.remove(entity);
						aSource.stop();
						this.sourcesAvailable.add(aSource);
					}
				}
			}
		}
	}

	/**
	 * Check is an entity crashed against the player
	 * 
	 * @param library
	 *            Set of sounds that can use to provide sound feedback to the
	 *            user
	 * @param entity
	 *            The entity to check
	 * @param player
	 *            The position of the player that is going to check the crash
	 * 
	 * @return False = No crash was detected True = A crash was detected and the
	 *         sound was played
	 */
	private boolean playCrash(HashMap<AudioEnum, IAudioDescription> library, Entity entity, Vector3f player) {
		if (roughApproximation(player, entity.getPosition(), ROUGH_THRESHOLD)
				&& (squareDistance(player, entity.getPosition()) < CRASH_THRESHOLD)) {
			IAudioSource source = sourcesAssign.get(entity);
			ModelEnum tEntity = entity.getGenericEntity().getObjectType();
			switch (tEntity) {
			case banana_tree:
				break;
			case fern:
				break;
			case flower:
				break;
			case grass:
				break;
			case marble:
				if (!source.isPlaying() && (source.getAudioDescription() == null)) {
					IAudioDescription breakingWood = library.get(AudioEnum.breakingWood);
					source.setPitch(1.0f);
					source.setVolume(1.0f);
					source.setPosition(entity.getPosition());
					source.play(breakingWood);
					source.setLoop(false);
				}
				break;
			case player:
				break;
			case tree:
				IAudioDescription breakingWood = library.get(AudioEnum.breakingWood);
				if (!source.isPlaying()
						|| ((source.getAudioDescription() == null) || (source.getAudioDescription() != breakingWood))) {
					source.setPitch(1.0f);
					source.setVolume(1.0f);
					source.setPosition(entity.getPosition());
					source.play(breakingWood);
					source.setLoop(false);
				}
				break;
			default:
				break;
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Reproduces the sound of the entities
	 * 
	 * @param library
	 *            Set of sounds that can use to provide sound feedback to the
	 *            user
	 * @param entities
	 *            Set of entities that should provide sound feedback
	 * @param player
	 *            The player of the scene and also the listener
	 */
	private void playEntities(HashMap<AudioEnum, IAudioDescription> library, Set<Entity> entities, Player player) {
		if (!Utils.isEmpty(entities)) {
			Vector3f pPosition = player.getPosition();

			for (Entity entity : entities) {
				// Check is has one source
				IAudioSource source = sourcesAssign.get(entity);
				if (source == null) {
					continue;
				}

				if (!playCrash(library, entity, pPosition)) {
					// If not played a crash
					if (entity.getGenericEntity().getObjectType() != ModelEnum.tree) {
						continue;
					}

					// Make birds to play in each tree
					IAudioDescription breakingWood = library.get(AudioEnum.breakingWood);
					if (source.getAudioDescription() == breakingWood) {
						continue;
					}

					if (!source.isPlaying()) {
						IAudioDescription falconBuffer = library.get(AudioEnum.falcon);
						source.setPitch(1.0f);
						source.setVolume(1.0f);
						source.setPosition(entity.getPosition());
						source.play(falconBuffer);
						source.setLoop(true);
					}
				}

			}
		}
	}

	/**
	 * Plays all the sounds related with entities
	 * 
	 * @param library
	 *            Set of sounds that can use to provide sound feedback to the
	 *            user
	 * @param listener
	 *            Listener to put in a certain position
	 * @param entities
	 *            Set of entities that should provide sound feedback
	 * @param player
	 *            The player of the scene and also the listener
	 */
	public void play(HashMap<AudioEnum, IAudioDescription> library, IListener listener, Entity[] entities,
			Player player) {
		if (player == null) {
			return;
		}
		this.playPlayer(library, listener, player);
		this.assignSources(player, entities);
		this.playEntities(library, this.sourcesAssign.keySet(), player);

	}

	/**
	 * Clean up because we need to clean up when we finish the program
	 */
	public void dispose() {

	}
}
