package com.dferreira.desktopUtils;

import com.dferreira.commons.generic_resources.IAudioData;

import org.newdawn.slick.openal.OggData;

import java.nio.ByteBuffer;

/**
 * Has the associated logic to load OGG files
 */
public class OggAudioData implements IAudioData {

    /**
     * The data that has been read from the OGG file
     */
    private final ByteBuffer data;

    /**
     * The sampling rate
     */
    private final int rate;

    /**
     * The number of channels in the sound file
     */
    private final int channels;

    /**
     * The constructor of the OGG audio file data
     *
     * @param oggFile Has all the information need it but does not implement the IAudioData
     */
    public OggAudioData(OggData oggFile) {
        super();
        this.data = oggFile.data;
        this.rate = oggFile.rate;
        this.channels = oggFile.channels;
    }

    /**
     * @return the data that has been read from the OGG file
     */
    public ByteBuffer getData() {
        return data;
    }

    /**
     * @return The sampling rate
     */
    public int getRate() {
        return rate;
    }

    /**
     * @return The number of channels in the sound file
     */
    public int getChannels() {
        return channels;
    }


}
