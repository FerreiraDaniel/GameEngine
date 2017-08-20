package com.dferreira.desktopUtils;

import com.dferreira.commons.LoadUtils;
import com.dferreira.commons.generic_resources.AudioEnum;
import com.dferreira.commons.generic_resources.IAudioData;
import com.dferreira.commons.generic_resources.IResourceProvider;
import com.dferreira.commons.generic_resources.ISubResourceProvider;
import com.dferreira.commons.generic_resources.ModelEnum;
import com.dferreira.commons.generic_resources.TextEnum;
import com.dferreira.commons.generic_resources.TextureEnum;
import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.shapes.IExternalMaterial;
import com.dferreira.commons.shapes.IShape;
import com.dferreira.commons.utils.Utils;
import com.dferreira.commons.waveFront.MtlLoader;
import com.dferreira.commons.waveFront.OBJLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.newdawn.slick.openal.OggData;
import org.newdawn.slick.openal.OggDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Has the actions to allow to get the resources from in desktop architecture
 */
public class DesktopResourceProvider implements IResourceProvider, ISubResourceProvider {

    private static final String SHADERS_FOLDER_PATH = "shaders/";

    /*
     * Folder where the resources are
     */
    private final static String RESOURCES_FOLDER = "";
    private final static String SKY_FOLDER = "sky/";
    private final static String TERRAIN_FOLDER = "terrain/";
    private final static String GUI_FOLDER = "gui/";

    /**
     * Extension of Png images
     */
    private final static String PNG_EXTENSION = ".png";
    /**
     * Folder where the resources are
     */
    private final static String AUDIO_FOLDER = "audio/";
    /**
     * Decoder for ogg files
     */
    private static final OggDecoder oggDecoder = new OggDecoder();
    private final static Logger logger = LogManager.getLogger(DesktopResourceProvider.class);
    private final static String TAG = "OBJLoader";
    /**
     * Extension of wave front files files
     */
    private final String WAVEFRONT_EXTENSION = ".obj";
    /**
     * Extension of vorbis files
     */
    private final String OGG_EXTENSION = ".ogg";

    /**
     * @param textEnum The text that is to get the identifier
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
     * @param textureEnum The texture that is to get the identifier
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

    /**
     * @param audioEnum Enumeration of audio to get the path
     * @return The path to the audio enumeration passed
     */
    private String getResourcePath(AudioEnum audioEnum) {
        HashMap<AudioEnum, String> audioPaths = new HashMap<>();

        audioPaths.put(AudioEnum.bounce, "bounce");
        audioPaths.put(AudioEnum.breakingWood, "breaking wood");
        audioPaths.put(AudioEnum.falcon, "falcon");
        audioPaths.put(AudioEnum.footsteps, "footsteps");
        audioPaths.put(AudioEnum.wind, "wind");

        return RESOURCES_FOLDER + AUDIO_FOLDER + audioPaths.get(audioEnum) + OGG_EXTENSION;
    }

    /**
     * @param modelEnum The model to load
     * @return A resource descriptor to load the model passed as argument
     */
    @Override
    public List<IShape> getResource(ModelEnum modelEnum) {

        String objName = modelEnum.toString();

        try {
            InputStream fr = LoadUtils.getInputStreamToResource(RESOURCES_FOLDER + objName + WAVEFRONT_EXTENSION);
            List<IShape> shapes = OBJLoader.loadObjModel(fr, this);

            return shapes;
        } catch (Exception e) {
            System.err.println("Could not load file : " + modelEnum);
            e.printStackTrace();
            logger.error(TAG, e);
            return null;
        }
    }

    /**
     * @param textureEnum The texture to load
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
        if (Utils.isEmpty(textureFileName)) {
            return null;
        } else {
            String path = RESOURCES_FOLDER + textureFileName;
            return pGetTexture(path);
        }
    }


    /**
     * @param textEnum The text to load
     * @return The text loaded
     */
    @Override
    public String getResource(TextEnum textEnum) {
        String filePath = getResourcePath(textEnum);
        return LoadUtils.readTextFromRawResource(filePath);
    }

    /**
     * @param materialFileName The name of the file where the materials are
     * @return An hash with information about materials read
     */
    @Override
    public HashMap<String, IExternalMaterial> getMaterials(String materialFileName) {
        InputStream inputStream = LoadUtils.getInputStreamToResource(materialFileName);
        return MtlLoader.loadMaterials(inputStream);
    }

    /**
     * Open the input stream for a certain audio
     *
     * @param audioEnum The audio to load
     * @return Input stream to the audio to load
     */
    @Override
    public IAudioData getResource(AudioEnum audioEnum) {
        String filePath = getResourcePath(audioEnum);

        InputStream bin = null;
        OggData oggFile = null;
        try {
            bin = LoadUtils.getInputStreamToResource(filePath);
            oggFile = oggDecoder.getData(bin);

            if (oggFile == null) {
                return null;
            } else {
                return new OggAudioData(oggFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (oggFile != null) {
                oggFile.data.clear();
                oggFile = null;
            }
            try {
                if (bin != null) {
                    bin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Dispose the resources used by the resource provider
     */
    @Override
    public void dispose() {
    }

}
