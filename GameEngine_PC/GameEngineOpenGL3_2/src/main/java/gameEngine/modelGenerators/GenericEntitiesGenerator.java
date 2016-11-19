package gameEngine.modelGenerators;

import com.dferreira.commons.shapes.IShape;
import com.dferreira.commons.waveFront.OBJLoader;

import gameEngine.models.RawModel;
import gameEngine.models.TexturedModel;
import gameEngine.renderEngine.Loader;
import gameEngine.textures.ModelTexture;

/**
 * Provide generic methods to generate entities
 */
public class GenericEntitiesGenerator {

	/**
	 * Load a textured model
	 *
	 * @param loader
	 *            the loader of the texture
	 * @param objName
	 *            The name of the waveFront file without extension
	 * @param texureName
	 *            The name of the image file without extension
	 * @param hasTransparency
	 *            Flag that indicates if has transparency or not
	 * @param normalsPointingUp
	 *            Indicates that all the normals of the object are pointing up
	 * @param atlasFactor
	 * 			The atlas factor of the texture
	 * 
	 * @return the textured model loaded
	 */
	protected static TexturedModel getTexturedObj(Loader loader, String objName, String texureName,
			boolean hasTransparency, boolean normalsPointingUp, int atlasFactor) {
		IShape shape = OBJLoader.loadObjModel(objName);

		RawModel model = loader.loadToVAO(shape.getVertices(), shape.getTextureCoords(), shape.getNormals(),
				shape.getIndices());
		Integer textureId = loader.loadTexture(texureName);
		ModelTexture texture = new ModelTexture(textureId);
		texture.setShineDamper(10.0f);
		texture.setReflectivity(1.0f);
		texture.setAtlasFactor(atlasFactor);
		TexturedModel texturedModel = new TexturedModel(model, texture, hasTransparency, normalsPointingUp);

		return texturedModel;
	}
}
