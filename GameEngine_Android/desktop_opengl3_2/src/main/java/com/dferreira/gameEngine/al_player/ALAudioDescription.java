package com.dferreira.gameEngine.al_player;

import com.dferreira.commons.generic_player.IAudioDescription;

/**
 * Audio buffer to get played
 */
public class ALAudioDescription implements IAudioDescription {

    /**
     * Identifier of the buffer to play in openAL
     */
    private final int bufferId;

    /**
     * @param bufferId Identifier of the buffer to play in openAL
     */
    public ALAudioDescription(int bufferId) {
        super();
        this.bufferId = bufferId;
    }

    /**
     * @return the bufferId Identifier of the buffer to play in openAL
     */
    public int getBufferId() {
        return bufferId;
    }
}
