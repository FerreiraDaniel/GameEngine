package com.dferreira.game_engine.modelGenerators;

import android.content.Context;

import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.models.TexturedModel;
import com.dferreira.game_engine.renderEngine.Loader;
import com.dferreira.game_engine.shapes.IShape;
import com.dferreira.game_engine.textures.ModelTexture;
import com.dferreira.game_engine.wave_front.OBJLoader;

/**
 * Provide generic methods to generate entities
 */
class GenericEntitiesGenerator {

    /**
     * Load the texture of the dragon model
     *
     * @param loader the loader of the texture
     * @return the textured model of the dragon
     */

    /**
     * Load a textured model
     *
     * @param loader            the loader of the texture
     * @param modelId           The name of the waveFront file without extension
     * @param mTextureId        The name of the image file without extension
     * @param hasTransparency   Flag that indicates if has transparency or not
     * @param normalsPointingUp Indicates that all the normals of the object are pointing up
     * @param atlasFactor       The atlas factor of the texture
     * @return the textured model loaded
     */

    protected static TexturedModel getTexturedObj(Context context, Loader loader, int modelId, int mTextureId, boolean hasTransparency, boolean normalsPointingUp, int atlasFactor) {
        IShape objModel = OBJLoader.loadObjModel(context, modelId);
        RawModel model = loader.loadToRawModel(objModel.getVertices(), objModel.getTextureCoords(),
                objModel.getNormals(), objModel.getIndices());
        Integer textureId = loader.loadTexture(context, mTextureId);
        ModelTexture texture = new ModelTexture(textureId);
        TexturedModel texturedModel = new TexturedModel(model, texture, hasTransparency, normalsPointingUp);

        texturedModel.setShineDamper(10.0f);
        texturedModel.setReflectivity(1.0f);
        texture.setAtlasFactor(atlasFactor);

        return texturedModel;
    }
}
