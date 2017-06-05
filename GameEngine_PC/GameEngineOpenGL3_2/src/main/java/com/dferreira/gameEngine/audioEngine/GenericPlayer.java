package com.dferreira.gameEngine.audioEngine;

import org.lwjgl.openal.AL10;

import com.dferreira.commons.Vector3f;
import com.dferreira.gameEngine.models.AudioBuffer;
import com.dferreira.gameEngine.models.AudioSource;

/**
 * Contains a set of methods that are useful for all the players of the
 * application
 */
public class GenericPlayer {

	/**
	 * The the properties of the listener of the scene
	 * 
	 * @param listenerPos
	 *            Position of the listener in the 3D world
	 */
	protected void positioningListener(Vector3f listenerPos) {
		AL10.alListener3f(AL10.AL_POSITION, listenerPos.x, listenerPos.y, listenerPos.z);
		// Second sets the velocity of the listener (for the moment idle
		Vector3f listenerVel = new Vector3f(0.0f, 0.0f, 0.0f);
		AL10.alListener3f(AL10.AL_VELOCITY, listenerVel.x, listenerVel.y, listenerVel.z);
	}
	
	/**
	 * Plays one buffer in a certain source
	 * 
	 * @param source
	 *            Source of audio
	 * @param buffer
	 *            Buffer to play
	 */
	protected void play(AudioSource source, AudioBuffer buffer) {
		int sourceId = source.getSourceId();
		int bufferId = buffer.getBufferId();

		this.stop(source);
		source.setBuffer(buffer);
		// Associate the buffer with the source (Put the CD into the player)
		AL10.alSourcei(sourceId, AL10.AL_BUFFER, bufferId);
		AL10.alSourcePlay(sourceId);
	}

	/**
	 * Pauses the reproduction of a sound if it is playing something
	 * 
	 * @param source
	 *            Source of audio
	 */
	protected void pause(AudioSource source) {
		int sourceId = source.getSourceId();
		AL10.alSourcePause(sourceId);
	}

	/**
	 * Continues the reproduction of a sound if it is in pause
	 * 
	 * @param source
	 *            The source of audio
	 */
	protected void continuePlaying(AudioSource source) {
		int sourceId = source.getSourceId();
		AL10.alSourcePlay(sourceId);
	}

	/**
	 * Stop the reproduction of a sound if is playing
	 * 
	 * @param source
	 *            The source of audio
	 */
	protected void stop(AudioSource source) {
		source.setBuffer(null);
		AL10.alSourceStop(source.getSourceId());
	}

	/**
	 * Indicates if the source should play over and over again the associated
	 * buffer
	 * 
	 * 
	 * @param source
	 *            The source of audio
	 * @param loop
	 *            The flag that indicate if should use looping or not
	 */
	protected void setLoop(AudioSource source, boolean loop) {
		int sourceId = source.getSourceId();
		AL10.alSourcei(sourceId, AL10.AL_LOOPING, (loop) ? AL10.AL_TRUE : AL10.AL_FALSE);
	}

	/**
	 * @param source
	 *            The source of audio
	 * @return If the source is playing or not
	 */
	protected boolean isPlaying(AudioSource source) {
		int sourceId = source.getSourceId();
		return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}

	/**
	 * @param source
	 *            The source of audio
	 * @return If the source is paused or not
	 */
	protected boolean isPaused(AudioSource source) {
		int sourceId = source.getSourceId();
		return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PAUSED;
	}

	/**
	 * Set the velocity of the source
	 * 
	 * @param source
	 *            The source of audio
	 * @param velocity
	 *            The velocity to set
	 */
	protected void setVelocity(AudioSource source, Vector3f velocity) {
		int sourceId = source.getSourceId();
		/* Set the velocity of the source */
		AL10.alSource3f(sourceId, AL10.AL_VELOCITY, velocity.x, velocity.y, velocity.z);
	}

	/**
	 * Set the volume of the source
	 * 
	 * @param source
	 *            The source of audio
	 * @param volume
	 *            volume of the source between 0.0f and 1.0f
	 */
	protected void setVolume(AudioSource source, float volume) {
		int sourceId = source.getSourceId();
		/* Set the gain of the source */
		AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
	}

	/**
	 * Set the pitch of the source (Speed of play)
	 * 
	 * 
	 * @param source
	 *            The source of audio
	 * @param pitch
	 *            The pitch to set in the source
	 */
	protected void setPitch(AudioSource source, float pitch) {
		int sourceId = source.getSourceId();
		/* Set the pitch of the source */
		AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
	}

	/**
	 * Set the position of the source
	 * 
	 * @param source
	 *            The source of audio
	 * @param position
	 *            The position to set
	 */
	protected void setPosition(AudioSource source, Vector3f position) {
		int sourceId = source.getSourceId();
		/* Set the position of the source */
		AL10.alSource3f(sourceId, AL10.AL_POSITION, position.x, position.y, position.z);
	}
}
