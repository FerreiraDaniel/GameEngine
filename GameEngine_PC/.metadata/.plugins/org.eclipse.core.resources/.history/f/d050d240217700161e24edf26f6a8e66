package gameEngine.engineTester;

import java.util.Date;

import com.dferreira.commons.models.Light;

import gameEngine.modelGenerators.WorldEntitiesGenerator;
import gameEngine.modelGenerators.WorldSkyBoxGenerator;
import gameEngine.modelGenerators.WorldTerrainsGenerator;
import gameEngine.models.Entity;
import gameEngine.models.SkyBox;
import gameEngine.models.Terrain;
import gameEngine.renderEngine.DisplayManager;
import gameEngine.renderEngine.Loader;
import gameEngine.renderEngine.MasterRender;

/**
 * The entry point of the application
 *
 */
public class MainGameLoop {

	/**
	 * The main method of the application that is going to be run
	 * 
	 * @param args
	 *            the arguments passed to the application
	 */
	public static void main(String[] args) {
		DisplayManager.createDisplay();
		DisplayManager.printSystemInfo();

		/* Initializes the main variables responsible to render the 3D world */
		Loader loader = new Loader();
		MasterRender renderer = new MasterRender();

		/* Prepares the entities that is going to be render */
		Entity[] entities = WorldEntitiesGenerator.getEntities(loader);

		/* Prepares the terrains that is going to render */
		Terrain[] terrains = WorldTerrainsGenerator.getTerrains(loader);

		/* Load the lights that is going to render*/
		Light light = WorldEntitiesGenerator.getLight();
		
		/* Load the sky box that is going to render*/
		SkyBox skyBox = WorldSkyBoxGenerator.getSky(loader);

		while (DisplayManager.closeWasNotRequested()) {
			Date startDate = new Date();

			// game logic
			renderer.processTerrains(terrains);
			renderer.processEntities(entities);
			renderer.processSkyBox(skyBox);
			
			renderer.render(light);

			DisplayManager.updateDisplay();

			// Logs frames/s
			Date endDate = new Date();
			long timeToRender = (endDate.getTime() - startDate.getTime());
			System.out.println((1000.0f / timeToRender) + "Frames/s");
		}

		/* Calls the clean up methods to free memory */
		loader.cleanUp();
		renderer.cleanUp();
		DisplayManager.closeDisplay();

	}
}
