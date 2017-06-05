package com.dferreira.gameEngine.modelGenerators;

import com.dferreira.commons.ColorRGB;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.models.Light;

/**
 * Responsible for creating the multiple lights of the 3D world
 */
public class WorldLightsGenerator {

	private final static int NUMBER_OF_LIGHTS = 4;

	/**
	 * @return The sun of the scene
	 */
	private static Light getSun() {
		Vector3f lightPosition = new Vector3f(10.0f, 100.0f, 10.0f);
		ColorRGB lightColor = new ColorRGB(1.0f, 1.0f, 1.0f);

		return new Light(lightPosition, lightColor);
	}

	/**
	 * @return A red light to the scene
	 */
	private static Light getRedLight() {
		Vector3f lightPosition = new Vector3f(10.0f, 100.0f, 10.0f);
		ColorRGB lightColor = new ColorRGB(1.0f, 0.0f, 0.0f);

		return new Light(lightPosition, lightColor);
	}

	/**
	 * @return A green light to the scene
	 */
	private static Light getGreenLight() {
		Vector3f lightPosition = new Vector3f(10.0f, 100.0f, 10.0f);
		ColorRGB lightColor = new ColorRGB(0.0f, 1.0f, 0.0f);

		return new Light(lightPosition, lightColor);
	}

	/**
	 * @return A blue light to the scene
	 */
	public static Light getBlueLight() {
		Vector3f lightPosition = new Vector3f(10.0f, 100.0f, 10.0f);
		ColorRGB lightColor = new ColorRGB(0.0f, 0.0f, 1.0f);

		return new Light(lightPosition, lightColor);
	}

	/**
	 * @return A source list of lights to the scene
	 */
	public static Light[] getLights() {
		Light[] lights = new Light[NUMBER_OF_LIGHTS];

		int index = 0;

		lights[index++] = getSun();
		lights[index++] = getRedLight();
		lights[index++] = getGreenLight();
		lights[index++] = getBlueLight();

		return lights;
	}
}
