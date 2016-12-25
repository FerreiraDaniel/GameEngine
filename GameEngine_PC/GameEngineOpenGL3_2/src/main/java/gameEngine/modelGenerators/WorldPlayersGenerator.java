package gameEngine.modelGenerators;

import java.util.HashMap;

import com.dferreira.commons.Vector3f;

import gameEngine.models.Player;
import gameEngine.models.complexEntities.GenericEntity;
import gameEngine.models.complexEntities.MaterialGroup;
import gameEngine.models.complexEntities.TEntity;
import gameEngine.renderEngine.Loader;

/**
 * Responsible for creating the creating the player(s) of the scene
 */
public class WorldPlayersGenerator extends GenericEntitiesGenerator {

	/**
	 * 
	 * @return The model with information to generate a player
	 */
	private static DefaultModelGenerator getPlayerModel() {
		/* Player model */
		DefaultModelGenerator playerModel = new DefaultModelGenerator();
		playerModel.setObjectType(TEntity.player);
		playerModel.setScale(1.0f);
		playerModel.setHasTransparency(false);
		playerModel.setNormalsPointingUp(false);

		return playerModel;
	}

	/**
	 * 
	 * @param loader
	 *            loader that will load the entities of the 3D world
	 * 
	 * @return The player that is going to be used in the scene
	 */
	public static Player getPlayer(Loader loader) {
		DefaultModelGenerator model = getPlayerModel();
		HashMap<String, MaterialGroup> groupsOfMaterials = getTexturedObj(loader, model.getObjectType().getValue(),
				model.getHasTransparency(), model.getNormalsPointingUp());
		float xPosition = 20.0f;
		float yPosition = -1.0f;
		float zPosition = 0.0f;
		Vector3f playerPosition = new Vector3f(xPosition, yPosition, zPosition);
		// Prepare generic entity begin
		GenericEntity genericEntity = new GenericEntity(groupsOfMaterials, model.getObjectType());
		// Prepare generic entity end
		Player player = new Player(genericEntity, playerPosition, // Position
				0.0f, 0.0f, 0.0f, // Rotation
				model.getScale() // Scale
		);
		return player;
	}
}
