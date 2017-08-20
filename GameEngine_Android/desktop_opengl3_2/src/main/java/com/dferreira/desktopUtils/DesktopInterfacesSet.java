package com.dferreira.desktopUtils;

import com.dferreira.commons.IPlaformSet;
import com.dferreira.commons.generic_player.IAudioLoader;
import com.dferreira.commons.generic_player.IListener;
import com.dferreira.commons.generic_render.IRenderAPIAccess;
import com.dferreira.commons.generic_resources.IResourceProvider;
import com.dferreira.gameEngine.al_player.ALAudioLoader;
import com.dferreira.gameEngine.al_player.ALListener;
import com.dferreira.gameEngine.gl_render.GLRenderAPIAccess;

/**
 * Has the set of interfaces that support the engine in the desktop platform
 */
public class DesktopInterfacesSet implements IPlaformSet {

    /**
     * Loader of resources
     */
    private final IResourceProvider resourceProvider;

    /**
     * API to render graphics
     */
    private final IRenderAPIAccess renderAPIAccess;

    /**
     * Listener of the audio API
     */
    private final IListener listener;

    /**
     * Loader of the audio elements
     */
    private final IAudioLoader audioLoader;

    /**
     * Constructor of the desktop interfaces provider
     */
    public DesktopInterfacesSet() {
        super();
        this.resourceProvider = new DesktopResourceProvider();
        this.renderAPIAccess = new GLRenderAPIAccess(resourceProvider);
        this.listener = new ALListener();

		/*
		 * Initializes the main variable responsible to the audio of the 3D
		 * world
		 */
        this.audioLoader = new ALAudioLoader();
    }

    /**
     * @return The interface to load resources from disk
     */
    @Override
    public IResourceProvider getResourceProvider() {
        return this.resourceProvider;
    }

    /**
     * @return Reference to the API responsible for render the scene
     */
    @Override
    public IRenderAPIAccess getRenderAPIAccess() {
        return this.renderAPIAccess;
    }

    /**
     * @return Reference to the interface of the listener of the scene
     */
    @Override
    public IListener getListener() {
        return this.listener;
    }

    /**
     * @return Loader should handle the loading of audio from disk
     */
    @Override
    public IAudioLoader getAudioLoader() {
        return this.audioLoader;
    }

    /**
     * Release the resources used by the specific APIs
     */
    public void dispose() {
        this.resourceProvider.dispose();
        this.renderAPIAccess.dispose();
        this.listener.dispose();
        this.audioLoader.dispose();
    }
}
