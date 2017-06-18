package com.dferreira.gameEngine.renderEngine;


import com.dferreira.commons.ColorRGBA;
import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.generic_render.IFrameRenderAPI;
import com.dferreira.commons.generic_render.IRawModel;
import com.dferreira.commons.models.Light;
import com.dferreira.commons.utils.Utils;
import com.dferreira.gameEngine.models.Player;
import com.dferreira.gameEngine.models.complexEntities.Entity;
import com.dferreira.gameEngine.models.complexEntities.GenericEntity;
import com.dferreira.gameEngine.models.complexEntities.LightingComponent;
import com.dferreira.gameEngine.models.complexEntities.Material;
import com.dferreira.gameEngine.models.complexEntities.MaterialGroup;
import com.dferreira.gameEngine.models.complexEntities.RawModelMaterial;
import com.dferreira.gameEngine.shaders.entities.EntityShaderManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class responsible to render the entities in the screen
 */
@SuppressWarnings("WeakerAccess")
public class EntityRender extends GenericRender {

    /**
     * Reference to the shader manager
     */
    private final EntityShaderManager eShader;

    /**
     * Initializer of the entity render
     *
     * @param sManager         Shader manager
     * @param projectionMatrix The projection matrix of the render
     * @param frameRenderAPI   Reference to the API responsible for render the frame
     */
    public EntityRender(EntityShaderManager sManager, GLTransformation projectionMatrix, IFrameRenderAPI frameRenderAPI) {
        super(frameRenderAPI);
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
        matrix.loadIdentity();
        matrix.translate(entity.getPosition().x, entity.getPosition().y, entity.getPosition().z);
        //Rotate the entity
        matrix.rotate(entity.getRotX(), 1.0f, 0.0f, 0.0f);
        matrix.rotate(entity.getRotY(), 0.0f, 1.0f, 0.0f);
        matrix.rotate(entity.getRotZ(), 0.0f, 0.0f, 1.0f);

        //Scale the entity
        matrix.scale(entity.getScale(), entity.getScale(), entity.getScale());

        return matrix;
    }


    /**
     * Render the entities in the scene
     *
     * @param skyColor   Color of the sky
     * @param lights     The lights of the scene
     * @param viewMatrix View matrix to render the scene
     * @param entities   List of entities of the scene
     * @param player     The player of the scene
     */
    public void render(ColorRGBA skyColor, Light[] lights, GLTransformation viewMatrix,
                       Map<GenericEntity, List<Entity>> entities, Player player) {
        eShader.start();
        eShader.loadSkyColor(skyColor);
        eShader.loadLights(lights);
        eShader.loadViewMatrix(viewMatrix);

        this.render(entities);
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
        if (!Utils.isEmpty(entities)) {
            for (GenericEntity genericEntity : entities.keySet()) {
                HashMap<String, MaterialGroup> groupsOfMaterials = genericEntity.getGroupsOfMaterials();
                for (String groupName : groupsOfMaterials.keySet()) {
                    MaterialGroup materialGroup = groupsOfMaterials.get(groupName);
                    for (RawModelMaterial rawModelMaterial : materialGroup.getMaterials()) {
                        IRawModel model = rawModelMaterial.getRawModel();
                        Material material = rawModelMaterial.getMaterial();
                        prepareMaterial(material);
                        prepareModel(model);
                        List<Entity> batch = entities.get(genericEntity);
                        for (Entity entity : batch) {
                            loadEntityTransformation(entity);
                            render(model);
                        }
                        unPrepareModel(model);
                        unPrepareMaterial(material);
                    }
                }
            }
        }
    }

    /**
     * Render one player_mtl of the scene
     *
     * @param player the player_mtl that is to render in the scene
     */
    private void renderPlayer(Player player) {
        GenericEntity genericEntity = player.getGenericEntity();
        HashMap<String, MaterialGroup> groupsOfMaterials = genericEntity.getGroupsOfMaterials();
        for (String groupName : groupsOfMaterials.keySet()) {
            MaterialGroup materialGroup = groupsOfMaterials.get(groupName);
            for (RawModelMaterial rawModelMaterial : materialGroup.getMaterials()) {
                IRawModel model = rawModelMaterial.getRawModel();
                Material material = rawModelMaterial.getMaterial();
                prepareMaterial(material);
                prepareModel(model);
                loadEntityTransformation(player);
                render(model);
                unPrepareModel(model);
                unPrepareMaterial(material);
            }
        }
    }

    /**
     * Bind the model to render the entity with openGL
     *
     * @param model Model that contains the model of the entity with textures
     */
    private void prepareModel(IRawModel model) {
        frameRenderAPI.prepareModel(model);
    }

    /**
     * Prepares the shader to render a lighting component
     *
     * @param component The component to be prepared
     */
    private void prepareLightingComponent(LightingComponent component) {
        if (component.getTextureWeight() > 0.0f) {
            this.frameRenderAPI.activeAndBindTexture(component.getTexture());
        }

        // Load the texture weight of the material
        eShader.loadTextureWeight(component.getTextureWeight());

        // Load the diffuse color of the material
        eShader.loadDiffuseColor(component.getColor());
    }

    /**
     * Bind the attributes of the material with openGL
     *
     * @param material Contains a reference to the material to bind
     */
    private void prepareMaterial(Material material) {

        //Enable the culling to not force the render of polygons that are not going to be visible
        if (!material.hasTransparency()) {
            this.frameRenderAPI.enableCulling();
        }


        // Load if should put the normals of the entity point up or not
        eShader.loadNormalsPointingUp(material.areNormalsPointingUp());

        //Load the light properties
        eShader.loadShineVariables(material.getShineDamper(), material.getReflectivity());

        prepareLightingComponent(material.getDiffuse());
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
    private void render(IRawModel model) {
        this.frameRenderAPI.drawTrianglesIndexes(model);
    }

    /**
     * UnBind the previous bound elements
     *
     * @param model Model to unPrepare
     */
    private void unPrepareModel(IRawModel model) {
        this.frameRenderAPI.unPrepareModel(model);
    }

    /**
     * UnBind the attributes of the material with openGL
     *
     * @param material Contains a reference to the material to bind
     */
    private void unPrepareMaterial(Material material) {
        // Restore the state if has transparency
        if (!material.hasTransparency()) {
            this.frameRenderAPI.disableCulling();
        }
    }

    /**
     * Clean up because we need to clean up when we finish the program
     */
    @Override
    public void dispose() {
        this.eShader.dispose();
    }
}
