package com.dferreira.game_engine.modelGenerators;

import android.content.Context;

import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.models.complexEntities.Material;
import com.dferreira.game_engine.models.complexEntities.RawModelMaterial;
import com.dferreira.game_engine.renderEngine.Loader;
import com.dferreira.game_engine.shapes.IShape;
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
     * @return the textured model loaded
     */
    static RawModelMaterial getTexturedObj(Context context, Loader loader, int modelId, int mTextureId, boolean hasTransparency, boolean normalsPointingUp) {
        IShape objModel = OBJLoader.loadObjModel(context, modelId);
        RawModel model = loader.loadToRawModel(objModel.getVertices(), objModel.getTextureCoords(),
                objModel.getNormals(), objModel.getIndices());
        Integer textureId = loader.loadTexture(context, mTextureId);
        //The material
        Material material = new Material(textureId);
        material.setHasTransparency(hasTransparency);
        material.setNormalsPointingUp(normalsPointingUp);
        material.setShineDamper(10.0f);
        material.setReflectivity(1.0f);
        //

        return new RawModelMaterial(model, material);
    }
}
