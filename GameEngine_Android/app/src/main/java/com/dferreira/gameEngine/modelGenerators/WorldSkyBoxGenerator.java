package com.dferreira.gameEngine.modelGenerators;

import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.shapes.IShape;
import com.dferreira.gameEngine.R;
import com.dferreira.gameEngine.models.SkyBox;
import com.dferreira.gameEngine.models.SkyBoxShape;


/**
 * Responsible for creating the sky of the 3D world
 */
public class WorldSkyBoxGenerator {

    /* The names that the textures of the sky have */
    private final static int[] SKY_RESOURCE_IDS = {
            R.mipmap.sky_right,
            R.mipmap.sky_left,
            R.mipmap.sky_top,
            R.mipmap.sky_bottom,
            R.mipmap.sky_back,
            R.mipmap.sky_front};

    /**
     * The sky of the 3D scene
     *
     * @param loaderRenderAPI object that is going to read the coordinates of the sky
     * @return the reference to the sky box created
     */
    public static SkyBox getSky(ILoaderRenderAPI loaderRenderAPI) {
        IShape skyBoxShape = new SkyBoxShape();
        IRawModel rawModel = loaderRenderAPI.load3DPositionsToRawModel(skyBoxShape.getVertices());
        return new SkyBox(rawModel);
    }

    /**
     * Load the textures of the skyBox
     *
     * @param loaderRenderAPI Loader to load the raw model
     * @param skyBox          The skyBox to load the elements
     */
    public static void loadTextures(ILoaderRenderAPI loaderRenderAPI, SkyBox skyBox) {
        Integer textureId = loaderRenderAPI.loadTCubeMap(SKY_RESOURCE_IDS, false);
        skyBox.setTextureId(textureId);
    }
}
