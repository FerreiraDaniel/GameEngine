package com.dferreira.gameEngine.renderEngine;

import com.dferreira.commons.ColorRGBA;
import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.generic_render.IFrameRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.models.Light;
import com.dferreira.commons.utils.Utils;
import com.dferreira.gameEngine.models.Terrain;
import com.dferreira.gameEngine.shaders.terrains.TTerrainAttribute;
import com.dferreira.gameEngine.shaders.terrains.TerrainShaderManager;
import com.dferreira.gameEngine.textures.TerrainTexturesPack;

import java.util.List;

/**
 * Class responsible to render the terrain of the scene
 */
@SuppressWarnings("WeakerAccess")
public class TerrainRender extends GenericRender {
    /**
     * Reference to the shader manager
     */
    private final TerrainShaderManager tShader;


    /**
     * Constructor of the terrain render
     *
     * @param sManager         Shader manager
     * @param projectionMatrix The projection matrix of the render
     * @param frameRenderAPI   Reference to the API responsible for render the frame
     */
    public TerrainRender(TerrainShaderManager sManager, GLTransformation projectionMatrix, IFrameRenderAPI frameRenderAPI) {
        super(frameRenderAPI);
        this.tShader = sManager;

        sManager.start();
        sManager.loadProjectionMatrix(projectionMatrix);
        sManager.connectTextureUnits();
        sManager.stop();
    }

    /**
     * Get the transformation matrix of one entity
     *
     * @param entity Entity for which is to create the transformation matrix
     * @return The transformation matrix that put the entity in its right
     * position
     */
    private GLTransformation getTransformationMatrix(Terrain entity) {
        GLTransformation matrix = new GLTransformation();
        matrix.loadIdentity();
        matrix.translate(entity.getX(), entity.getY(), entity.getZ());
        float terrainRotation = 0.0f;
        matrix.rotate(terrainRotation, 1.0f, 0.0f, 0.0f);
        matrix.rotate(terrainRotation, 0.0f, 1.0f, 0.0f);
        matrix.rotate(terrainRotation, 0.0f, 0.0f, 1.0f);

        return matrix;
    }


    /**
     * Render the terrains in the scene
     *
     * @param skyColor   Color of the sky
     * @param lights     The lights of the scene
     * @param viewMatrix View matrix to render the scene
     * @param terrains   List of terrains of the scene
     */
    public void render(ColorRGBA skyColor, Light[] lights, GLTransformation viewMatrix, List<Terrain> terrains) {
        tShader.start();
        tShader.loadSkyColor(skyColor);
        tShader.loadLights(lights);
        tShader.loadViewMatrix(viewMatrix);

        this.render(terrains);
        tShader.stop();
    }

    /**
     * Render one list of terrains
     *
     * @param terrains List of Terrains to render
     */
    private void render(List<Terrain> terrains) {
        if (!Utils.isEmpty(terrains)) {
            for (Terrain terrain : terrains) {
                prepareTerrain(terrain);
                prepareInstance(terrain);
                render(terrain);
                unbindTexturedModel();
            }
        }
    }


    /**
     * Bind the several textures of the terrain
     */
    private void bindTextures(Terrain terrain) {
        TerrainTexturesPack texturesPackage = terrain.getTexturePack();
        this.frameRenderAPI.activeAndBindTextures(texturesPackage.getBackgroundTextureId(),
                texturesPackage.getMudTextureId(),
                texturesPackage.getGrassTextureId(),
                texturesPackage.getPathTextureId(),
                texturesPackage.getWeightMapTextureId());
    }

    /**
     * Bind the attributes of terrain with openGL
     *
     * @param terrain The terrain that have the properties to bind
     */
    private void prepareTerrain(Terrain terrain) {
        IRawModel model = terrain.getModel();


        // bind several textures of the terrain
        bindTextures(terrain);


        //Load the light properties
        tShader.loadShineVariables(1.0f, 0.0f);

        frameRenderAPI.prepareModel(model,
                TTerrainAttribute.position,
                TTerrainAttribute.textureCoords,
                TTerrainAttribute.normal
        );
    }

    /**
     * Render the terrain itself
     *
     * @param terrain the terrain to render
     */
    private void prepareInstance(Terrain terrain) {
        // Load the transformation matrix
        tShader.loadTransformationMatrix(getTransformationMatrix(terrain));
    }

    /**
     * Call the render of the triangles to the terrain itself
     *
     * @param terrain A reference to the terrain to get render
     */
    private void render(Terrain terrain) {
        IRawModel model = terrain.getModel();
        this.frameRenderAPI.drawTrianglesIndexes(model);
    }

    /**
     * UnBind the previous bound elements
     */
    private void unbindTexturedModel() {
        this.frameRenderAPI.unPrepareModel(TTerrainAttribute.position, TTerrainAttribute.textureCoords, TTerrainAttribute.normal);
    }


    /**
     * Clean up because we need to clean up when we finish the program
     */
    public void cleanUp() {
        tShader.cleanUp();
    }
}
