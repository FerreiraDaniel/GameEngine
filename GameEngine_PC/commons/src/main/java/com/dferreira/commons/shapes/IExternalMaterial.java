package com.dferreira.commons.shapes;

import com.dferreira.commons.ColorRGB;

/**
 * Interface that should be implemented for the different material of the files
 * format supported
 */
public interface IExternalMaterial {

	/**
	 * @return the name
	 */
	String getName();

	/**
	 * @return the weight specular color
	 */
	float getWeightSpecularColor();

	/**
	 * @return the specular color
	 */
	ColorRGB getSpecularColor();

	/**
	 * @return the ambient color
	 */
	ColorRGB getAmbientColor();

	/**
	 * @return the diffuse color
	 */
	ColorRGB getDiffuseColor();

	/**
	 * @return the emissive color
	 */
	ColorRGB getEmissiveColor();

	/**
	 * @return the optical density
	 */
	float getOpticalDensity();

	/**
	 * @return the dissolve factor
	 */
	float getDissolveFactor();

	/**
	 * @return the illumination model
	 */
	int getIlluminationModel();

	/**
	 * @return the transmission factor
	 */
	ColorRGB getTransmissionFactor();

	/**
	 * @return the diffuse texture FileName
	 */
	String getDiffuseTextureFileName();

	/**
	 * @return the specular texture FileName
	 */
	String getSpecularTextureFileName();

	/**
	 * @return the ambient texture FileName
	 */
	String getAmbientTextureFileName();

	/**
	 * @return the bump texture FileName
	 */
	String getBumpTextureFileName();

	/**
	 * @return the opacity texture FileName
	 */
	String getOpacityTextureFileName();
}
