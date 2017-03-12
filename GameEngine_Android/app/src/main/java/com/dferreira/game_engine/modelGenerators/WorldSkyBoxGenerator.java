package com.dferreira.game_engine.modelGenerators;

import android.content.Context;

import com.dferreira.commons.shapes.IShape;
import com.dferreira.game_engine.R;
import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.models.SkyBox;
import com.dferreira.game_engine.models.SkyBoxShape;
import com.dferreira.game_engine.renderEngine.Loader;
import com.dferreira.game_engine.renderEngine.LoaderGL;


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
     * @param loader  object that is going to read the coordinates of the sky
     * @return the reference to the sky box created
     */
    public static SkyBox getSky(Loader loader) {
        IShape skyBoxShape = new SkyBoxShape();
        RawModel rawModel = loader.load3DPositionsToRawModel(skyBoxShape.getVertices());
        return new SkyBox(rawModel);
    }

    /**
     * Load the textures of the skyBox
     *
     * @param context   Context where the method was called
     * @param loaderGL  Object that is going to read the textures of the sky
     * @param skyBox    The skyBox to load the elements
     */
    public static void loadTextures(Context context, LoaderGL loaderGL, SkyBox skyBox) {
        Integer textureId = loaderGL.loadTCubeMap(context, SKY_RESOURCE_IDS);
        skyBox.setTextureId(textureId);
    }
}
