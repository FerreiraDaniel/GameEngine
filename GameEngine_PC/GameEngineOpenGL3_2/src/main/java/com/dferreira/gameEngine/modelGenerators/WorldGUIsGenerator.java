package com.dferreira.gameEngine.modelGenerators;

import com.dferreira.commons.Vector2f;
import com.dferreira.commons.gl_render.GLRawModel;
import com.dferreira.gameEngine.models.GuiShape;
import com.dferreira.gameEngine.models.GuiTexture;
import com.dferreira.gameEngine.renderEngine.Loader;

/**
 * Responsible for creating the multiple GUIs to the user interact with 3D world
 */
public class WorldGUIsGenerator {
	private final static String GUI_FOLDER = "gui/";
	private final static int NUMBER_OF_GUI = 1;

	/**
	 * 
	 * @param loader
	 *            The loader to load the texture of the GUI
	 * @param rawMode
	 *            Shape of the GUI
	 * @param textureFileName
	 *            The name of the file where the texture exists
	 * @param xPosition
	 *            xPosition of the GUI
	 * @param yPosition
	 *            yPosition of the GUI
	 * @param scale
	 *            The scale of the GUI
	 * 
	 * @return The textured GUI to render GUI
	 */
	private static GuiTexture getGUI(Loader loader, GLRawModel rawMode, String textureFileName, float xPosition,
			float yPosition, float scale) {
		// Load the texture of the GUI
		Integer textureId = loader.loadTexture(GUI_FOLDER + textureFileName);

		// Create the position of the GUI
		Vector2f guiPosition = new Vector2f(xPosition, yPosition);
		// The scale vector
		Vector2f guiScale = new Vector2f(scale, scale);
		// Create the textured GUI
		GuiTexture guiTexture = new GuiTexture(rawMode, textureId, guiPosition, guiScale);

		return guiTexture;
	}

	/**
	 * The GUIs of the scene
	 *
	 * @param loader
	 *            The loader in charge of loading the textures of the terrains
	 *
	 * @return list of terrains of the scene
	 */
	public static GuiTexture[] getGUIs(Loader loader) {
		GuiShape guiShape = new GuiShape();
		GLRawModel rawModel = loader.load2DPositionsToVAO(guiShape.getVertices());
		GuiTexture gameEngineLogo = getGUI(loader, rawModel, "game_engine_logo", -0.0f, 0.9f, 0.1f);

		GuiTexture[] guis = new GuiTexture[NUMBER_OF_GUI];

		guis[0] = gameEngineLogo;

		return guis;
	}
}
