package com.dferreira.gameEngine.renderEngine;

import com.dferreira.commons.ColorRGBA;
import com.dferreira.commons.shapes.IExternalMaterial;
import com.dferreira.commons.utils.Utils;
import com.dferreira.commons.wavefront.RGBAColorEnum;
import com.dferreira.gameEngine.models.complexEntities.LightingComponent;
import com.dferreira.gameEngine.models.complexEntities.Material;

/**
 * Load the data to render the scene
 */
public class Loader {

    /**
     * Load the diffuse component of the material
     *
     * @param externalMaterial A reference to the external material to load
     * @return The diffuse lighting component of the material
     */
    private LightingComponent loadDiffuseLighting(IExternalMaterial externalMaterial) {
        float textureWeight;
        ColorRGBA color;
        String textureFileName = externalMaterial.getDiffuseTextureFileName();

        if (Utils.isEmpty(textureFileName)) {
            textureWeight = 0.0f;
            color = (externalMaterial.getDiffuseColor() == null) ? RGBAColorEnum.transparent.toRGBA() : new ColorRGBA(externalMaterial.getDiffuseColor());
            textureFileName = null;
        } else {
            textureWeight = 1.0f;
            color = RGBAColorEnum.transparent.toRGBA();
        }

        LightingComponent diffuse = new LightingComponent();
        diffuse.setTextureWeight(textureWeight);
        diffuse.setColor(color);
        diffuse.setFilename(textureFileName);

        return diffuse;
    }

    /**
     * @param externalMaterial A reference to an external material with all the information
     *                         needed to create a material
     * @return The material loaded
     */
    public Material loadMaterial(IExternalMaterial externalMaterial) {
        if (externalMaterial == null) {
            return null;
        } else {
            Material material = new Material();

            LightingComponent diffuse = loadDiffuseLighting(externalMaterial);
            material.setDiffuse(diffuse);

            return material;
        }
    }

}
