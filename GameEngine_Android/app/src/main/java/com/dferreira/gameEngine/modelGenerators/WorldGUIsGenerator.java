package com.dferreira.gameEngine.modelGenerators;

import com.dferreira.commons.IEnum;
import com.dferreira.commons.Vector2f;
import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.generic_render.ITexture;
import com.dferreira.commons.generic_render.RenderAttributeEnum;
import com.dferreira.commons.generic_resources.TextureEnum;
import com.dferreira.commons.utils.Utils;
import com.dferreira.gameController.GamePadKey;
import com.dferreira.gameEngine.models.GuiShape;
import com.dferreira.gameEngine.models.GuiTexture;
import com.dferreira.gameEngine.shaders.guis.TGuiAttribute;

import java.util.HashMap;

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
     * @param rawMode     Shape of the GUI
     * @param textureEnum The enum of the file where the texture exists
     * @param xPosition   xPosition of the GUI
     * @param yPosition   yPosition of the GUI
     * @param scale       The scale of the GUI
     * @param key         The key that GUI match if any
     * @return The textured GUI to render GUI
     */
    @SuppressWarnings("SameParameterValue")
    private static GuiTexture getGUI(IRawModel rawMode, TextureEnum textureEnum, float xPosition,
                                     float yPosition, float scale, GamePadKey key) {
        // Create the position of the GUI
        Vector2f guiPosition = new Vector2f(xPosition, yPosition);
        // The scale vector
        Vector2f guiScale = new Vector2f(scale, scale);
        // Create the textured GUI

        return new GuiTexture(rawMode, textureEnum, guiPosition, guiScale, key);
    }

    /**
     * The GUIs of the scene
     *
     * @param loaderRenderAPI The loader in charge of loading the textures of the guis
     * @return list of guis of the scene
     */
    public static GuiTexture[] getGUIs(ILoaderRenderAPI loaderRenderAPI) {
        GuiShape guiShape = new GuiShape();

        HashMap<RenderAttributeEnum, IEnum> attributes = new HashMap<>();

        attributes.put(RenderAttributeEnum.position, TGuiAttribute.position);

        IRawModel rawModel = loaderRenderAPI.load2DPositionsToRawModel(guiShape.getVertices(), attributes);
        GuiTexture leftButton = getGUI(rawModel, TextureEnum.ic_pad_left, -0.8f, MIDDLE_BUTTONS, BUTTONS_ZOOM, GamePadKey.left);
        GuiTexture upButton = getGUI(rawModel, TextureEnum.ic_pad_up, -0.7f, UPPER_BUTTONS, BUTTONS_ZOOM, GamePadKey.up);
        GuiTexture downButton = getGUI(rawModel, TextureEnum.ic_pad_down, -0.7f, BOTTOM_BUTTONS, BUTTONS_ZOOM, GamePadKey.down);
        GuiTexture rightButton = getGUI(rawModel, TextureEnum.ic_pad_right, -0.6f, MIDDLE_BUTTONS, BUTTONS_ZOOM, GamePadKey.right);
        GuiTexture squareButton = getGUI(rawModel, TextureEnum.ic_pad_left, 0.6f, MIDDLE_BUTTONS, BUTTONS_ZOOM, GamePadKey.square);
        GuiTexture xButton = getGUI(rawModel, TextureEnum.ic_pad_down, 0.7f, BOTTOM_BUTTONS, BUTTONS_ZOOM, GamePadKey.x);
        GuiTexture triangleButton = getGUI(rawModel, TextureEnum.ic_pad_up, 0.7f, UPPER_BUTTONS, BUTTONS_ZOOM, GamePadKey.triangle);
        GuiTexture circleButton = getGUI(rawModel, TextureEnum.ic_pad_right, 0.8f, MIDDLE_BUTTONS, BUTTONS_ZOOM, GamePadKey.circle);

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

    /**
     * Loads the textures of the guis
     *
     * @param loaderRenderAPI Loader to load the raw model
     * @param GUIs            List of the GUIs in the scene
     */
    public static void loadTextures(ILoaderRenderAPI loaderRenderAPI, GuiTexture[] GUIs) {
        if (!Utils.isEmpty(GUIs)) {
            for (GuiTexture guiTexture : GUIs) {
                // Load the texture of the GUI
                ITexture texture = loaderRenderAPI.loadTexture(guiTexture.getTextureEnum(), false);
                guiTexture.setTexture(texture);
            }
        }
    }
}