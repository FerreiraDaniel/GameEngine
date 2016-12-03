package gameEngine.modelGenerators;

import com.dferreira.commons.shapes.IShape;
import com.dferreira.commons.waveFront.OBJLoader;

import gameEngine.models.RawModel;
import gameEngine.models.complexEntities.Material;
import gameEngine.models.complexEntities.RawModelMaterial;
import gameEngine.renderEngine.Loader;

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
	 * 
	 * @return the textured model loaded
	 */
	protected static RawModelMaterial getTexturedObj(Loader loader, String objName, String texureName,
			boolean hasTransparency, boolean normalsPointingUp) {
		IShape shape = OBJLoader.loadObjModel(objName);

		RawModel model = loader.loadToVAO(shape.getVertices(), shape.getTextureCoords(), shape.getNormals(),
				shape.getIndices());
		Integer textureId = loader.loadTexture(texureName);
		Material material = new Material(textureId);
		material.setShineDamper(10.0f);
		material.setReflectivity(1.0f);
		material.setHasTransparency(hasTransparency);
		;
		material.setNormalsPointingUp(normalsPointingUp);
		RawModelMaterial texturedModel = new RawModelMaterial(model, material);

		return texturedModel;
	}
}
