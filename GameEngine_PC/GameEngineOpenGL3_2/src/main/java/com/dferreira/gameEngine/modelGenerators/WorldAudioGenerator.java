package com.dferreira.gameEngine.modelGenerators;

import java.util.HashMap;

import com.dferreira.commons.generic_player.IAudioDescription;
import com.dferreira.commons.generic_player.IAudioLoader;
import com.dferreira.commons.generic_resources.AudioEnum;
import com.dferreira.commons.generic_resources.IResourceProvider;

/**
 * Generates the sounds that is going to be used by the audio engine of the game
 */
public class WorldAudioGenerator {

	/**
	 * Creates one dictionary with buffers to the audio engine
	 * 
	 * @param loader
	 * @param resourceProvider
	 * @return
	 */
	public static HashMap<AudioEnum, IAudioDescription> getBuffers(IAudioLoader loader, IResourceProvider resourceProvider) {
		AudioEnum[] audioTypes = AudioEnum.values();
		HashMap<AudioEnum, IAudioDescription> audioLibrary = new HashMap<AudioEnum, IAudioDescription>();

		for (AudioEnum tAudio : audioTypes) {
			IAudioDescription audioBuffer = loader.loadAudio(tAudio, resourceProvider);
			if (audioBuffer != null) {
				audioLibrary.put(tAudio, audioBuffer);
			}
		}

		return audioLibrary;
	}

}
