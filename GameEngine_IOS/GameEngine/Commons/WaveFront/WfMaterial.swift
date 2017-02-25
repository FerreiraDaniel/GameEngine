import Foundation

/**
 * Represents one material defined by one waveFront file
 */
open class WfMaterial : IExternalMaterial {
    
    /**
     * Name of the material
     *
     * Example: newmtl Body
     */
    fileprivate var _name : String?;
    
    /**
     * Weight of specular color
     *
     * Phong specular component. Ranges from 0 to 1000
     *
     * Example: Ns 96.078431
     */
    fileprivate var _weightSpecularColor : Float
    
    /**
     * Specular color is the color of the light of a specular reflection
     * (specular reflection is the type of reflection that is characteristic of
     * light reflected from a shiny surface).
     *
     * Specular color weighted by the specular coefficient
     *
     * Example: Ks 0.064706 0.090196 0.131373
     */
    fileprivate var _specularColor : ColorRGB?
    
    /**
     * The ambient color of the material
     *
     * Example: Ka 1.000000 1.000000 1.000000
     */
    fileprivate var _ambientColor : ColorRGB?
    
    /**
     * The diffuse color Diffuse color weighted by the diffuse coefficient.
     *
     * Example: Kd 0.175686 0.244706 0.351373
     */
    fileprivate var _diffuseColor : ColorRGB?
    
    /**
     * Defines the lighting output of the material itself Color only affect the
     * material in question, and do not cause nearby objects
     *
     * Example: Ke 0.000000 0.000000 0.000000
     */
    fileprivate var _emissiveColor : ColorRGB?
    
    /**
     * Is the value for the optical density. The values can range from 0.001 to
     * 10. A value of 1.0 means that light does not bend as it passes through an
     * object.
     *
     * Example: Ni 1.000000
     */
    fileprivate var _opticalDensity : Float
    
    /**
     * Dissolve factor (pseudo-transparency). Values are from 0-1. 0 is
     * completely transparent, 1 is opaque.
     *
     * Example: d 1.000000
     */
    fileprivate var _dissolveFactor : Float
    
    /**
     * Multiple illumination models are available, per material
     *
     * Example: illum illum_#
     */
    fileprivate var _illuminationModel : Int
    
    /**
     * Transmission filter
     *
     * Example: Tf r g b
     */
    fileprivate var _transmissionFactor : ColorRGB?
    
    /**
     * Diffuse color texture map.
     *
     * Example: map_Kd fileName
     */
    fileprivate var _diffuseTextureFileName : String?
    
    /**
     * Specular color texture map.
     *
     * Example: map_Ks
     */
    fileprivate var _specularTextureFileName : String?
    
    /**
     * Ambient color texture map.
     *
     * Example: map_Ka
     */
    fileprivate var _ambientTextureFileName : String?
    
    /**
     * Bump texture map.
     *
     * Example: map_Bump
     */
    fileprivate var _bumpTextureFileName : String?
    
    /**
     * Opacity texture map.
     *
     * Example: map_d
     */
    fileprivate var _opacityTextureFileName : String?
    
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
    open func getName() -> String? {
        return self._name;
    }
    
    /**
     * @return the weightSpecularColor
     */
    open func getWeightSpecularColor() -> Float {
        return self._weightSpecularColor;
    }
    
    /**
     * @return the specularColor
     */
    open func getSpecularColor() -> ColorRGB? {
        return self._specularColor;
    }
    
    /**
     * @return the ambientColor
     */
    open func getAmbientColor() -> ColorRGB? {
        return self._ambientColor;
    }
    
    /**
     * @return the diffuseColor
     */
    open func getDiffuseColor() -> ColorRGB? {
        return self._diffuseColor;
    }
    
    /**
     * @return the emissiveColor
     */
    open func getEmissiveColor() -> ColorRGB? {
        return self._emissiveColor;
    }
    
    /**
     * @return the opticalDensity
     */
    open func getOpticalDensity() -> Float {
        return self._opticalDensity;
    }
    
    /**
     * @return the dissolveFactor
     */
    open func getDissolveFactor() -> Float {
        return self._dissolveFactor;
    }
    
    /**
     * @return the illuminationModel
     */
    open func getIlluminationModel() -> Int {
        return self._illuminationModel;
    }
    
    /**
     * @return the transmisionFactor
     */
    open func getTransmisionFactor() -> ColorRGB? {
        return self._transmissionFactor;
    }
    
    /**
     * @return the diffuseTextureFileName
     */
    open func getDiffuseTextureFileName() -> String? {
        return self._diffuseTextureFileName;
    }
    
    /**
     * @return the specularTextureFileName
     */
    open func getSpecularTextureFileName() -> String? {
        return self._specularTextureFileName;
    }
    
    /**
     * @return the ambientTextureFileName
     */
    open func getAmbientTextureFileName() -> String? {
        return self._ambientTextureFileName;
    }
    
    /**
     * @return the bumpTextureFileName
     */
    open func getBumpTextureFileName() -> String? {
        return self._bumpTextureFileName;
    }
    
    /**
     * @return the opacityTextureFileName
     */
    open func getOpacityTextureFileName() -> String? {
        return self._opacityTextureFileName;
    }
    
    /**
     * @param name
     *            the name to set
     */
    open func setName(_ name : String) {
        self._name = name;
    }
    
    /**
     * @param weightSpecularColor
     *            the weightSpecularColor to set
     */
    open func setWeightSpecularColor(_ weightSpecularColor : Float) {
        self._weightSpecularColor = weightSpecularColor;
    }
    
    /**
     * @param specularColor
     *            the specularColor to set
     */
    open func setSpecularColor(_ specularColor : ColorRGB) {
        self._specularColor = specularColor;
    }
    
    /**
     * @param ambientColor
     *            the ambientColor to set
     */
    open func setAmbientColor(_ ambientColor : ColorRGB) {
        self._ambientColor = ambientColor;
    }
    
    /**
     * @param diffuseColor
     *            the diffuseColor to set
     */
    open func setDiffuseColor(_ diffuseColor : ColorRGB) {
        self._diffuseColor = diffuseColor;
    }
    
    /**
     * @param emissiveColor
     *            the emissiveColor to set
     */
    open func setEmissiveColor(_ emissiveColor : ColorRGB) {
        self._emissiveColor = emissiveColor;
    }
    
    /**
     * @param opticalDensity
     *            the opticalDensity to set
     */
    open func setOpticalDensity(_ opticalDensity : Float) {
        self._opticalDensity = opticalDensity;
    }
    
    /**
     * @param dissolveFactor
     *            the dissolveFactor to set
     */
    open func setDissolveFactor(_ dissolveFactor : Float) {
        self._dissolveFactor = dissolveFactor;
    }
    
    /**
     * @param illuminationModel
     *            the illuminationModel to set
     */
    open func setIlluminationModel(_ illuminationModel : Int) {
        self._illuminationModel = illuminationModel;
    }
    
    /**
     * @param transmisionFactor
     *            the transmisionFactor to set
     */
    open func setTransmisionFactor(_ transmisionFactor : ColorRGB) {
        self._transmissionFactor = transmisionFactor;
    }
    
    /**
     * @param diffuseTextureFileName
     *            the diffuseTextureFileName to set
     */
    open func setDiffuseTextureFileName(_ diffuseTextureFileName : String) {
        self._diffuseTextureFileName = diffuseTextureFileName;
    }
    
    /**
     * @param specularTextureFileName
     *            the specularTextureFileName to set
     */
    open func setSpecularTextureFileName(_ specularTextureFileName : String) {
        self._specularTextureFileName = specularTextureFileName;
    }
    
    /**
     * @param ambientTextureFileName
     *            the ambientTextureFileName to set
     */
    open func setAmbientTextureFileName(_ ambientTextureFileName : String) {
        self._ambientTextureFileName = ambientTextureFileName;
    }
    
    /**
     * @param bumpTextureFileName
     *            the bumpTextureFileName to set
     */
    open func setBumpTextureFileName(_ bumpTextureFileName : String) {
        self._bumpTextureFileName = bumpTextureFileName;
    }
    
    /**
     * @param opacityTextureFileName
     *            the opacityTextureFileName to set
     */
    open func setOpacityTextureFileName(_ opacityTextureFileName : String) {
        self._opacityTextureFileName = opacityTextureFileName;
    }
    
}
