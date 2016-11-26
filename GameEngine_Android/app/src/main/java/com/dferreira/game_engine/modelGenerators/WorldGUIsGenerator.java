package com.dferreira.game_engine.modelGenerators;

import android.content.Context;

import com.dferreira.commons.Vector2f;
import com.dferreira.game_engine.R;
import com.dferreira.game_engine.models.GuiShape;
import com.dferreira.game_engine.models.GuiTexture;
import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.renderEngine.Loader;

/**
 * Responsible for creating the multiple GUIs to the user interact with 3D world
 */
public class WorldGUIsGenerator {
    private final static int NUMBER_OF_GUI = 1;

    /**
     * @param loader            The loader to load the texture of the GUI
     * @param rawMode           Shape of the GUI
     * @param textureResourceId The name of the file where the texture exists
     * @param xPosition         xPosition of the GUI
     * @param yPosition         yPosition of the GUI
     * @param scale             The scale of the GUI
     * @return The textured GUI to render GUI
     */
    @SuppressWarnings("SameParameterValue")
    private static GuiTexture getGUI(Context context, Loader loader, RawModel rawMode, int textureResourceId, float xPosition,
                                     float yPosition, float scale) {
        // Load the texture of the GUI
        Integer textureId = loader.loadTexture(context, textureResourceId);

        // Create the position of the GUI
        Vector2f guiPosition = new Vector2f(xPosition, yPosition);
        // The scale vector
        Vector2f guiScale = new Vector2f(scale, scale);
        // Create the textured GUI

        return new GuiTexture(rawMode, textureId, guiPosition, guiScale);
    }

    /**
     * The GUIs of the scene
     *
     * @param loader The loader in charge of loading the textures of the terrains
     * @return list of terrains of the scene
     */
    public static GuiTexture[] getGUIs(Context context, Loader loader) {
        GuiShape guiShape = new GuiShape();
        RawModel rawModel = loader.load2DPositionsToRawModel(guiShape.getVertices());
        GuiTexture gameEngineLogo = getGUI(context, loader, rawModel, R.mipmap.game_engine_logo, -0.0f, 0.9f, 0.1f);

        GuiTexture[] GUIs = new GuiTexture[NUMBER_OF_GUI];

        GUIs[0] = gameEngineLogo;

        return GUIs;
    }
}