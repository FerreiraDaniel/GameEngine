package com.dferreira.game_engine.models.complexEntities;

import com.dferreira.game_engine.models.RawModel;

/**
 * Wrapper that besides of have the raw model also has the material to put in the model
 */
public class RawModelMaterial {

    /**
     * Raw model of the entity
     */
    private final RawModel rawModel;

    /**
     * Reference to the material of the entity
     */
    private final Material material;

    /**
     * Constructor of the textured model
     *
     * @param rawModel          Raw model of the entity
     * @param material          Reference to the material of the entity
     */
    public RawModelMaterial(RawModel rawModel, Material material) {
        super();
        this.rawModel = rawModel;
        this.material = material;
    }


    /**
     * @return the raw model of the entity
     */
    public RawModel getRawModel() {
        return rawModel;
    }


    /**
     * @return the description of the material of the entity
     */
    public Material getMaterial() {
        return material;
    }


}
