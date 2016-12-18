import Foundation

/**
 * Interface that should be implemented for the different material of the files
 * format supported
 */
public protocol IExternalMaterial {
    
    /**
     * @return the name
     */
    func getName() -> String?
    
    /**
     * @return the weight specular color
     */
    func getWeightSpecularColor() -> Float
    
    /**
     * @return the specular color
     */
    func getSpecularColor() -> ColorRGB?
    
    /**
     * @return the ambient color
     */
    func getAmbientColor() -> ColorRGB?
    
    /**
     * @return the diffuse color
     */
    func getDiffuseColor() -> ColorRGB?
    
    /**
     * @return the emissive color
     */
    func getEmissiveColor() -> ColorRGB?
    
    /**
     * @return the optical density
     */
    func getOpticalDensity() -> Float
    
    /**
     * @return the dissolve factor
     */
    func getDissolveFactor() -> Float
    
    /**
     * @return the illumination model
     */
    func getIlluminationModel() -> Int
    
    /**
     * @return the transmission factor
     */
    func getTransmisionFactor() -> ColorRGB?
    
    /**
     * @return the diffuse texture FileName
     */
    func getDiffuseTextureFileName() -> String?
    
    /**
     * @return the specular texture FileName
     */
    func getSpecularTextureFileName() -> String?
    
    /**
     * @return the ambient texture FileName
     */
    func getAmbientTextureFileName() -> String?
    
    /**
     * @return the bump texture FileName
     */
    func getBumpTextureFileName() -> String?
    
    /**
     * @return the opacity texture FileName
     */
    func getOpacityTextureFileName() -> String?
}
