package com.dferreira.game_engine.renderEngine;

import android.opengl.GLES10;
import android.opengl.GLES20;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.Light;
import com.dferreira.game_engine.models.Player;
import com.dferreira.game_engine.models.RawModel;
import com.dferreira.game_engine.models.complexEntities.Entity;
import com.dferreira.game_engine.models.complexEntities.GenericEntity;
import com.dferreira.game_engine.models.complexEntities.Material;
import com.dferreira.game_engine.models.complexEntities.MaterialGroup;
import com.dferreira.game_engine.models.complexEntities.RawModelMaterial;
import com.dferreira.game_engine.shaders.entities.EntityShaderManager;
import com.dferreira.game_engine.shaders.entities.TEntityAttribute;

import java.util.HashMap;
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
    public void render(Vector3f skyColor, Light sun, GLTransformation viewMatrix, Map<GenericEntity, List<Entity>> entities) {
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
    private void render(Map<GenericEntity, List<Entity>> entities) {
        if ((entities != null) && (!entities.isEmpty())) {
            for (GenericEntity genericEntity : entities.keySet()) {
                HashMap<String, MaterialGroup> groupsOfMaterials = genericEntity.getGroupsOfMaterials();
                for (String groupName : groupsOfMaterials.keySet()) {
                    MaterialGroup materialGroup = groupsOfMaterials.get(groupName);
                    for (RawModelMaterial rawModelMaterial : materialGroup.getMaterials()) {
                        RawModel model = rawModelMaterial.getRawModel();
                        Material material = rawModelMaterial.getMaterial();
                        prepareMaterial(material);
                        prepareModel(model);
                        List<Entity> batch = entities.get(genericEntity);
                        for (Entity entity : batch) {
                            loadEntityTransformation(entity);
                            render(model);
                        }
                        unPrepareModel();
                        unPrepareMaterial(material);
                    }
                }
            }
        }
    }

    /**
     * Render one player of the scene
     *
     * @param player the player that is to render in the scene
     */
    private void renderPlayer(Player player) {
        GenericEntity genericEntity = player.getGenericEntity();
        HashMap<String, MaterialGroup> groupsOfMaterials = genericEntity.getGroupsOfMaterials();
        for (String groupName : groupsOfMaterials.keySet()) {
            MaterialGroup materialGroup = groupsOfMaterials.get(groupName);
            for (RawModelMaterial rawModelMaterial : materialGroup.getMaterials()) {
                RawModel model = rawModelMaterial.getRawModel();
                Material material = rawModelMaterial.getMaterial();
                prepareMaterial(material);
                prepareModel(model);
                loadEntityTransformation(player);
                render(model);
                unPrepareModel();
                unPrepareMaterial(material);
            }
        }
    }

    /**
     * Bind the model to render the entity with openGL
     *
     * @param model Model that contains the model of the entity with textures
     */
    private void prepareModel(RawModel model) {
        //Enable the attributes to bind
        GLES20.glEnableVertexAttribArray(TEntityAttribute.position.getValue());
        GLES20.glEnableVertexAttribArray(TEntityAttribute.textureCoords.getValue());
        GLES20.glEnableVertexAttribArray(TEntityAttribute.normal.getValue());

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
     * Bind the attributes of the material with openGL
     *
     * @param material Contains a reference to the material to bind
     */
    private void prepareMaterial(Material material) {

        //Enable the culling to not force the render of polygons that are not going to be visible
        if (!material.hasTransparency()) {
            enableCulling();
        }


        //Enable the specific texture
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, material.getTextureId());

        // Load if should put the normals of the entity point up or not
        eShader.loadNormalsPointingUp(material.areNormalsPointingUp());

        //Load the light properties
        eShader.loadShineVariables(material.getShineDamper(), material.getReflectivity());
    }


    /**
     * Load the transformation matrix of the entity
     *
     * @param entity Entity that is to get prepared to be loaded
     */
    private void loadEntityTransformation(Entity entity) {
        // Load the transformation matrix
        eShader.loadTransformationMatrix(getTransformationMatrix(entity));
    }

    /**
     * Call the render of the triangles to the model
     *
     * @param model Raw model to get render
     */
    private void render(RawModel model) {
        //Specify the indexes
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, model.getNumOfIndexes(),
                GLES20.GL_UNSIGNED_INT, model.getIndexBuffer());
    }

    /**
     * UnBind the previous bound elements
     */
    private void unPrepareModel() {
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
     * UnBind the attributes of the material with openGL
     *
     * @param material Contains a reference to the material to bind
     */
    private void unPrepareMaterial(Material material) {
        // Restore the state if has transparency
        if (!material.hasTransparency()) {
            disableCulling();
        }
    }

    /**
     * Clean up because we need to clean up when we finish the program
     */
    public void cleanUp() {
        this.eShader.cleanUp();
    }
}
