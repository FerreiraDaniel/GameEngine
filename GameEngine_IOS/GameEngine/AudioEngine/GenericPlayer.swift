import Foundation

import OpenAL

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
    internal func play(audioSource : AudioSource, audioBuffer : AudioBuffer) {
    
        let sourceId : ALuint = audioSource.getSourceId();
        let bufferId : ALint = audioBuffer.getBufferId();
    
        self.stop(audioSource);
    audioSource.setBuffer(audioBuffer);
        // Associate the buffer with the source (Put the CD into the player)
        alSourcei(sourceId, AL_BUFFER, bufferId);
        alSourcePlay(sourceId);
    }
    
    /**
     * Pauses the reproduction of a sound in a source if it is playing something
     *
     * @param sourceId
     *            Identifier of the source of audio
     */
    internal func pause(sourceId : ALuint) {
        alSourcePause(sourceId);
    }
    
    /**
     * Continues the reproduction of a sound if it is in pause
     *
     * @param sourceId
     *            Identifier of the source of audio
     */
    internal func continuePlaying(sourceId: ALuint) {
        alSourcePlay(sourceId);
    }
    
    /**
     * Stop the reproduction of a sound if is playing
     *
     * @param audioSource
     *            The source of audio
     */
    internal func stop(audioSource : AudioSource) {
        audioSource.setBuffer(nil);
        alSourceStop(audioSource.getSourceId());
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
    internal func setLoop(sourceId : ALuint, loop : Bool) {
        alSourcei(sourceId, AL_LOOPING, (loop) ? AL_TRUE : AL_FALSE);
    }
    
    /**
     *  Returns the state of the source
     */
    private func getSourceiState(sourceId : ALuint) -> ALint {
        var state : ALint = 0;
        alGetSourcei(sourceId, AL_SOURCE_STATE, &state);
        return state;
    }
    
    /**
     * @param sourceId
     *            Identifier of the source of audio
     * @return If the source is playing or not
     */
    internal func isPlaying(sourceId : ALuint) -> Bool {
        return getSourceiState(sourceId) == AL_PLAYING;
    }
    
    /**
     * @param sourceId
     *            Identifier of the source of audio
     * @return If the source is paused or not
     */
    internal func isPaused(sourceId : ALuint) -> Bool {
        return getSourceiState(sourceId) == AL_PAUSED;
    }
    
    /**
     * Set the velocity of the source
     *
     * @param sourceId
     *            Identifier of the source of audio
     * @param velocity
     *            The velocity to set
     */
    internal func setVelocity(sourceId : ALuint, velocity : Vector3f) {
    /* Set the velocity of the source */
    // Vector3f velocity = new Vector3f(0.0f, 0.0f, 0.0f);
        alSource3f(sourceId, AL_VELOCITY, velocity.x, velocity.y, velocity.z);
    }
    
    /**
     * Set the volume of the source
     *
     * @param sourceId
     *            Identifier of the source of audio
     * @param volume
     *            volume of the source between 0.0f and 1.0f
     */
    internal func  setVolume(sourceId: ALuint, volume : ALfloat) {
    /* Set the gain of the source */
        alSourcef(sourceId, AL_GAIN, volume);
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
    internal func  setPitch(sourceId : ALuint, pitch : ALfloat) {
    /* Set the pitch of the source */
        alSourcef(sourceId, AL_PITCH, pitch);
    }
    
    /**
     * Set the position of the source
     *
     * @param sourceId
     *            Identifier of the source of audio
     * @param position
     *            The position to set
     */
    internal func  setPosition(sourceId : ALuint, position : Vector3f) {
    /* Set the position of the source */
    // Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);
        alSource3f(sourceId, AL_POSITION, position.x, position.y, position.z);
    }
}
