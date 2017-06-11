package com.dferreira.commons.generic_render;

/**
 * A raw model widely depends of the render API used so this is Interface completely lets open
 * the Way who to implement it
 */

public interface IRawModel {

    /**
     * Clean the memory used by the model
     */
    void dispose();
}
