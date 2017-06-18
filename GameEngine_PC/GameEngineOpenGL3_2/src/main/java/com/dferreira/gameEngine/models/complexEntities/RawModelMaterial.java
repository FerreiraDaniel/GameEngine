package com.dferreira.gameEngine.models.complexEntities;

import com.dferreira.commons.generic_render.IRawModel;

/**
 * Wrapper that besides of have the raw model also has the material to put in the model
 */
public class RawModelMaterial {

    /**
     * Raw model of the entity
     */
    private final IRawModel rawModel;

    /**
     * Reference to the material of the entity
     */
    private final Material material;

    /**
     * Constructor of the textured model
     *
     * @param rawModel Raw model of the entity
     * @param material Reference to the material of the entity
     */
    public RawModelMaterial(IRawModel rawModel, Material material) {
        super();
        this.rawModel = rawModel;
        this.material = material;
    }


    /**
     * @return the raw model of the entity
     */
    public IRawModel getRawModel() {
        return rawModel;
    }


    /**
     * @return the description of the material of the entity
     */
    public Material getMaterial() {
        return material;
    }
}