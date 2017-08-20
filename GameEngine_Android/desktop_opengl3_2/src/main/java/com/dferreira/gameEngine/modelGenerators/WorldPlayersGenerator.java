package com.dferreira.gameEngine.modelGenerators;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.commons.generic_resources.IResourceProvider;
import com.dferreira.commons.generic_resources.ModelEnum;
import com.dferreira.gameEngine.models.Player;
import com.dferreira.gameEngine.models.complexEntities.GenericEntity;
import com.dferreira.gameEngine.models.complexEntities.MaterialGroup;
import com.dferreira.gameEngine.renderEngine.Loader;

import java.util.HashMap;

/**
 * Responsible for creating the creating the player_mtl(s) of the scene
 */
public class WorldPlayersGenerator extends GenericEntitiesGenerator {

    /**
     * @param rProvider Provider of the resources to load
     * @return The model with information to generate a player_mtl
     */
    private static DefaultModelGenerator getPlayerModel(IResourceProvider rProvider) {
        /* Player model */
        DefaultModelGenerator playerModel = new DefaultModelGenerator();
        playerModel.setObjectReference(rProvider.getResource(ModelEnum.player));
        playerModel.setObjectType(ModelEnum.player);
        playerModel.setScale(1.0f);
        playerModel.setHasTransparency(false);
        playerModel.setNormalsPointingUp(false);


        return playerModel;
    }

    /**
     * @param loader loader that will load the entities of the 3D world
     * @return The player_mtl that is going to be used in the scene
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    public static Player getPlayer(Loader loader, ILoaderRenderAPI loaderAPI, IResourceProvider resourceProvider) {
        DefaultModelGenerator model = getPlayerModel(resourceProvider);

        float xPosition = 20.0f;
        float yPosition = -1.0f;
        float zPosition = 0.0f;

        Vector3f playerPosition = new Vector3f(xPosition, yPosition, zPosition);

        //Load the obj of the player
        HashMap<String, MaterialGroup> groupsOfMaterials = getTexturedObj(loader, loaderAPI, model.getObjectReference(),
                model.getHasTransparency(), model.getNormalsPointingUp());
        GenericEntity genericEntity = new GenericEntity(groupsOfMaterials, model.getObjectType());
        //Prepare generic entity end
        Player player = new Player(genericEntity, playerPosition, // Position
                0.0f, 0.0f, 0.0f, // Rotation
                model.getScale() // Scale
        );
        return player;
    }


    /**
     * Load the textures of the players
     *
     * @param loaderRenderAPI Loader to load the raw model
     * @param player          The player which is to load the textures
     */
    public static void loadTextures(ILoaderRenderAPI loaderRenderAPI, Player player) {
        loadTexturesOfEntity(loaderRenderAPI, player);
    }
}
