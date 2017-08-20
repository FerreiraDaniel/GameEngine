package com.dferreira.gameEngine.engineTester;

import com.dferreira.commons.IPlaformSet;
import com.dferreira.desktopUtils.DesktopInterfacesSet;
import com.dferreira.gameEngine.renderEngine.DisplayManager;
import com.dferreira.gameEngine.views.GameEngineRenderer;

/**
 * The entry point of the application
 */
public class MainGameLoop {

    /**
     * The main method of the application that is going to be run
     *
     * @param args the arguments passed to the application
     */
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        DisplayManager.printSystemInfo();


        IPlaformSet platformSet = new DesktopInterfacesSet();

        platformSet.getListener().init();

        GameEngineRenderer gameEngineRender = new GameEngineRenderer(platformSet);

        gameEngineRender.onSurfaceCreated();
        while (DisplayManager.closeWasNotRequested()) {
            gameEngineRender.onDrawFrame();
        }
        gameEngineRender.dispose();
        platformSet.dispose();

		/* Calls the clean up methods to free memory */

        DisplayManager.closeDisplay();
    }
}
