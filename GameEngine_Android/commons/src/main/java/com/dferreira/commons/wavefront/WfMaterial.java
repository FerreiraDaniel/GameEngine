package com.dferreira.commons.wavefront;

import com.dferreira.commons.ColorRGB;
import com.dferreira.commons.shapes.IExternalMaterial;

/**
 * Represents one material defined by one waveFront file
 */
public class WfMaterial implements IExternalMaterial {

    /**
     * Name of the material
     * <p>
     * Example: newmtl Body
     */
    private String name;

    /**
     * Weight of specular color
     * <p>
     * Pong specular component. Ranges from 0 to 1000
     * <p>
     * Example: Ns 96.078431
     */
    private float weightSpecularColor;

    /**
     * Specular color is the color of the light of a specular reflection
     * (specular reflection is the type of reflection that is characteristic of
     * light reflected from a shiny surface).
     * <p>
     * Specular color weighted by the specular coefficient
     * <p>
     * Example: Ks 0.064706 0.090196 0.131373
     */
    private ColorRGB specularColor;

    /**
     * The ambient color of the material
     * <p>
     * Example: Ka 1.000000 1.000000 1.000000
     */
    private ColorRGB ambientColor;

    /**
     * The diffuse color Diffuse color weighted by the diffuse coefficient.
     * <p>
     * Example: Kd 0.175686 0.244706 0.351373
     */
    private ColorRGB diffuseColor;

    /**
     * Defines the lighting output of the material itself Color only affect the
     * material in question, and do not cause nearby objects
     * <p>
     * Example: Ke 0.000000 0.000000 0.000000
     */
    private ColorRGB emissiveColor;

    /**
     * Is the value for the optical density. The values can range from 0.001 to
     * 10. A value of 1.0 means that light does not bend as it passes through an
     * object.
     * <p>
     * Example: Ni 1.000000
     */
    private float opticalDensity;

    /**
     * Dissolve factor (pseudo-transparency). Values are from 0-1. 0 is
     * completely transparent, 1 is opaque.
     * <p>
     * Example: d 1.000000
     */
    private float dissolveFactor;

    /**
     * Multiple illumination models are available, per material
     * <p>
     * Example: illum illum_#
     */
    private int illuminationModel;

    /**
     * Transmission filter
     * <p>
     * Example: Tf r g b
     */
    private ColorRGB transmissionFactor;

    /**
     * Diffuse color texture map.
     * <p>
     * Example: map_Kd fileName
     */
    private String diffuseTextureFileName;

    /**
     * Specular color texture map.
     * <p>
     * Example: map_Ks
     */
    private String specularTextureFileName;

    /**
     * Ambient color texture map.
     * <p>
     * Example: map_Ka
     */
    private String ambientTextureFileName;

    /**
     * Bump texture map.
     * <p>
     * Example: map_Bump
     */
    private String bumpTextureFileName;

    /**
     * Opacity texture map.
     * <p>
     * Example: map_d
     */
    private String opacityTextureFileName;

    /**
     * Constructor of wave front material
     */
    public WfMaterial() {
        super();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the weightSpecularColor
     */
    public float getWeightSpecularColor() {
        return weightSpecularColor;
    }

    /**
     * @return the specularColor
     */
    public ColorRGB getSpecularColor() {
        return specularColor;
    }

    /**
     * @return the ambientColor
     */
    public ColorRGB getAmbientColor() {
        return ambientColor;
    }

    /**
     * @return the diffuseColor
     */
    public ColorRGB getDiffuseColor() {
        return diffuseColor;
    }

    /**
     * @return the emissiveColor
     */
    public ColorRGB getEmissiveColor() {
        return emissiveColor;
    }

    /**
     * @return the opticalDensity
     */
    public float getOpticalDensity() {
        return opticalDensity;
    }

    /**
     * @return the dissolveFactor
     */
    public float getDissolveFactor() {
        return dissolveFactor;
    }

    /**
     * @return the illuminationModel
     */
    public int getIlluminationModel() {
        return illuminationModel;
    }

    /**
     * @return the transmissionFactor
     */
    public ColorRGB getTransmissionFactor() {
        return transmissionFactor;
    }

    /**
     * @return the diffuseTextureFileName
     */
    public String getDiffuseTextureFileName() {
        return diffuseTextureFileName;
    }

    /**
     * @return the specularTextureFileName
     */
    public String getSpecularTextureFileName() {
        return specularTextureFileName;
    }

    /**
     * @return the ambientTextureFileName
     */
    public String getAmbientTextureFileName() {
        return ambientTextureFileName;
    }

    /**
     * @return the bumpTextureFileName
     */
    public String getBumpTextureFileName() {
        return bumpTextureFileName;
    }

    /**
     * @return the opacityTextureFileName
     */
    public String getOpacityTextureFileName() {
        return opacityTextureFileName;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param weightSpecularColor the weightSpecularColor to set
     */
    public void setWeightSpecularColor(float weightSpecularColor) {
        this.weightSpecularColor = weightSpecularColor;
    }

    /**
     * @param specularColor the specularColor to set
     */
    public void setSpecularColor(ColorRGB specularColor) {
        this.specularColor = specularColor;
    }

    /**
     * @param ambientColor the ambientColor to set
     */
    public void setAmbientColor(ColorRGB ambientColor) {
        this.ambientColor = ambientColor;
    }

    /**
     * @param diffuseColor the diffuseColor to set
     */
    public void setDiffuseColor(ColorRGB diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    /**
     * @param emissiveColor the emissiveColor to set
     */
    public void setEmissiveColor(ColorRGB emissiveColor) {
        this.emissiveColor = emissiveColor;
    }

    /**
     * @param opticalDensity the opticalDensity to set
     */
    public void setOpticalDensity(float opticalDensity) {
        this.opticalDensity = opticalDensity;
    }

    /**
     * @param dissolveFactor the dissolveFactor to set
     */
    public void setDissolveFactor(float dissolveFactor) {
        this.dissolveFactor = dissolveFactor;
    }

    /**
     * @param illuminationModel the illuminationModel to set
     */
    public void setIlluminationModel(int illuminationModel) {
        this.illuminationModel = illuminationModel;
    }

    /**
     * @param transmissionFactor the transmissionFactor to set
     */
    public void setTransmissionFactor(ColorRGB transmissionFactor) {
        this.transmissionFactor = transmissionFactor;
    }

    /**
     * @param diffuseTextureFileName the diffuseTextureFileName to set
     */
    public void setDiffuseTextureFileName(String diffuseTextureFileName) {
        this.diffuseTextureFileName = diffuseTextureFileName;
    }

    /**
     * @param specularTextureFileName the specularTextureFileName to set
     */
    public void setSpecularTextureFileName(String specularTextureFileName) {
        this.specularTextureFileName = specularTextureFileName;
    }

    /**
     * @param ambientTextureFileName the ambientTextureFileName to set
     */
    public void setAmbientTextureFileName(String ambientTextureFileName) {
        this.ambientTextureFileName = ambientTextureFileName;
    }

    /**
     * @param bumpTextureFileName the bumpTextureFileName to set
     */
    public void setBumpTextureFileName(String bumpTextureFileName) {
        this.bumpTextureFileName = bumpTextureFileName;
    }

    /**
     * @param opacityTextureFileName the opacityTextureFileName to set
     */
    public void setOpacityTextureFileName(String opacityTextureFileName) {
        this.opacityTextureFileName = opacityTextureFileName;
    }

}