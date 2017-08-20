package com.dferreira.gameEngine.al_player;

import com.dferreira.commons.generic_player.IAudioDescription;
import com.dferreira.commons.generic_player.IAudioLoader;
import com.dferreira.commons.generic_player.IAudioSource;
import com.dferreira.commons.generic_player.IListener;
import com.dferreira.commons.generic_resources.AudioEnum;
import com.dferreira.commons.generic_resources.IAudioData;
import com.dferreira.commons.generic_resources.IResourceProvider;
import com.dferreira.desktopUtils.OggAudioData;

import org.lwjgl.openal.AL10;

import java.util.ArrayList;
import java.util.List;

/**
 * Loader of audio elements
 */
public class ALAudioLoader implements IAudioLoader {

    /**
     * List of audio buffers in use by the game
     */
    private List<Integer> audioBuffers;

    /**
     * List of audio sources in use by the game
     */
    private List<Integer> audioSources;

    /**
     * the listener of the audio engine
     */
    private IListener listener;

    /**
     * Constructor of the loader class
     */
    public ALAudioLoader() {
        this.audioBuffers = new ArrayList<Integer>();
        this.audioSources = new ArrayList<Integer>();
        this.listener = new ALListener();
    }

    /**
     * @return The listener to the audio engine
     */
    public IListener getListener() {
        return this.listener;
    }

    /**
     * Load one audio file in a buffer
     *
     * @param fileName         The file name of the file to load
     * @param resourceProvider The provider of resources
     * @return The identifier of the buffer return by openAL
     */
    @Override
    public IAudioDescription loadAudio(AudioEnum audioEnum, IResourceProvider resourceProvider) {
        IAudioData iOggFile = resourceProvider.getResource(audioEnum);
        if (iOggFile == null) {
            return null;
        } else {
            OggAudioData oggFile = (OggAudioData) iOggFile;

            try {
                int bufferId = AL10.alGenBuffers();
                /**
                 * Was not possible to one buffer return AL false
                 */
                if (AL10.alGetError() != AL10.AL_NO_ERROR) {
                    return null;
                }
                // Load the ogg file into the buffer
                int audioFormat = oggFile.getChannels() > 1 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16;
                AL10.alBufferData(bufferId, audioFormat, oggFile.getData(), oggFile.getRate());
                return new ALAudioDescription(bufferId);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if ((oggFile != null) && (oggFile.getData() != null)) {
                    oggFile.getData().clear();
                }
            }
        }
    }

    /**
     * @param errorId
     * @return A string with description of the error
     */
    private String getALErrorString(int errorId) {
        switch (errorId) {
            case AL10.AL_NO_ERROR:
                return "No Error token";
            case AL10.AL_INVALID_NAME:
                return "Invalid Name parameter";
            case AL10.AL_INVALID_ENUM:
                return "Invalid parameter";
            case AL10.AL_INVALID_VALUE:
                return "Invalid enum parameter value";
            case AL10.AL_INVALID_OPERATION:
                return "Illegal call";
            case AL10.AL_OUT_OF_MEMORY:
                return "Unable to allocate memory";
            default:
                return "";
        }
    }

    /**
     * @return If possible one audio source with everything set
     */
    private ALAudioSource genAudioSource() {
        int sourceId = AL10.alGenSources();
        int errorId = AL10.alGetError();
        if (errorId == AL10.AL_NO_ERROR) {
            return new ALAudioSource(sourceId);
        } else {
            System.err.println(getALErrorString(errorId));
            return null;
        }
    }

    /**
     * @param numberOfSources Number of audio sources to generate
     * @return A list of the audio sources generated
     */
    @Override
    public List<IAudioSource> genAudioSources(int numberOfSources) {
        List<IAudioSource> sourceLst = new ArrayList<IAudioSource>();
        for (int i = 0; i < numberOfSources; i++) {
            ALAudioSource audioSource = genAudioSource();
            if (audioSource == null) {
                break;
            } else {
                sourceLst.add(audioSource);
            }
        }
        return sourceLst;
    }

    /**
     * Disposes all the information used by the audio loader
     */
    @Override
    public void dispose() {
        // Release of audio sources
        for (int sourceId : this.audioSources) {
            AL10.alSourceStop(sourceId);
            AL10.alDeleteSources(sourceId);
        }

        // Release of audio buffers
        for (int buffer : this.audioBuffers) {
            AL10.alDeleteBuffers(buffer);
        }
        // Delete the lists
        this.audioSources.clear();
        this.audioSources = null;
        this.audioBuffers.clear();
        this.audioBuffers = null;
    }
}
