package gameEngine.modelGenerators;

import java.util.HashMap;

import gameEngine.audioEngine.TAudioEnum;
import gameEngine.models.AudioBuffer;
import gameEngine.renderEngine.Loader;

/**
 * Generates the sounds that is going to be used by the audio engine of the game
 */
public class WorldAudioGenerator {

	/**
	 * Folder where the resources are
	 */
	private final static String AUDIO_FOLDER = "audio/";
	
	/**
	 * Creates one dictionary with buffers to the audio engine
	 */
	public static HashMap<TAudioEnum, AudioBuffer> getBuffers(Loader loader) {
		TAudioEnum[] audioTypes = TAudioEnum.values();
		HashMap<TAudioEnum, AudioBuffer> audioLibrary = new HashMap<TAudioEnum, AudioBuffer>();
		
		for(TAudioEnum tAudio : audioTypes) {
			String fileName = tAudio.getValue();
			AudioBuffer audioBuffer = loader.loadSound(AUDIO_FOLDER + fileName);
			if(audioBuffer != null) {
				audioLibrary.put(tAudio, audioBuffer);
			}
		}
		
		return audioLibrary;
	}

}
