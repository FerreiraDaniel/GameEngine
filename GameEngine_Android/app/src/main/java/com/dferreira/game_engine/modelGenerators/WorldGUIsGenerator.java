package com.dferreira.game_engine.modelGenerators;

import android.content.Context;

import com.dferreira.commons.Vector2f;
import com.dferreira.game_controller.GamePadKey;
import com.dferreira.game_engine.R;
import com.dferreira.game_engine.models.GuiShape;
import com.dferreira.game_engine.models.GuiTexture;
import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.renderEngine.Loader;

/**
 * Responsible for creating the multiple GUIs to the user interact with 3D world
 */
public class WorldGUIsGenerator {
    private final static int NUMBER_OF_GUI = 8;

    /*Zoom that is going apply in the buttons of the gam*/
    private final static float BUTTONS_ZOOM = 0.07f;

    /*Position of the buttons that are upper in the Y*/
    private final static float UPPER_BUTTONS = -0.7f;

    /*Position of the buttons that are in the middle in the Y*/
    private final static float MIDDLE_BUTTONS = -0.8f;

    /*Position of the buttons that are upper in the Y*/
    private final static float BOTTOM_BUTTONS = -0.9f;


    /**
     * @param loader            The loader to load the texture of the GUI
     * @param rawMode           Shape of the GUI
     * @param textureResourceId The name of the file where the texture exists
     * @param xPosition         xPosition of the GUI
     * @param yPosition         yPosition of the GUI
     * @param scale             The scale of the GUI
     * @param key               The key that GUI match if any
     * @return The textured GUI to render GUI
     */
    @SuppressWarnings("SameParameterValue")
    private static GuiTexture getGUI(Context context, Loader loader, RawModel rawMode, int textureResourceId, float xPosition,
                                     float yPosition, float scale, GamePadKey key) {
        // Load the texture of the GUI
        Integer textureId = loader.loadTexture(context, textureResourceId);

        // Create the position of the GUI
        Vector2f guiPosition = new Vector2f(xPosition, yPosition);
        // The scale vector
        Vector2f guiScale = new Vector2f(scale, scale);
        // Create the textured GUI

        return new GuiTexture(rawMode, textureId, guiPosition, guiScale, key);
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
        GuiTexture leftButton = getGUI(context, loader, rawModel, R.drawable.ic_pad_left, -0.8f, MIDDLE_BUTTONS, BUTTONS_ZOOM, GamePadKey.left);
        GuiTexture upButton = getGUI(context, loader, rawModel, R.drawable.ic_pad_up, -0.7f, UPPER_BUTTONS, BUTTONS_ZOOM, GamePadKey.up);
        GuiTexture downButton = getGUI(context, loader, rawModel, R.drawable.ic_pad_down, -0.7f, BOTTOM_BUTTONS, BUTTONS_ZOOM, GamePadKey.down);
        GuiTexture rightButton = getGUI(context, loader, rawModel, R.drawable.ic_pad_right, -0.6f, MIDDLE_BUTTONS, BUTTONS_ZOOM, GamePadKey.right);
        GuiTexture squareButton = getGUI(context, loader, rawModel, R.drawable.ic_pad_left, 0.6f, MIDDLE_BUTTONS, BUTTONS_ZOOM, GamePadKey.square);
        GuiTexture xButton = getGUI(context, loader, rawModel, R.drawable.ic_pad_down, 0.7f, BOTTOM_BUTTONS, BUTTONS_ZOOM, GamePadKey.x);
        GuiTexture triangleButton = getGUI(context, loader, rawModel, R.drawable.ic_pad_up, 0.7f, UPPER_BUTTONS, BUTTONS_ZOOM, GamePadKey.triangle);
        GuiTexture circleButton = getGUI(context, loader, rawModel, R.drawable.ic_pad_right, 0.8f, MIDDLE_BUTTONS, BUTTONS_ZOOM, GamePadKey.circle);

        GuiTexture[] GUIs = new GuiTexture[NUMBER_OF_GUI];

        int GUIIndex = 0;
        GUIs[GUIIndex++] = leftButton;
        GUIs[GUIIndex++] = upButton;
        GUIs[GUIIndex++] = downButton;
        GUIs[GUIIndex++] = rightButton;
        GUIs[GUIIndex++] = squareButton;
        GUIs[GUIIndex++] = xButton;
        GUIs[GUIIndex++] = triangleButton;
        GUIs[GUIIndex] = circleButton;

        return GUIs;
    }
}