package com.dferreira.game_engine.modelGenerators;

import android.content.Context;
import android.text.TextUtils;

import com.dferreira.commons.shapes.IShape;
import com.dferreira.commons.wavefront.OBJLoader;
import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.models.complexEntities.Material;
import com.dferreira.game_engine.models.complexEntities.MaterialGroup;
import com.dferreira.game_engine.models.complexEntities.RawModelMaterial;
import com.dferreira.game_engine.renderEngine.Loader;
import com.dferreira.game_engine.renderEngine.LoaderGL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Provide generic methods to generate entities
 */
class GenericEntitiesGenerator {


    /**
     * Load a textured model
     *
     * @param context           Context where this method will be called
     * @param loader            the loader of the texture
     * @param loaderGL            the loader of the texture
     * @param modelId           The name of the waveFront file without extension
     * @param hasTransparency   Flag that indicates if has transparency or not
     * @param normalsPointingUp Indicates that all the normals of the object are pointing up
     * @return the textured model loaded
     */
    static HashMap<String, MaterialGroup> getTexturedObj(Context context, Loader loader, LoaderGL loaderGL, int modelId,
                                                         boolean hasTransparency, boolean normalsPointingUp) {
        List<IShape> shapes = OBJLoader.loadObjModel(context, modelId);

        HashMap<String, MaterialGroup> groupsOfMaterials = new HashMap<>();

        for (int i = 0; i < shapes.size(); i++) {
            IShape shape = shapes.get(i);

            RawModel model = loader.loadToRawModel(shape.getVertices(), shape.getTextureCoords(), shape.getNormals(),
                    shape.getIndices());
            Material material = loader.loadMaterial(shape.getMaterial());
            if(!TextUtils.isEmpty(shape.getMaterial().getDiffuseTextureFileName()))
            {
                int textureId = loaderGL.loadTexture(context, shape.getMaterial().getDiffuseTextureFileName());
                material.setTextureId(textureId);
            }
            material.setShineDamper(10.0f);
            material.setReflectivity(1.0f);
            material.setHasTransparency(hasTransparency);
            material.setNormalsPointingUp(normalsPointingUp);
            RawModelMaterial texturedModel = new RawModelMaterial(model, material);

            List<RawModelMaterial> materials = new ArrayList<>();
            materials.add(texturedModel);
            MaterialGroup materialGroup = new MaterialGroup(materials);
            groupsOfMaterials.put(shape.getGroupName(), materialGroup);
        }

        return groupsOfMaterials;
    }
}
