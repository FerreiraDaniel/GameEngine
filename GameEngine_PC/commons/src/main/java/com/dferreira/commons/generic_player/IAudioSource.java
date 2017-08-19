package com.dferreira.commons.generic_player;

import com.dferreira.commons.Vector3f;

/**
 * Responsible for managing the associated mediaDescription
 */
public interface IAudioSource {
    /**
     * @return the Identifier of the source
     */
    int getId();

    /**
     * Starts playing one audio descriptor
     *
     * @param audioDescriptor Descriptor of the audio to play
     */
    void play(IAudioDescription audioDescriptor);

    /**
     * @return The descriptor of the audio that the source is currently associated (If any)
     */
    IAudioDescription getAudioDescription();

    /**
     * Pauses the reproduction of a sound if it is playing something
     */
    void pause();

    /**
     * Continues the reproduction of a sound if it is in pause
     */
    void continuePlaying();


    /**
     * Stop the reproduction of a sound if is playing
     */
    void stop();

    /**
     * Indicates if the source should play over and over again the associated
     * audio
     *
     * @param loop The flag that indicate if should use looping or not
     */
    void setLoop(boolean loop);

    /**
     * @return If the source is playing or not
     */
    boolean isPlaying();

    /**
     * @return If the source is paused or not
     */
    boolean isPaused();

    /**
     * Set the velocity of the source
     *
     * @param velocity The velocity to set
     */
    void setVelocity(Vector3f velocity);

    /**
     * Set the volume of the source
     *
     * @param volume volume of the source between 0.0f and 1.0f
     */
    void setVolume(float volume);

    /**
     * Set the pitch of the source (Speed of play)
     *
     * @param pitch The pitch to set in the source
     */
    void setPitch(float pitch);

    /**
     * Set the position of the source
     *
     * @param position The position to set
     */
    void setPosition(Vector3f position);
}
