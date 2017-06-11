package com.dferreira.desktopUtils;

import java.util.HashMap;
import java.util.List;

import com.dferreira.commons.LoadUtils;
import com.dferreira.commons.generic_resources.IResourceProvider;
import com.dferreira.commons.generic_resources.ISubResourceProvider;
import com.dferreira.commons.generic_resources.ModelEnum;
import com.dferreira.commons.generic_resources.TextEnum;
import com.dferreira.commons.generic_resources.TextureEnum;
import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.shapes.IExternalMaterial;
import com.dferreira.commons.shapes.IShape;
import com.dferreira.commons.utils.Utils;

/**
 * Has the actions to allow to get the resources from in desktop architecture
 */
public class DesktopResourceProvider implements IResourceProvider, ISubResourceProvider {

	private static final String SHADERS_FOLDER_PATH = "src/main/java/com/dferreira/gameEngine/gl_render/shaders/";

	private final static String RESOURCES_FOLDER = "res/";
	private final static String SKY_FOLDER = "sky/";
	private final static String TERRAIN_FOLDER = "terrain/";
	private final static String GUI_FOLDER = "gui/";

	private final static String PNG_EXTENSION = ".png";

	/**
	 * @param textEnum
	 *            The text that is to get the identifier
	 * @return The path of the text passed in the arguments of the method
	 */
	private String getResourcePath(TextEnum textEnum) {

		String fileName = textEnum.toString();

		return SHADERS_FOLDER_PATH + fileName + ".glsl";
	}

    /**
     * @param filePath Path of the texture to load
     * @return The information of the texture to load
     */
    private TextureData pGetTexture(String filePath) {
    	TextureData texture = LoadUtils.loadTexture(filePath);
        return texture;
    }
	
	/**
	 * @param textureEnum
	 *            The texture that is to get the identifier
	 * @return The path of the texture passed in the arguments of the method
	 */
	private String getResourcePath(TextureEnum textureEnum) {
		HashMap<TextureEnum, String> textureIds = new HashMap<>();

		// GUI images
		textureIds.put(TextureEnum.game_engine_logo, GUI_FOLDER + "game_engine_logo");
		textureIds.put(TextureEnum.ic_pad_up, null);
		textureIds.put(TextureEnum.ic_pad_down, null);
		textureIds.put(TextureEnum.ic_pad_right, null);
		textureIds.put(TextureEnum.ic_pad_left, null);

		// SkyBox images
		textureIds.put(TextureEnum.sky_right, SKY_FOLDER + "right");
		textureIds.put(TextureEnum.sky_left, SKY_FOLDER + "left");
		textureIds.put(TextureEnum.sky_top, SKY_FOLDER + "top");
		textureIds.put(TextureEnum.sky_bottom, SKY_FOLDER + "bottom");
		textureIds.put(TextureEnum.sky_back, SKY_FOLDER + "back");
		textureIds.put(TextureEnum.sky_front, SKY_FOLDER + "front");

		// Terrain images
		textureIds.put(TextureEnum.weight_map, TERRAIN_FOLDER + "blendMap");
		textureIds.put(TextureEnum.terrain, TERRAIN_FOLDER + "terrain");
		textureIds.put(TextureEnum.mud, TERRAIN_FOLDER + "mud");
		textureIds.put(TextureEnum.terrain_grass, TERRAIN_FOLDER + "terrain_grass");
		textureIds.put(TextureEnum.path, TERRAIN_FOLDER + "path");
		textureIds.put(TextureEnum.terrain_heightmap, TERRAIN_FOLDER + "heightmap");

		return RESOURCES_FOLDER + textureIds.get(textureEnum) + PNG_EXTENSION;
	}


	@Override
	public List<IShape> getResource(ModelEnum modelEnum) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param textureEnum
	 *            The texture to load
	 * @return The information of the texture to load
	 */
	@Override
	public TextureData getResource(TextureEnum textureEnum) {
		String path = getResourcePath(textureEnum);
		return pGetTexture(path);
	}

    /**
     * @param textureFileName Name of the file where the texture it is
     * @return The texture data of the file passed
     */
    @Override
    public TextureData getTexture(String textureFileName) {
    	if(Utils.isEmpty(textureFileName)) {
    		return null;
    	} else {
    		String path = RESOURCES_FOLDER + textureFileName;
    		return pGetTexture(path);
    	}
	}

	/**
	 * @param textEnum
	 *            The text to load
	 * @return The text loaded
	 */
	@Override
	public String getResource(TextEnum textEnum) {
		String filePath = getResourcePath(textEnum);
		return LoadUtils.readTextFromRawResource(filePath);
	}
	
	@Override
	public HashMap<String, IExternalMaterial> getMaterials(String materialFileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
