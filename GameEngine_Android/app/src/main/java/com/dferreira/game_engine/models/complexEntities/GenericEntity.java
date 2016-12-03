package com.dferreira.game_engine.models.complexEntities;


/**
 * Generic entity without any specific of a determined entity
 */
public class GenericEntity {
    /* 3D model to be render */
    private RawModelMaterial model;

    /**
     *
     * Constructor of the generic entity to be render in the 3D world
     *
     * @param model
     *            Textured model
     *
     */
    public GenericEntity(RawModelMaterial model) {
        super();
        this.model = model;
    }

    /**
     * @return the textured model
     */
    public RawModelMaterial getModel() {
        return model;
    }

    /**
     * @param model
     *            The textured model to be set
     */
    public void setModel(RawModelMaterial model) {
        this.model = model;
    }
}
