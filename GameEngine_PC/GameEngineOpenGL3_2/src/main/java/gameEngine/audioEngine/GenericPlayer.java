package gameEngine.audioEngine;

import org.lwjgl.openal.AL10;

import com.dferreira.commons.Vector3f;

import gameEngine.models.AudioBuffer;
import gameEngine.models.AudioSource;

/**
 * Contains a set of methods that are useful for all the players of the
 * application
 */
public class GenericPlayer {

	/**
	 * Plays one buffer in a certain source
	 * 
	 * @param audioSource
	 *            Source of audio
	 * @param audioBuffer
	 *            Buffer to play
	 */
	protected void play(AudioSource audioSource, AudioBuffer audioBuffer) {
		int sourceId = audioSource.getSourceId();
		int bufferId = audioBuffer.getBufferId();

		this.stop(audioSource);
		audioSource.setBuffer(audioBuffer);
		// Associate the buffer with the source (Put the CD into the player)
		AL10.alSourcei(sourceId, AL10.AL_BUFFER, bufferId);
		AL10.alSourcePlay(sourceId);
	}

	/**
	 * Pauses the reproduction of a sound if it is playing something
	 * 
	 * @param sourceId
	 *            Identifier of the source of audio
	 */
	protected void pause(int sourceId) {
		AL10.alSourcePause(sourceId);
	}

	/**
	 * Continues the reproduction of a sound if it is in pause
	 * 
	 * @param sourceId
	 *            Identifier of the source of audio
	 */
	protected void continuePlaying(int sourceId) {
		AL10.alSourcePlay(sourceId);
	}

	/**
	 * Stop the reproduction of a sound if is playing
	 * 
	 * @param audioSource
	 *            The source of audio
	 */
	protected void stop(AudioSource audioSource) {
		audioSource.setBuffer(null);
		AL10.alSourceStop(audioSource.getSourceId());
	}

	/**
	 * Indicates if the source should play over and over again the associated
	 * buffer
	 * 
	 * 
	 * @param sourceId
	 *            Identifier of the source of audio
	 * @param loop
	 *            The flag that indicate if should use looping or not
	 */
	protected void setLoop(int sourceId, boolean loop) {
		AL10.alSourcei(sourceId, AL10.AL_LOOPING, (loop) ? AL10.AL_TRUE : AL10.AL_FALSE);
	}

	/**
	 * @param sourceId
	 *            Identifier of the source of audio
	 * @return If the source is playing or not
	 */
	protected boolean isPlaying(int sourceId) {
		return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}

	/**
	 * @param sourceId
	 *            Identifier of the source of audio
	 * @return If the source is paused or not
	 */
	protected boolean isPaused(int sourceId) {
		return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PAUSED;
	}

	/**
	 * Set the velocity of the source
	 * 
	 * @param sourceId
	 *            Identifier of the source of audio
	 * @param velocity
	 *            The velocity to set
	 */
	protected void setVelocity(int sourceId, Vector3f velocity) {
		/* Set the velocity of the source */
		// Vector3f velocity = new Vector3f(0.0f, 0.0f, 0.0f);
		AL10.alSource3f(sourceId, AL10.AL_VELOCITY, velocity.x, velocity.y, velocity.z);
	}

	/**
	 * Set the volume of the source
	 * 
	 * @param sourceId
	 *            Identifier of the source of audio
	 * @param volume
	 *            volume of the source between 0.0f and 1.0f
	 */
	protected void setVolume(int sourceId, float volume) {
		/* Set the gain of the source */
		AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
	}

	/**
	 * Set the pitch of the source (Speed of play)
	 * 
	 * 
	 * @param sourceId
	 *            Identifier of the source of audio
	 * @param pitch
	 *            The pitch to set in the source
	 */
	protected void setPitch(int sourceId, float pitch) {
		/* Set the pitch of the source */
		AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
	}

	/**
	 * Set the position of the source
	 * 
	 * @param sourceId
	 *            Identifier of the source of audio
	 * @param position
	 *            The position to set
	 */
	protected void setPosition(int sourceId, Vector3f position) {
		/* Set the position of the source */
		// Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);
		AL10.alSource3f(sourceId, AL10.AL_POSITION, position.x, position.y, position.z);
	}
}
