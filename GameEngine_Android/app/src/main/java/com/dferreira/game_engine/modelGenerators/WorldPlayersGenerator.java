package com.dferreira.game_engine.modelGenerators;

import android.content.Context;

import com.dferreira.commons.Vector3f;
import com.dferreira.commons.generic_render.ILoaderRenderAPI;
import com.dferreira.game_engine.R;
import com.dferreira.game_engine.models.Player;
import com.dferreira.game_engine.models.complexEntities.GenericEntity;
import com.dferreira.game_engine.models.complexEntities.MaterialGroup;
import com.dferreira.game_engine.renderEngine.Loader;

import java.util.HashMap;

/**
 * Responsible for creating the creating the player_mtl(s) of the scene
 */
public class WorldPlayersGenerator extends GenericEntitiesGenerator {
    /**
     * @return The model with information to generate a player_mtl
     */
    private static DefaultModelGenerator getPlayerModel() {
        /* Player model */
        DefaultModelGenerator playerModel = new DefaultModelGenerator();
        playerModel.setObjectReference(R.raw.player);
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
    public static Player getPlayer(Context context, Loader loader, ILoaderRenderAPI loaderAPI) {
        DefaultModelGenerator model = getPlayerModel();

        float xPosition = 20.0f;
        float yPosition = -1.0f;
        float zPosition = 0.0f;

        Vector3f playerPosition = new Vector3f(xPosition, yPosition, zPosition);

        //Load the obj of the player
        HashMap<String, MaterialGroup> groupsOfMaterials = getTexturedObj(context, loader, loaderAPI, model.getObjectReference(),
                model.getHasTransparency(), model.getNormalsPointingUp());
        GenericEntity genericEntity = new GenericEntity(groupsOfMaterials);
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
