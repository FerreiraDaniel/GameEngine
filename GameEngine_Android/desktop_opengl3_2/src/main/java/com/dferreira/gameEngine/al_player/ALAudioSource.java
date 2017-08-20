package com.dferreira.gameEngine.al_player;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.generic_player.IAudioDescription;
import com.dferreira.commons.generic_player.IAudioSource;

import org.lwjgl.openal.AL10;

/**
 * Source represents the object that can play sounds for instance the CD player
 */
public class ALAudioSource implements IAudioSource {

    /**
     * Identifier of the source in openAL
     */
    private final int id;

    /**
     * Reference to the buffer currently in the source if any
     */
    private ALAudioDescription buffer;

    /**
     * @param sourceId Identifier of the source in openAL
     */
    public ALAudioSource(int sourceId) {
        super();
        this.id = sourceId;
    }

    /**
     * @return the sourceId Identifier of the source
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @return The descriptor of the audio that the source is currently associated (If any)
     */
    @Override
    public IAudioDescription getAudioDescription() {
        return buffer;
    }

    /**
     * Starts playing one audio descriptor
     *
     * @param audioDescriptor Descriptor of the audio to play
     */
    @Override
    public void play(IAudioDescription audioDescriptor) {
        this.stop();
        this.buffer = (ALAudioDescription) audioDescriptor;
        int bufferId = buffer.getBufferId();


        // Associate the buffer with the source (Put the CD into the player)
        AL10.alSourcei(this.id, AL10.AL_BUFFER, bufferId);
        AL10.alSourcePlay(this.id);
    }

    /**
     * Pauses the reproduction of a sound if it is playing something
     *
     * @param source Source of audio
     */
    @Override
    public void pause() {
        AL10.alSourcePause(this.id);
    }

    /**
     * Continues the reproduction of a sound if it is in pause
     *
     * @param source The source of audio
     */
    @Override
    public void continuePlaying() {
        AL10.alSourcePlay(this.id);
    }

    /**
     * Stop the reproduction of a sound if is playing
     *
     * @param source The source of audio
     */
    @Override
    public void stop() {
        this.buffer = null;
        AL10.alSourceStop(this.id);
    }

    /**
     * Indicates if the source should play over and over again the associated
     * buffer
     *
     * @param source The source of audio
     * @param loop   The flag that indicate if should use looping or not
     */
    @Override
    public void setLoop(boolean loop) {
        AL10.alSourcei(this.id, AL10.AL_LOOPING, (loop) ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    /**
     * @param source The source of audio
     * @return If the source is playing or not
     */
    @Override
    public boolean isPlaying() {
        return AL10.alGetSourcei(this.id, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }

    /**
     * @param source The source of audio
     * @return If the source is paused or not
     */
    @Override
    public boolean isPaused() {
        return AL10.alGetSourcei(this.id, AL10.AL_SOURCE_STATE) == AL10.AL_PAUSED;
    }

    /**
     * Set the velocity of the source
     *
     * @param source   The source of audio
     * @param velocity The velocity to set
     */
    @Override
    public void setVelocity(Vector3f velocity) {
        /* Set the velocity of the source */
        AL10.alSource3f(this.id, AL10.AL_VELOCITY, velocity.x, velocity.y, velocity.z);
    }

    /**
     * Set the volume of the source
     *
     * @param volume volume of the source between 0.0f and 1.0f
     */
    @Override
    public void setVolume(float volume) {
		/* Set the gain of the source */
        AL10.alSourcef(this.id, AL10.AL_GAIN, volume);
    }

    /**
     * Set the pitch of the source (Speed of play)
     *
     * @param pitch The pitch to set in the source
     */
    @Override
    public void setPitch(float pitch) {
		/* Set the pitch of the source */
        AL10.alSourcef(this.id, AL10.AL_PITCH, pitch);
    }

    /**
     * Set the position of the source
     *
     * @param position The position to set
     */
    @Override
    public void setPosition(Vector3f position) {
		/* Set the position of the source */
        AL10.alSource3f(this.id, AL10.AL_POSITION, position.x, position.y, position.z);
    }

}
