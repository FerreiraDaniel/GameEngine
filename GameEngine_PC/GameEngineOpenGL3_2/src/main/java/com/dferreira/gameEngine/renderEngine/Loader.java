package com.dferreira.gameEngine.renderEngine;


import com.dferreira.commons.ColorRGBA;
import com.dferreira.commons.LoadUtils;
import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.shapes.IExternalMaterial;
import com.dferreira.commons.utils.Utils;
import com.dferreira.commons.waveFront.RGBAColorEnum;
import com.dferreira.gameEngine.models.complexEntities.LightingComponent;
import com.dferreira.gameEngine.models.complexEntities.Material;

public class Loader {




	/**
	 * Folder where the resources are
	 */
	private final String RESOURCES_FOLDER = "res/";

	/**
	 * Extension of the png files
	 */
	private final String PNG_EXTENSION = ".png";

	/**
	 * Constructor of the loader class
	 */
	public Loader() {
	}



	/**
	 * Load the diffuse component of the material
	 *
	 * @param externalMaterial
	 *            A reference to the external material to load
	 * @return The diffuse lighting component of the material
	 */
	private LightingComponent loadDiffuseLighting(IExternalMaterial externalMaterial) {
		float textureWeight;
		ColorRGBA color;
		String textureFileName = externalMaterial.getDiffuseTextureFileName();

		if (Utils.isEmpty(textureFileName)) {
			textureWeight = 0.0f;
			color = (externalMaterial.getDiffuseColor() == null) ? RGBAColorEnum.transparent.toRGBA()
					: new ColorRGBA(externalMaterial.getDiffuseColor());
			textureFileName = null;
		} else {
			//textureId = this.loadTexture(externalMaterial.getDiffuseTextureFileName());
			textureWeight = 1.0f;
			color = RGBAColorEnum.transparent.toRGBA();
		}

		LightingComponent diffuse = new LightingComponent();
		diffuse.setTextureWeight(textureWeight);
		diffuse.setColor(color);
		diffuse.setFilename(textureFileName);

		return diffuse;
	}

	/**
	 * 
	 * @param externalMaterial
	 *            A reference to an external material with all the information
	 *            needed to create a material
	 * 
	 * @return The material loaded
	 */
	public Material loadMaterial(IExternalMaterial externalMaterial) {
		if (externalMaterial == null) {
			return null;
		} else {
			Material material = new Material();

			LightingComponent diffuse = loadDiffuseLighting(externalMaterial);
			material.setDiffuse(diffuse);

			return material;
		}
	}


	/**
	 * Loads the data of a texture without bind
	 * 
	 * @param fileName
	 *            Name of the file to load without the .png extension in the end
	 *
	 * @return The texture read from the file without any openGL bind
	 */
	public TextureData getTextureData(String fileName) {
		TextureData textureData = LoadUtils.loadTexture(RESOURCES_FOLDER + fileName + PNG_EXTENSION);
		return textureData;
	}

}
