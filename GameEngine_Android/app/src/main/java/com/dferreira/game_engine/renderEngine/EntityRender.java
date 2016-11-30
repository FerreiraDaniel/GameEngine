package com.dferreira.game_engine.renderEngine;

import android.opengl.GLES10;
import android.opengl.GLES20;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.Light;
import com.dferreira.game_engine.models.Entity;
import com.dferreira.game_engine.models.Player;
import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.models.TexturedModel;
import com.dferreira.game_engine.shaders.entities.EntityShaderManager;
import com.dferreira.game_engine.shaders.entities.TEntityAttribute;
import com.dferreira.game_engine.textures.ModelTexture;

import java.util.List;
import java.util.Map;

/**
 * Class responsible to render the entities in the screen
 */
@SuppressWarnings("WeakerAccess")
public class EntityRender {

    /**
     * Reference to the shader manager
     */
    private final EntityShaderManager eShader;

    /**
     * Initializer of the entity render
     *
     * @param sManager         Shader manager
     * @param projectionMatrix The projection matrix of the render
     */
    public EntityRender(EntityShaderManager sManager, GLTransformation projectionMatrix) {
        this.eShader = sManager;

        sManager.start();
        sManager.loadProjectionMatrix(projectionMatrix);
        sManager.stop();
    }

    /**
     * Get the transformation matrix of one entity
     *
     * @param entity Entity for which is to create the transformation matrix
     * @return The transformation matrix that put the entity in its right
     * position
     */
    private GLTransformation getTransformationMatrix(Entity entity) {
        GLTransformation matrix = new GLTransformation();
        matrix.glLoadIdentity();
        matrix.glTranslate(entity.getPosition().x, entity.getPosition().y, entity.getPosition().z);
        //Rotate the entity
        matrix.glRotate(entity.getRotX(), 1.0f, 0.0f, 0.0f);
        matrix.glRotate(entity.getRotY(), 0.0f, 1.0f, 0.0f);
        matrix.glRotate(entity.getRotZ(), 0.0f, 0.0f, 1.0f);

        //Scale the entity
        matrix.glScale(entity.getScale(), entity.getScale(), entity.getScale());

        return matrix;
    }


    /**
     * Render the entities in the scene
     *
     * @param skyColor   Color of the sky
     * @param sun        The source of light of the scene
     * @param viewMatrix View matrix to render the scene
     * @param entities   List of entities of the scene
     */
    public void render(Vector3f skyColor, Light sun, GLTransformation viewMatrix, Map<TexturedModel, List<Entity>> entities) {
        eShader.start();
        eShader.loadSkyColor(skyColor);
        eShader.loadLight(sun);
        eShader.loadViewMatrix(viewMatrix);

        this.render(entities);
        eShader.stop();
    }

    /**
     * Render the entities in the scene
     *
     * @param skyColor   Color of the sky
     * @param sun        The source of light of the scene
     * @param viewMatrix View matrix to render the scene
     * @param player     The player of the scene
     */
    public void render(Vector3f skyColor, Light sun, GLTransformation viewMatrix, Player player) {
        eShader.start();
        eShader.loadSkyColor(skyColor);
        eShader.loadLight(sun);
        eShader.loadViewMatrix(viewMatrix);

        this.renderPlayer(player);
        eShader.stop();
    }

    /**
     * Render one hashMap of entities where each key is a group of similar
     * entities to be render
     *
     * @param entities HashMap of entities to render
     */
    private void render(Map<TexturedModel, List<Entity>> entities) {
        if ((entities != null) && (!entities.isEmpty())) {
            for (TexturedModel model : entities.keySet()) {
                List<Entity> batch = entities.get(model);
                prepareTexturedModel(model);

                for (Entity entity : batch) {
                    prepareInstance(entity);
                    render(entity);
                }
                // Restore the state if has transparency
                if (!model.hasTransparency()) {
                    disableCulling();
                }

                unbindTexturedModel();
            }
        }
    }

    /**
     * Bind the attributes of openGL
     *
     * @param texturedModel Model that contains the model of the entity with textures
     */
    private void prepareTexturedModel(TexturedModel texturedModel) {
        RawModel model = texturedModel.getRawModel();
        ModelTexture texture = texturedModel.getTexture();

        //Enable the culling to not force the render of polygons that are not going to be visible
        if (!texturedModel.hasTransparency()) {
            enableCulling();
        }

        //Enable the attributes to bind
        GLES20.glEnableVertexAttribArray(TEntityAttribute.position.getValue());
        GLES20.glEnableVertexAttribArray(TEntityAttribute.textureCoords.getValue());
        GLES20.glEnableVertexAttribArray(TEntityAttribute.normal.getValue());

        //Enable the specific texture
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.getTextureId());

        // Load if should put the normals of the entity point up or not
        eShader.loadNormalsPointingUp(texturedModel.isNormalsPointingUp());

        //Load the light properties
        eShader.loadShineVariables(texturedModel.getShineDamper(), texturedModel.getReflectivity());

        // Load the vertex data
        GLES20.glVertexAttribPointer(TEntityAttribute.position.getValue(), RenderConstants.VERTEX_SIZE, GLES20.GL_FLOAT, RenderConstants.VERTEX_NORMALIZED, RenderConstants.STRIDE, model.getVertexBuffer());

        // Load the texture coordinate
        GLES20.glVertexAttribPointer(TEntityAttribute.textureCoords.getValue(), RenderConstants.NUMBER_COMPONENTS_PER_VERTEX_ATTR, GLES20.GL_FLOAT,
                RenderConstants.VERTEX_NORMALIZED,
                RenderConstants.STRIDE,
                model.getTexCoordinates());


        // Load the normals data
        GLES20.glVertexAttribPointer(TEntityAttribute.normal.getValue(), RenderConstants.NUMBER_COMPONENTS_PER_NORMAL_ATTR, GLES20.GL_FLOAT, RenderConstants.VERTEX_NORMALIZED, RenderConstants.STRIDE,
                model.getNormalBuffer());
    }


    /**
     * Load the transformation matrix of the entity
     *
     * @param entity Entity that is to get prepared to be loaded
     */
    private void prepareInstance(Entity entity) {
        //Load the transformation matrix
        eShader.loadTransformationMatrix(getTransformationMatrix(entity));
    }

    /**
     * Call the render of the triangles to the entity itself
     *
     * @param entity Entity to get render
     */
    private void render(Entity entity) {
        TexturedModel texturedModel = entity.getModel();
        RawModel model = texturedModel.getRawModel();

        //Specify the indexes
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, model.getNumOfIndexes(),
                GLES20.GL_UNSIGNED_INT, model.getIndexBuffer());

        // Specify the indexes
    }

    /**
     * Render one player of the scene
     *
     * @param player the player that is to render in the scene
     */
    private void renderPlayer(Player player) {
        prepareTexturedModel(player.getModel());
        prepareInstance(player);
        render(player);
        // Restore the state if has transparency
        if (!player.getModel().hasTransparency()) {
            disableCulling();
        }
        unbindTexturedModel();
    }

    /**
     * UnBind the previous bound elements
     */
    private void unbindTexturedModel() {
        GLES20.glDisableVertexAttribArray(TEntityAttribute.position.getValue());
        GLES20.glDisableVertexAttribArray(TEntityAttribute.textureCoords.getValue());
        GLES20.glDisableVertexAttribArray(TEntityAttribute.normal.getValue());
    }

    /**
     * Enable culling of faces to get better performance
     */
    private void enableCulling() {
        // Enable the GL cull face feature
        GLES10.glEnable(GLES10.GL_CULL_FACE);
        // Avoid to render faces that are away from the camera
        GLES10.glCullFace(GLES10.GL_BACK);
    }

    /**
     * Disable the culling of the faces vital for transparent model
     */
    private void disableCulling() {
        GLES10.glDisable(GLES10.GL_CULL_FACE);
    }

    /**
     * Clean up because we need to clean up when we finish the program
     */
    public void cleanUp() {
        this.eShader.cleanUp();
    }
}
