package gameEngine.audioEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;

/**
 * Class responsible for handling the creation, setup and close of audio
 */
public class AudioManager {

	/**
	 * Before using any openAL methods we need to initialize openAL
	 * 
	 * @return False -> One or more errors occurred True -> Everything was all
	 *         right
	 */
	public static boolean init() {
		try {
			// Setup all openAL stuff that we are going to be using
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		return (AL10.alGetError() == AL10.AL_NO_ERROR);
	}

	/**
	 * Releases the resources used by openAL
	 */
	public static void cleanUp() {
		AL.destroy();
	}
}
