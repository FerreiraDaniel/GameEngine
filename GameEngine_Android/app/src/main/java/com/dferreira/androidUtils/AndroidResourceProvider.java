package com.dferreira.androidUtils;

import android.content.Context;
import android.util.Log;

import com.dferreira.commons.generic_resources.IResourceProvider;
import com.dferreira.commons.generic_resources.ISubResourceProvider;
import com.dferreira.commons.generic_resources.ModelEnum;
import com.dferreira.commons.generic_resources.TextEnum;
import com.dferreira.commons.generic_resources.TextureEnum;
import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.shapes.IExternalMaterial;
import com.dferreira.commons.shapes.IShape;
import com.dferreira.commons.utils.Utils;
import com.dferreira.commons.wavefront.MtlLoader;
import com.dferreira.commons.wavefront.OBJLoader;
import com.dferreira.gameEngine.R;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Has the actions to allow to get the resources from in android architecture
 */
public class AndroidResourceProvider implements IResourceProvider, ISubResourceProvider {

    private final static String TAG = "AndroidResourceProvider";
    private final static String MODEL_PREFIX = "model:";
    private final static String TEXTURE_PREFIX = "texture:";
    private final static String TEXT_PREFIX = "text:";


    private final static String MTL_PREFIX = "\\.mtl";
    private final static String PNG_PREFIX = "\\.png";

    private final static String MIPMAP = "mipmap";
    private final static String RAW = "raw";


    /**
     * Context where the methods will be called
     */
    private Context context;

    public AndroidResourceProvider(Context context) {
        this.context = context;
    }


    /**
     * @param modelEnum The model that is to get the identifier
     * @return The identifier of the model passed in the arguments of the method
     */
    private int getResourceId(ModelEnum modelEnum) {
        HashMap<ModelEnum, Integer> modelIds = new HashMap<>();
        modelIds.put(ModelEnum.player, R.raw.player);
        modelIds.put(ModelEnum.fern, R.raw.fern);
        modelIds.put(ModelEnum.tree, R.raw.tree);
        modelIds.put(ModelEnum.banana_tree, R.raw.banana_tree);
        modelIds.put(ModelEnum.grass, R.raw.grass);
        modelIds.put(ModelEnum.flower, R.raw.flower);
        modelIds.put(ModelEnum.marble, R.raw.marble);

        return modelIds.get(modelEnum);
    }

    /**
     * @param textureEnum The texture that is to get the identifier
     * @return The identifier of the texture passed in the arguments of the method
     */
    private int getResourceId(TextureEnum textureEnum) {
        HashMap<TextureEnum, Integer> textureIds = new HashMap<>();


        //Pad images
        textureIds.put(TextureEnum.ic_pad_up, R.drawable.ic_pad_up);
        textureIds.put(TextureEnum.ic_pad_down, R.drawable.ic_pad_down);
        textureIds.put(TextureEnum.ic_pad_right, R.drawable.ic_pad_right);
        textureIds.put(TextureEnum.ic_pad_left, R.drawable.ic_pad_left);

        //SkyBox images
        textureIds.put(TextureEnum.sky_right, R.mipmap.sky_right);
        textureIds.put(TextureEnum.sky_left, R.mipmap.sky_left);
        textureIds.put(TextureEnum.sky_top, R.mipmap.sky_top);
        textureIds.put(TextureEnum.sky_bottom, R.mipmap.sky_bottom);
        textureIds.put(TextureEnum.sky_back, R.mipmap.sky_back);
        textureIds.put(TextureEnum.sky_front, R.mipmap.sky_front);

        //Terrain images
        textureIds.put(TextureEnum.weight_map, R.mipmap.weight_map);
        textureIds.put(TextureEnum.terrain, R.mipmap.terrain);
        textureIds.put(TextureEnum.mud, R.mipmap.mud);
        textureIds.put(TextureEnum.terrain_grass, R.mipmap.terrain_grass);
        textureIds.put(TextureEnum.path, R.mipmap.path);
        textureIds.put(TextureEnum.terrain_heightmap, R.mipmap.terrain_heightmap);


        return textureIds.get(textureEnum);
    }

    /**
     * @param textEnum The text that is to get the identifier
     * @return The identifier of the text passed in the arguments of the method
     */
    private int getResourceId(TextEnum textEnum) {
        HashMap<TextEnum, Integer> textIds = new HashMap<>();

        //Entity shader
        textIds.put(TextEnum.entity_vertex_shader, R.raw.entity_vertex_shader);
        textIds.put(TextEnum.entity_fragment_shader, R.raw.entity_fragment_shader);
        //GUI shader
        textIds.put(TextEnum.gui_vertex_shader, R.raw.gui_vertex_shader);
        textIds.put(TextEnum.gui_fragment_shader, R.raw.gui_fragment_shader);
        //Terrain shader
        textIds.put(TextEnum.terrain_vertex_shader, R.raw.terrain_vertex_shader);
        textIds.put(TextEnum.terrain_fragment_shader, R.raw.terrain_fragment_shader);
        //SkyBox shader
        textIds.put(TextEnum.sky_box_vertex_shader, R.raw.sky_box_vertex_shader);
        textIds.put(TextEnum.sky_box_fragment_shader, R.raw.sky_box_fragment_shader);

        return textIds.get(textEnum);
    }


    /**
     * @param modelEnum The model to load
     * @return A resource descriptor to load the model passed as argument
     */
    @Override
    public List<IShape> getResource(ModelEnum modelEnum) {
        ResourcesCache cache = ResourcesCache.getInstance();
        String cacheKey = MODEL_PREFIX + modelEnum;
        List<IShape> shape = (List<IShape>) cache.get(cacheKey);
        if (shape == null) {
            int resourceId = getResourceId(modelEnum);
            try {
                InputStream inputStream = context.getResources().openRawResource(resourceId);
                shape = OBJLoader.loadObjModel(inputStream, this);
                cache.put(cacheKey, shape);
            } catch (Exception e) {
                Log.e(TAG, "Could not load file!", e);
                return null;
            }
        } else {
            Log.d(TAG, "Cached: " + cacheKey);
        }
        return shape;
    }

    /**
     * @param resourceId Identifier of the resource that has the texture to load
     * @return The information of the texture to load
     */
    private TextureData pGetTexture(int resourceId) {
        ResourcesCache cache = ResourcesCache.getInstance();
        String cacheKey = TEXTURE_PREFIX + resourceId;
        TextureData texture = (TextureData) cache.get(cacheKey);
        if (texture == null) {
            texture = LoadUtils.decodeTextureFile(this.context, resourceId);
            cache.put(cacheKey, texture);
        } else {
            Log.d(TAG, "Cached: " + cacheKey);
        }
        return texture;
    }

    /**
     * @param resourceId Identifier of the resource that has the text to load
     * @return The information of the text to load
     */
    private String pGetText(int resourceId) {
        ResourcesCache cache = ResourcesCache.getInstance();
        String cacheKey = TEXT_PREFIX + resourceId;
        String text = (String) cache.get(cacheKey);
        if (text == null) {
            text = LoadUtils.readTextFromRawResource(this.context, resourceId);
            cache.put(cacheKey, text);
        } else {
            Log.d(TAG, "Cached: " + cacheKey);
        }
        return text;
    }


    /**
     * @param textureEnum The texture to load
     * @return The information of the texture to load
     */
    @Override
    public TextureData getResource(TextureEnum textureEnum) {
        int resourceId = getResourceId(textureEnum);
        return pGetTexture(resourceId);
    }

    /**
     * @param textEnum The text to load
     * @return The text loaded
     */
    @Override
    public String getResource(TextEnum textEnum) {
        int resourceId = getResourceId(textEnum);
        return pGetText(resourceId);
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
            String resourceName = textureFileName.split(PNG_PREFIX)[0];
            int resourceId = context.getResources().getIdentifier(resourceName, MIPMAP, context.getPackageName());
            return pGetTexture(resourceId);
        }
    }

    /**
     * @param materialFileName The name of the file where the materials are
     * @return An hash with information about materials read
     */
    @Override
    public HashMap<String, IExternalMaterial> getMaterials(String materialFileName) {
        if (Utils.isEmpty(materialFileName)) {
            return null;
        } else {
            try {
                String resourceName = materialFileName.split(MTL_PREFIX)[0];
                int resourceId = context.getResources().getIdentifier(resourceName, RAW, context.getPackageName());
                InputStream inputStream = context.getResources().openRawResource(resourceId);
                return MtlLoader.loadMaterials(inputStream);
            } catch (Exception e) {
                Log.e(TAG, "Could not load file!");
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
                return null;
            }
        }
    }
}
