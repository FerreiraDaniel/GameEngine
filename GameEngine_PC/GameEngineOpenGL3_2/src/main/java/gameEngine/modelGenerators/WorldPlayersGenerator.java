package gameEngine.modelGenerators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dferreira.commons.Vector3f;

import gameEngine.models.Player;
import gameEngine.models.complexEntities.GenericEntity;
import gameEngine.models.complexEntities.MaterialGroup;
import gameEngine.models.complexEntities.RawModelMaterial;
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
		playerModel.setObjectName("player");
		playerModel.setTextureName("player");
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
		RawModelMaterial texturedObj = getTexturedObj(loader, model.getObjectName(), model.getTextureName(),
				model.getHasTransparency(), model.getNormalsPointingUp());
		float xPosition = 20.0f;
		float yPosition = -1.0f;
		float zPosition = 0.0f;
		Vector3f playerPosition = new Vector3f(xPosition, yPosition, zPosition);
		//Prepare generic entity begin
		List<RawModelMaterial> materials = new ArrayList<>();
		materials.add(texturedObj);
		MaterialGroup materialGroup = new MaterialGroup(materials);
		HashMap<String, MaterialGroup> groupsOfMaterials = new HashMap<>();
		groupsOfMaterials.put("body", materialGroup);
		GenericEntity genericEntity = new GenericEntity(groupsOfMaterials);
		//Prepare generic entity end
		Player player = new Player(genericEntity, playerPosition, // Position
				0.0f, 0.0f, 0.0f, // Rotation
				model.getScale() // Scale
		);
		return player;
	}
}
