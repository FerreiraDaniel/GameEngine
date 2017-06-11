package com.dferreira.commons.generic_resources;

import com.dferreira.commons.shapes.IExternalMaterial;

import java.util.HashMap;

/**
 * Contain methods to load resources that do not make sense in one isolated mather
 */

public interface ISubResourceProvider {


    /**
     * @param materialFileName The name of the file where the materials are
     * @return An hash with information about materials read
     */
    HashMap<String, IExternalMaterial> getMaterials(String materialFileName);


}
