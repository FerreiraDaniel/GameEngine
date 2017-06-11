package com.dferreira.commons.generic_render;

import com.dferreira.commons.generic_resources.TextureEnum;
import com.dferreira.commons.models.TextureData;
import com.dferreira.commons.shapes.IShape;

/**
 * Loader of the Render API
 */

public interface ILoaderRenderAPI {


    /**
     * Indicates if the API passed as argument is possible to be used for render
     * the scene or not
     *
     * @param supportedRenderAPI    API to get tested
     *
     * @return False -> The API is not supported
     *          True -> The API is supported
     */
    boolean detectSupportedAPI(SupportedRenderAPIEnum supportedRenderAPI);


    /**
     * Load a texture in the Render API
     *
     * @param textureEnum Enum of the resource where the texture exists
     * @param repeat     Indicate that should repeat the texture if the polygon surpass the size of texture
     * @return The identifier of the texture loaded
     */
    Integer loadTexture(TextureEnum textureEnum, boolean repeat);

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
     * Loads the data of a texture without bind
     *
     * @param textureEnum id of the resource where the texture exists
     *
     * @return The texture read from the file without any openGL bind
     */
    public TextureData getTextureData(TextureEnum textureEnum);

    /**
     * Load a set of textures in the Render API
     *
     * @param textures The resources where should get the images of the cube
     * @param repeat      Indicate that should repeat the texture if the polygon surpass the size of texture
     * @return The identifier of the texture
     */
    @SuppressWarnings("SameParameterValue")
    Integer loadTCubeMap(TextureEnum[] textures, boolean repeat);

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
