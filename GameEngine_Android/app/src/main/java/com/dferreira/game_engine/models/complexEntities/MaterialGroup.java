package com.dferreira.game_engine.models.complexEntities;

import java.util.List;

/**
 * Contains a group of materials to be render in the scene As part of the the
 */
public class MaterialGroup {

    /**
     * List of rawModel materials that compose the group
     */
    private final List<RawModelMaterial> materials;

    /**
     * @param materials List of the materials that make part of the group
     */
    public MaterialGroup(List<RawModelMaterial> materials) {
        super();
        this.materials = materials;
    }

    /**
     * @return the materials
     */
    public List<RawModelMaterial> getMaterials() {
        return materials;
    }

}
