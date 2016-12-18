import Foundation

/**
 * Represents one material defined by one waveFront file
 */
public class WfMaterial : IExternalMaterial {
    
    /**
     * Name of the material
     *
     * Example: newmtl Body
     */
    private var _name : String?;
    
    /**
     * Weight of specular color
     *
     * Phong specular component. Ranges from 0 to 1000
     *
     * Example: Ns 96.078431
     */
    private var _weightSpecularColor : Float
    
    /**
     * Specular color is the color of the light of a specular reflection
     * (specular reflection is the type of reflection that is characteristic of
     * light reflected from a shiny surface).
     *
     * Specular color weighted by the specular coefficient
     *
     * Example: Ks 0.064706 0.090196 0.131373
     */
    private var _specularColor : ColorRGB?
    
    /**
     * The ambient color of the material
     *
     * Example: Ka 1.000000 1.000000 1.000000
     */
    private var _ambientColor : ColorRGB?
    
    /**
     * The diffuse color Diffuse color weighted by the diffuse coefficient.
     *
     * Example: Kd 0.175686 0.244706 0.351373
     */
    private var _diffuseColor : ColorRGB?
    
    /**
     * Defines the lighting output of the material itself Color only affect the
     * material in question, and do not cause nearby objects
     *
     * Example: Ke 0.000000 0.000000 0.000000
     */
    private var _emissiveColor : ColorRGB?
    
    /**
     * Is the value for the optical density. The values can range from 0.001 to
     * 10. A value of 1.0 means that light does not bend as it passes through an
     * object.
     *
     * Example: Ni 1.000000
     */
    private var _opticalDensity : Float
    
    /**
     * Dissolve factor (pseudo-transparency). Values are from 0-1. 0 is
     * completely transparent, 1 is opaque.
     *
     * Example: d 1.000000
     */
    private var _dissolveFactor : Float
    
    /**
     * Multiple illumination models are available, per material
     *
     * Example: illum illum_#
     */
    private var _illuminationModel : Int
    
    /**
     * Transmission filter
     *
     * Example: Tf r g b
     */
    private var _transmissionFactor : ColorRGB?
    
    /**
     * Diffuse color texture map.
     *
     * Example: map_Kd fileName
     */
    private var _diffuseTextureFileName : String?
    
    /**
     * Specular color texture map.
     *
     * Example: map_Ks
     */
    private var _specularTextureFileName : String?
    
    /**
     * Ambient color texture map.
     *
     * Example: map_Ka
     */
    private var _ambientTextureFileName : String?
    
    /**
     * Bump texture map.
     *
     * Example: map_Bump
     */
    private var _bumpTextureFileName : String?
    
    /**
     * Opacity texture map.
     *
     * Example: map_d
     */
    private var _opacityTextureFileName : String?
    
    /**
     * Constructor of wave front material
     */
    public init() {
        self._name = nil
        self._weightSpecularColor = 0.0
        self._specularColor = nil
        self._ambientColor = nil
        self._diffuseColor = nil
        self._emissiveColor = nil
        self._opticalDensity = 0.0
        self._dissolveFactor = 0.0
        self._illuminationModel = 0
        self._transmissionFactor = nil
        self._diffuseTextureFileName = nil
        self._specularTextureFileName = nil
        self._ambientTextureFileName = nil
        self._bumpTextureFileName = nil
        self._opacityTextureFileName = nil
    }
    
    /**
     * @return the name
     */
    public func getName() -> String? {
        return self._name;
    }
    
    /**
     * @return the weightSpecularColor
     */
    public func getWeightSpecularColor() -> Float {
        return self._weightSpecularColor;
    }
    
    /**
     * @return the specularColor
     */
    public func getSpecularColor() -> ColorRGB? {
        return self._specularColor;
    }
    
    /**
     * @return the ambientColor
     */
    public func getAmbientColor() -> ColorRGB? {
        return self._ambientColor;
    }
    
    /**
     * @return the diffuseColor
     */
    public func getDiffuseColor() -> ColorRGB? {
        return self._diffuseColor;
    }
    
    /**
     * @return the emissiveColor
     */
    public func getEmissiveColor() -> ColorRGB? {
        return self._emissiveColor;
    }
    
    /**
     * @return the opticalDensity
     */
    public func getOpticalDensity() -> Float {
        return self._opticalDensity;
    }
    
    /**
     * @return the dissolveFactor
     */
    public func getDissolveFactor() -> Float {
        return self._dissolveFactor;
    }
    
    /**
     * @return the illuminationModel
     */
    public func getIlluminationModel() -> Int {
        return self._illuminationModel;
    }
    
    /**
     * @return the transmisionFactor
     */
    public func getTransmisionFactor() -> ColorRGB? {
        return self._transmissionFactor;
    }
    
    /**
     * @return the diffuseTextureFileName
     */
    public func getDiffuseTextureFileName() -> String? {
        return self._diffuseTextureFileName;
    }
    
    /**
     * @return the specularTextureFileName
     */
    public func getSpecularTextureFileName() -> String? {
        return self._specularTextureFileName;
    }
    
    /**
     * @return the ambientTextureFileName
     */
    public func getAmbientTextureFileName() -> String? {
        return self._ambientTextureFileName;
    }
    
    /**
     * @return the bumpTextureFileName
     */
    public func getBumpTextureFileName() -> String? {
        return self._bumpTextureFileName;
    }
    
    /**
     * @return the opacityTextureFileName
     */
    public func getOpacityTextureFileName() -> String? {
        return self._opacityTextureFileName;
    }
    
    /**
     * @param name
     *            the name to set
     */
    public func setName(name : String) {
        self._name = name;
    }
    
    /**
     * @param weightSpecularColor
     *            the weightSpecularColor to set
     */
    public func setWeightSpecularColor(weightSpecularColor : Float) {
        self._weightSpecularColor = weightSpecularColor;
    }
    
    /**
     * @param specularColor
     *            the specularColor to set
     */
    public func setSpecularColor(specularColor : ColorRGB) {
        self._specularColor = specularColor;
    }
    
    /**
     * @param ambientColor
     *            the ambientColor to set
     */
    public func setAmbientColor(ambientColor : ColorRGB) {
        self._ambientColor = ambientColor;
    }
    
    /**
     * @param diffuseColor
     *            the diffuseColor to set
     */
    public func setDiffuseColor(diffuseColor : ColorRGB) {
        self._diffuseColor = diffuseColor;
    }
    
    /**
     * @param emissiveColor
     *            the emissiveColor to set
     */
    public func setEmissiveColor(emissiveColor : ColorRGB) {
        self._emissiveColor = emissiveColor;
    }
    
    /**
     * @param opticalDensity
     *            the opticalDensity to set
     */
    public func setOpticalDensity(opticalDensity : Float) {
        self._opticalDensity = opticalDensity;
    }
    
    /**
     * @param dissolveFactor
     *            the dissolveFactor to set
     */
    public func setDissolveFactor(dissolveFactor : Float) {
        self._dissolveFactor = dissolveFactor;
    }
    
    /**
     * @param illuminationModel
     *            the illuminationModel to set
     */
    public func setIlluminationModel(illuminationModel : Int) {
        self._illuminationModel = illuminationModel;
    }
    
    /**
     * @param transmisionFactor
     *            the transmisionFactor to set
     */
    public func setTransmisionFactor(transmisionFactor : ColorRGB) {
        self._transmissionFactor = transmisionFactor;
    }
    
    /**
     * @param diffuseTextureFileName
     *            the diffuseTextureFileName to set
     */
    public func setDiffuseTextureFileName(diffuseTextureFileName : String) {
        self._diffuseTextureFileName = diffuseTextureFileName;
    }
    
    /**
     * @param specularTextureFileName
     *            the specularTextureFileName to set
     */
    public func setSpecularTextureFileName(specularTextureFileName : String) {
        self._specularTextureFileName = specularTextureFileName;
    }
    
    /**
     * @param ambientTextureFileName
     *            the ambientTextureFileName to set
     */
    public func setAmbientTextureFileName(ambientTextureFileName : String) {
        self._ambientTextureFileName = ambientTextureFileName;
    }
    
    /**
     * @param bumpTextureFileName
     *            the bumpTextureFileName to set
     */
    public func setBumpTextureFileName(bumpTextureFileName : String) {
        self._bumpTextureFileName = bumpTextureFileName;
    }
    
    /**
     * @param opacityTextureFileName
     *            the opacityTextureFileName to set
     */
    public func setOpacityTextureFileName(opacityTextureFileName : String) {
        self._opacityTextureFileName = opacityTextureFileName;
    }
    
}
