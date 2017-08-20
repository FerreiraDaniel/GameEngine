package com.dferreira.commons;

import com.dferreira.commons.generic_player.IAudioLoader;
import com.dferreira.commons.generic_player.IListener;
import com.dferreira.commons.generic_render.IRenderAPIAccess;
import com.dferreira.commons.generic_resources.IResourceProvider;

/**
 * Has a set of methods to get the the interfaces specific from the platform
 */
public interface IPlaformSet {

	/**
	 * @return The interface to load resources from disk
	 */
	IResourceProvider getResourceProvider();

	/**
	 * 
	 * @return Loader should handle the loading of audio from disk
	 */
	IAudioLoader getAudioLoader();

	/**
	 * 
	 * @return Reference to the API responsible for render the scene
	 */
	IRenderAPIAccess getRenderAPIAccess();

	/**
	 * 
	 * @return Reference to the interface of the listener of the scene
	 */
	IListener getListener();
	
	/**
	 * Release the resources used by the specific APIs
	 */
	void dispose();
}
