package gameEngine.audioEngine;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.OggData;
import org.newdawn.slick.openal.OggDecoder;

import gameEngine.models.AudioBuffer;
import gameEngine.models.AudioSource;

/**
 * Loader of audio elements
 */
public class AudioLoader {

	/**
	 * Folder where the resources are
	 */
	private final String RESOURCES_FOLDER = "res/";

	/**
	 * Extension of vorbis files
	 */
	private final String OGG_EXTENSION = ".ogg";

	/**
	 * List of audio buffers in use by the game
	 */
	private List<Integer> audioBuffers;

	/**
	 * Decoder for ogg files
	 */
	private final OggDecoder oggDecoder;

	/**
	 * List of audio sources in use by the game
	 */
	private List<Integer> audioSources;

	/**
	 * Constructor of the loader class
	 */
	public AudioLoader() {
		this.audioBuffers = new ArrayList<Integer>();
		this.audioSources = new ArrayList<Integer>();
		this.oggDecoder = new OggDecoder();
	}

	/**
	 * Load one audio file in a buffer
	 * 
	 * @param fileName
	 *            The file name of the file to load
	 * 
	 * @return The identifier of the buffer return by open GL
	 */
	public AudioBuffer loadSound(String fileName) {
		int bufferId = AL10.alGenBuffers();
		AudioBuffer audioBuffer = null;

		/**
		 * Was not possible to one buffer return AL false
		 */
		if (AL10.alGetError() != AL10.AL_NO_ERROR) {
			return null;
		}

		FileInputStream fin = null;
		BufferedInputStream bin = null;
		OggData oggFile = null;
		try {
			fin = new FileInputStream(RESOURCES_FOLDER + fileName + OGG_EXTENSION);
			bin = new BufferedInputStream(fin);
			oggFile = oggDecoder.getData(bin);

			if (oggFile == null) {
				// Was not possible read the file so releases the resources
				AL10.alDeleteBuffers(bufferId);
			} else {
				audioBuffers.add(bufferId);
				int oggFormat = oggFile.channels > 1 ? AL10.AL_FORMAT_STEREO16 : AL10.AL_FORMAT_MONO16;
				AL10.alBufferData(bufferId, oggFormat, oggFile.data, oggFile.rate);
				// oggFile.dispose();
				audioBuffer = new AudioBuffer(bufferId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oggFile != null) {
				oggFile.data.clear();
				oggFile = null;
			}
			try {
				if (bin != null) {
					bin.close();
				}
				if (fin != null) {
					fin.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return audioBuffer;
	}

	/**
	 * 
	 * @param errorId
	 * 
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
	 * 
	 * @return If possible one audio source with everything set
	 */
	private AudioSource genAudioSource() {
		int sourceId = AL10.alGenSources();
		int errorId = AL10.alGetError();
		if (errorId == AL10.AL_NO_ERROR) {
			return new AudioSource(sourceId);
		} else {
			System.err.println(getALErrorString(errorId));
			return null;
		}
	}

	/**
	 * 
	 * @param numberOfSources
	 *            Number of audio sources to generate
	 * 
	 * @return A list of the audio sources generated
	 */
	public List<AudioSource> genAudioSources(int numberOfSources) {
		List<AudioSource> sourceLst = new ArrayList<AudioSource>();
		for (int i = 0; i < numberOfSources; i++) {
			AudioSource audioSource = genAudioSource();
			if (audioSource == null) {
				break;
			} else {
				sourceLst.add(audioSource);
			}
		}
		return sourceLst;
	}

	/**
	 * A bit o memory cleaning
	 */
	public void cleanUp() {
		// Release of audio sources
		for (int sourceId : this.audioSources) {
			AL10.alSourceStop(sourceId);
			AL10.alDeleteSources(sourceId);
		}

		// Release of audio buffers
		for (int buffer : this.audioBuffers) {
			AL10.alDeleteBuffers(buffer);
		}
		this.audioBuffers = null;
	}
}
