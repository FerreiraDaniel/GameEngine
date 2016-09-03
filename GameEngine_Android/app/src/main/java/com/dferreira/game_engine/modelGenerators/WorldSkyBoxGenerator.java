package com.dferreira.game_engine.modelGenerators;

import android.content.Context;

import com.dferreira.game_engine.R;
import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.models.SkyBox;
import com.dferreira.game_engine.models.SkyBoxShape;
import com.dferreira.game_engine.renderEngine.Loader;
import com.dferreira.game_engine.shapes.IShape;


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
     * @param loader object that is going to read the textures of the sky
     * @return
     */
    public static SkyBox getSky(Context context, Loader loader) {
        IShape skyBoxShape = new SkyBoxShape();
        RawModel rawModel = loader.load3DPositionsToRawModel(skyBoxShape.getVertices());
        Integer textureId = loader.loadTCubeMap(context, SKY_RESOURCE_IDS);
        return new SkyBox(textureId, rawModel);
    }
}
