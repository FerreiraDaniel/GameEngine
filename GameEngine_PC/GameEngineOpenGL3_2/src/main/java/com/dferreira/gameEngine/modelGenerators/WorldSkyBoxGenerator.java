package com.dferreira.gameEngine.modelGenerators;

import com.dferreira.commons.gl_render.GLRawModel;
import com.dferreira.commons.shapes.IShape;
import com.dferreira.gameEngine.models.SkyBox;
import com.dferreira.gameEngine.models.SkyBoxShape;
import com.dferreira.gameEngine.renderEngine.Loader;

/**
 * Responsible for creating the sky of the 3D world
 */
public class WorldSkyBoxGenerator {
	private final static String SKY_FOLDER = "sky/";

	/* The names that the textures of the sky */
	private final static String[] TEXTURE_FILES = { SKY_FOLDER + "right", SKY_FOLDER + "left", SKY_FOLDER + "top",
			SKY_FOLDER + "bottom", SKY_FOLDER + "back", SKY_FOLDER + "front" };

	/**
	 * The sky of the 3D scene
	 * 
	 * @param loader
	 *            object that is going to read the textures of the sky
	 * 
	 * @return A reference to the sky box loaded
	 */
	public static SkyBox getSky(Loader loader) {
		IShape skyBoxShape = new SkyBoxShape();
		GLRawModel rawModel = loader.load3DPositionsToVAO(skyBoxShape.getVertices());
		Integer textureId = loader.loadTCubeMap(TEXTURE_FILES);
		return new SkyBox(textureId, rawModel);
	}
}
