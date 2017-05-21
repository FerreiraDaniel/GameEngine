package com.dferreira.commons.generic_render;

import com.dferreira.commons.shapes.IShape;

/**
 * Loader of the Render API
 */

public interface ILoaderRenderAPI {
    /**
     * @param fileName The identifier of texture associated with file
     * @return The identifier of the texture that much with specified file name
     */
    int getTextureId(String fileName);

    /**
     * Load a texture in the Render API
     *
     * @param resourceId The identifier of the resource where is to load the texture
     * @param repeat     Indicate that should repeat the texture if the polygon surpass the size of texture
     * @return The identifier of the texture loaded
     */
    Integer loadTexture(int resourceId, boolean repeat);

    /**
     * Load a texture in the Render API
     *
     * @param filename The filename of the texture to load
     * @param repeat   Indicate that should repeat the texture if the polygon surpass the size of texture
     * @return The identifier of the texture loaded
     */
    @SuppressWarnings("SameParameterValue")
    Integer loadTexture(String filename, boolean repeat);

    /**
     * Load a set of textures in the Render API
     *
     * @param resourcesId The identifiers of the resources to load
     * @param repeat      Indicate that should repeat the texture if the polygon surpass the size of texture
     * @return The identifier of the texture
     */
    @SuppressWarnings("SameParameterValue")
    Integer loadTCubeMap(int[] resourcesId, boolean repeat);

    /**
     * Load from a shape to one equivalent IRawModel
     *
     * @param shape The shape to load
     * @return The raw model loaded
     */
    IRawModel loadToRawModel(IShape shape);

    /**
     * Load a list of 2D positions to GLRawModel
     *
     * @param positions Positions to load
     * @return The rawModel pointing to the positions
     */
    IRawModel load2DPositionsToRawModel(float[] positions);

    /**
     * Load a list of 3D positions to GLRawModel
     *
     * @param positions Positions to load
     * @return The rawModel pointing to the positions
     */
    IRawModel load3DPositionsToRawModel(float[] positions);
}
