package com.dferreira.commons.generic_player;

import com.dferreira.commons.generic_resources.AudioEnum;
import com.dferreira.commons.generic_resources.IResourceProvider;

import java.util.List;

/**
 * Interface that should be implemented by the loader of audio elements
 */
public interface IAudioLoader {


    /**
     * Load one audio file in an audio descriptor
     *
     * @param audioEnum        Enumeration of the audio that is going to play
     * @param resourceProvider The provider of resources
     * @return The audio descriptor of the audio loaded
     */
    IAudioDescription loadAudio(AudioEnum audioEnum, IResourceProvider resourceProvider);

    /**
     * @param numberOfSources Number of audio sources to generate
     * @return A list of the audio sources generated
     */
    List<IAudioSource> genAudioSources(int numberOfSources);

    /**
     * Disposes all the information used by the audio loader
     */
    void dispose();
}
