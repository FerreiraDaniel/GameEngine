package com.dferreira.game_engine.modelGenerators;

import android.content.Context;

import com.dferreira.commons.Vector3f;
import com.dferreira.game_engine.R;
import com.dferreira.game_engine.models.Player;
import com.dferreira.game_engine.models.TexturedModel;
import com.dferreira.game_engine.renderEngine.Loader;

/**
 * Responsible for creating the creating the player(s) of the scene
 */
public class WorldPlayersGenerator extends GenericEntitiesGenerator {
    /**
     * @return The model with information to generate a player
     */
    private static DefaultModelGenerator getPlayerModel() {
        /* Player model */
        DefaultModelGenerator playerModel = new DefaultModelGenerator();
        playerModel.setObjectReference(R.raw.player);
        playerModel.setTextureReference(R.mipmap.player);
        playerModel.setScale(0.5f);
        playerModel.setHasTransparency(false);
        playerModel.setNormalsPointingUp(false);


        return playerModel;
    }

    /**
     * @param loader loader that will load the entities of the 3D world
     * @return The player that is going to be used in the scene
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    public static Player getPlayer(Context context, Loader loader) {
        DefaultModelGenerator model = getPlayerModel();

        TexturedModel texturedObj = getTexturedObj(context, loader, model.getObjectReference(), model.getTextureReference(),
                model.getHasTransparency(), model.getNormalsPointingUp());
        float xPosition = 20.0f;
        float zPosition = 0.0f;
        Vector3f playerPosition = new Vector3f(xPosition, -1.0f, zPosition);
        Player player = new Player(texturedObj, playerPosition, // Position
                0.0f, 0.0f, 0.0f, // Rotation
                model.getScale() // Scale
        );
        return player;
    }
}
