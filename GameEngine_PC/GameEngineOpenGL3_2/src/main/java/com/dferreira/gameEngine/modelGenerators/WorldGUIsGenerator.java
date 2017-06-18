package com.dferreira.gameEngine.modelGenerators;

import java.util.HashMap;

import com.dferreira.commons.IEnum;
import com.dferreira.commons.Vector2f;
import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.generic_render.ITexture;
import com.dferreira.commons.generic_render.RenderAttributeEnum;
import com.dferreira.commons.generic_resources.TextureEnum;
import com.dferreira.commons.gl_render.GLRawModel;
import com.dferreira.commons.utils.Utils;
import com.dferreira.gameEngine.models.GuiShape;
import com.dferreira.gameEngine.models.GuiTexture;
import com.dferreira.gameEngine.renderEngine.Loader;
import com.dferreira.gameEngine.shaders.guis.TGuiAttribute;

/**
 * Responsible for creating the multiple GUIs to the user interact with 3D world
 */
public class WorldGUIsGenerator {
	
	private final static int NUMBER_OF_GUI = 1;

    /**
     * @param rawMode     Shape of the GUI
     * @param textureEnum The enum of the file where the texture exists
     * @param xPosition   xPosition of the GUI
     * @param yPosition   yPosition of the GUI
     * @param scale       The scale of the GUI
     * @return The textured GUI to render GUI
     */
    @SuppressWarnings("SameParameterValue")
    private static GuiTexture getGUI(IRawModel rawMode, TextureEnum textureEnum, float xPosition,
                                     float yPosition, float scale) {
        // Create the position of the GUI
        Vector2f guiPosition = new Vector2f(xPosition, yPosition);
        // The scale vector
        Vector2f guiScale = new Vector2f(scale, scale);
        // Create the textured GUI

        return new GuiTexture(rawMode, textureEnum, guiPosition, guiScale);
    }

	/**
	 * The GUIs of the scene
	 *
	 * @param loader
	 *            The loader in charge of loading the textures of the terrains
	 *
	 * @return list of terrains of the scene
	 */
    public static GuiTexture[] getGUIs(ILoaderRenderAPI loaderRenderAPI) {
		GuiShape guiShape = new GuiShape();
		
		HashMap<RenderAttributeEnum, IEnum> attributes = new HashMap<>();

        attributes.put(RenderAttributeEnum.position, TGuiAttribute.position);
		
        IRawModel rawModel = loaderRenderAPI.load2DPositionsToRawModel(guiShape.getVertices(), attributes);
		GuiTexture gameEngineLogo = getGUI(rawModel, TextureEnum.game_engine_logo, -0.0f, 0.9f, 0.1f);

		GuiTexture[] guis = new GuiTexture[NUMBER_OF_GUI];

		guis[0] = gameEngineLogo;

		return guis;
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
