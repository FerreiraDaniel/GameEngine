package com.dferreira.gameEngine.modelGenerators;

import android.content.Context;

import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.shapes.IShape;
import com.dferreira.commons.utils.Utils;
import com.dferreira.commons.wavefront.OBJLoader;
import com.dferreira.gameEngine.models.complexEntities.Entity;
import com.dferreira.gameEngine.models.complexEntities.Material;
import com.dferreira.gameEngine.models.complexEntities.MaterialGroup;
import com.dferreira.gameEngine.models.complexEntities.RawModelMaterial;
import com.dferreira.gameEngine.renderEngine.Loader;

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
     * @param modelId           The name of the waveFront file without extension
     * @param hasTransparency   Flag that indicates if has transparency or not
     * @param normalsPointingUp Indicates that all the normals of the object are pointing up
     * @return the textured model loaded
     */
    static HashMap<String, MaterialGroup> getTexturedObj(Context context, Loader loader, ILoaderRenderAPI loaderAPI, int modelId,
                                                         boolean hasTransparency, boolean normalsPointingUp) {
        List<IShape> shapes = OBJLoader.loadObjModel(context, modelId);

        HashMap<String, MaterialGroup> groupsOfMaterials = new HashMap<>();

        for (IShape shape : shapes) {

            IRawModel model = loaderAPI.loadToRawModel(shape);
            Material material = loader.loadMaterial(shape.getMaterial());
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

    /**
     * Load the textures of groups of materials
     *
     * @param loaderRenderAPI   Loader to load content specific to the render API
     * @param groupsOfMaterials The groups of material that is to load their textures
     */
    private static void loadTexturesOfObj(ILoaderRenderAPI loaderRenderAPI, HashMap<String, MaterialGroup> groupsOfMaterials) {
        if (!Utils.isEmpty(groupsOfMaterials)) {
            for (String key : groupsOfMaterials.keySet()) {
                MaterialGroup materialGroups = groupsOfMaterials.get(key);
                if (!Utils.isEmpty(materialGroups.getMaterials())) {
                    for (RawModelMaterial rawModelMaterial : materialGroups.getMaterials()) {
                        Material material = rawModelMaterial.getMaterial();
                        if (!Utils.isEmpty(material.getDiffuse().getFilename())) {
                            int textureId = loaderRenderAPI.loadTexture(material.getDiffuse().getFilename(), false);
                            material.getDiffuse().setTextureId(textureId);
                        }
                    }
                }
            }
        }
    }

    /**
     * Load the textures of one entity
     *
     * @param loaderRenderAPI Loader to load content specific to the render API
     * @param entity          The entity for which is to load the textures
     */
    static void loadTexturesOfEntity(ILoaderRenderAPI loaderRenderAPI, Entity entity) {
        if ((entity != null) && (entity.getGenericEntity() != null) && (!Utils.isEmpty(entity.getGenericEntity().getGroupsOfMaterials()))) {
            HashMap<String, MaterialGroup> groupsOfMaterials = entity.getGenericEntity().getGroupsOfMaterials();
            loadTexturesOfObj(loaderRenderAPI, groupsOfMaterials);
        }


    }
}
