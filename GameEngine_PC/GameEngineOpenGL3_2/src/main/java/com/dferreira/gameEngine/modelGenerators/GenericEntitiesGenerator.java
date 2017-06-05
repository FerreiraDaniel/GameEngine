package com.dferreira.gameEngine.modelGenerators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dferreira.commons.gl_render.GLRawModel;
import com.dferreira.commons.shapes.IShape;
import com.dferreira.commons.waveFront.OBJLoader;
import com.dferreira.gameEngine.models.complexEntities.Material;
import com.dferreira.gameEngine.models.complexEntities.MaterialGroup;
import com.dferreira.gameEngine.models.complexEntities.RawModelMaterial;
import com.dferreira.gameEngine.renderEngine.Loader;

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
	 * @param hasTransparency
	 *            Flag that indicates if has transparency or not
	 * @param normalsPointingUp
	 *            Indicates that all the normals of the object are pointing up
	 * 
	 * @return the textured model loaded
	 */
	protected static HashMap<String, MaterialGroup> getTexturedObj(Loader loader, String objName,
			boolean hasTransparency, boolean normalsPointingUp) {
		List<IShape> shapes = OBJLoader.loadObjModel(objName);

		HashMap<String, MaterialGroup> groupsOfMaterials = new HashMap<>();

		for (IShape shape : shapes) {

			GLRawModel model = loader.loadToVAO(shape.getVertices(), shape.getTextureCoords(), shape.getNormals(),
					shape.getIndices());
			Material material = loader.loadMaterial(shape.getMaterial());
			material.setShineDamper(10.0f);
			material.setReflectivity(1.0f);
			material.setHasTransparency(hasTransparency);
			material.setNormalsPointingUp(normalsPointingUp);
			RawModelMaterial texturedModel = new RawModelMaterial(model, material);

			List<RawModelMaterial> materials = new ArrayList<>();
			materials.add(texturedModel);
			MaterialGroup materialGroup = new MaterialGroup(materials);
			groupsOfMaterials.put(shape.getGroupName(), materialGroup);
		}

		return groupsOfMaterials;
	}
}
