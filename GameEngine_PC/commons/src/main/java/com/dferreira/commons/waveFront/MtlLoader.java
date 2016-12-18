package com.dferreira.commons.waveFront;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dferreira.commons.ColorRGB;
import com.dferreira.commons.shapes.IExternalMaterial;

/**
 * Parses files formated in wavefront format That describes a set of material
 */
public class MtlLoader extends GenericLoader {

	private final static Logger logger = LogManager.getLogger(MtlLoader.class);
	private final static String TAG = "MtlLoader";

	/**
	 * 
	 * @param line
	 * 
	 * @return Parse one element with only one component
	 */
	private static float parseComponent(String[] line) {
		return Float.parseFloat(line[ComponentPositions.r]);
	}

	/**
	 * Parses one RGB color
	 * 
	 * @param line
	 *            line to get parsed
	 * 
	 * @return The color that got parsed
	 */
	private static ColorRGB parseRgbColor(String[] line) {
		float r = Float.parseFloat(line[ComponentPositions.r]);
		float g = Float.parseFloat(line[ComponentPositions.g]);
		float b = Float.parseFloat(line[ComponentPositions.b]);

		return new ColorRGB(r, g, b);
	}

	/**
	 * Parses one waveFront file contain a list of material
	 * 
	 * @param fileName
	 *            Name of the file without extension
	 * 
	 * @return A list with information about material read
	 */
	private static List<IExternalMaterial> lLoadObjModel(String fileName) {

		FileReader fr = null;
		BufferedReader reader = null;
		String line;
		List<IExternalMaterial> materials = null;

		try {
			File file = new File("res/" + fileName);
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.err.println("Could not load file!");
			e.printStackTrace();
			logger.error(TAG, e);
			return null;
		}

		try {
			reader = new BufferedReader(fr);
			materials = new ArrayList<>();
			WfMaterial currentMaterial = null;

			while ((line = reader.readLine()) != null) {
				if ("".equals(line.trim())) {
					continue;
				}

				String[] currentLine = line.split(SPLIT_TOKEN);
				String prefix = currentLine[0];
				switch (prefix) {
				// Comment in the MTL file gets ignored
				case MtlPrefix.COMMENT:
					break;
				// Gets the information that should parse a new material
				case MtlPrefix.NEW_MATERIAL:
					currentMaterial = new WfMaterial();
					currentMaterial.setName(currentLine[1]);
					materials.add(currentMaterial);
					break;
				case MtlPrefix.WEIGHT_SPECULAR_COLOR:
					float weightSpecularColor = parseComponent(currentLine);
					currentMaterial.setWeightSpecularColor(weightSpecularColor);
					break;
				case MtlPrefix.SPECULAR_COLOR:
					ColorRGB specularColor = parseRgbColor(currentLine);
					currentMaterial.setSpecularColor(specularColor);
					break;
				case MtlPrefix.AMBIENT_COLOR:
					ColorRGB ambientColor = parseRgbColor(currentLine);
					currentMaterial.setAmbientColor(ambientColor);
					break;
				case MtlPrefix.DIFFUSE_COLOR:
					ColorRGB diffuseColor = parseRgbColor(currentLine);
					currentMaterial.setDiffuseColor(diffuseColor);
					break;
				case MtlPrefix.EMISSIVE_COLOR:
					ColorRGB emissiveColor = parseRgbColor(currentLine);
					currentMaterial.setEmissiveColor(emissiveColor);
					break;
				case MtlPrefix.OPTICAL_DENSITY:
					float opticalDensity = parseComponent(currentLine);
					currentMaterial.setOpticalDensity(opticalDensity);
					break;
				case MtlPrefix.DISSOLVE_FACTOR:
					float dissolveFactor = parseComponent(currentLine);
					currentMaterial.setDissolveFactor(dissolveFactor);
					break;
				case MtlPrefix.DISSOLVE_FACTOR_INVERTED:
					float dissolveFactorInverted = parseComponent(currentLine);
					currentMaterial.setDissolveFactor(1.0f - dissolveFactorInverted);
					break;
				case MtlPrefix.ILLUMINATION_MODEL:
					float illuminationModel = parseComponent(currentLine);
					currentMaterial.setIlluminationModel(new Float(illuminationModel).intValue());
					break;
				case MtlPrefix.TRANSMISSION_FILTER:
					ColorRGB transmissionFilter = parseRgbColor(currentLine);
					currentMaterial.setTransmisionFactor(transmissionFilter);
					break;
				case MtlPrefix.COLOR_TEXTURE_MAP:
					String textureFileName = parseStringComponent(line, currentLine);
					currentMaterial.setDiffuseTextureFileName(textureFileName);
					break;
				case MtlPrefix.SPECULAR_COLOR_TEXTURE_MAP:
					String specularFileName = parseStringComponent(line, currentLine);
					currentMaterial.setSpecularTextureFileName(specularFileName);
					break;
				case MtlPrefix.AMBIENT_COLOR_TEXTURE_MAP:
					String ambientFileName = parseStringComponent(line, currentLine);
					currentMaterial.setAmbientTextureFileName(ambientFileName);
					break;
				case MtlPrefix.BUMP_TEXTURE_MAP:
				case MtlPrefix.BUMP_TEXTURE_MAP_V2:
				case MtlPrefix.BUMP_TEXTURE_MAP_V3:
					String bumpFileName = parseStringComponent(line, currentLine);
					currentMaterial.setBumpTextureFileName(bumpFileName);
					break;
				case MtlPrefix.OPACITY_MAP:
					String opacityFileName = parseStringComponent(line, currentLine);
					currentMaterial.setOpacityTextureFileName(opacityFileName);
					break;

				// The material defines something that is not supported by the
				// parser
				default:
					logger.warn("line:" + line + " Not supported");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				reader.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return materials;
	}

	/**
	 * Creates an hashMap of the materials that were parsed in order to make
	 * faster associations
	 * 
	 * @param materials
	 *            List of materials
	 * 
	 * @return the HashMap with materials
	 */
	private static HashMap<String, IExternalMaterial> buildMapOfMaterials(List<IExternalMaterial> materials) {
		if ((materials == null) || (materials.isEmpty())) {
			return null;
		} else {
			HashMap<String, IExternalMaterial> mapOfMaterials = new HashMap<>();
			for (int i = 0; i < materials.size(); i++) {
				IExternalMaterial material = materials.get(i);
				mapOfMaterials.put(material.getName(), material);
			}
			return mapOfMaterials;
		}
	}

	/**
	 * Parses one waveFront file contain a list of material
	 * 
	 * @param fileName
	 *            Name of the file without extension
	 * 
	 * @return An hash with information about materials read
	 */
	public static HashMap<String, IExternalMaterial> loadObjModel(String fileName) {
		return buildMapOfMaterials(MtlLoader.lLoadObjModel(fileName));
	}
}
