import Foundation

/**
 * Parses files formated in wavefront format That describes a set of material
 */
public class MtlLoader : GenericLoader {
    
    private static let TAG : String = "MtlLoader";
    
    
    /**
     *
     * @param line
     *
     * @return Parse one element with only one component
     */
    private static func parseComponent(line : [String]) -> Float {
        return Float(line[ComponentPositions.r]) ?? 0.0
    }
    
    /**
     * Parses one RGB color
     *
     * @param line
     *            line to get parsed
     *
     * @return The color that got parsed
     */
    private static func parseRgbColor(line : [String]) -> ColorRGB {
        let r : Float = Float(line[ComponentPositions.r]) ?? 0.0
        let g : Float = Float(line[ComponentPositions.g]) ?? 0.0
        let b : Float = Float(line[ComponentPositions.b]) ?? 0.0
        
        return ColorRGB(r: r, g: g, b: b);
    }
    
    /**
     * Parses one waveFront file contain a list of material
     *
     * @param fileName
     *            Name of the file without extension
     *
     * @return A list with information about material read
     */
    private static func lLoadObjModel(fileName : String) -> [IExternalMaterial]? {
        
        var materials : [IExternalMaterial]?  = nil;
        
        //Open the obj file from the disk
        
        let fNames : Array<String> = fileName.componentsSeparatedByString(".");
        let mtlPath : String = NSBundle.mainBundle().pathForResource(fNames[0], ofType: fNames[1])!
        let file : UnsafeMutablePointer<FILE>? = fopen(mtlPath, "r")
        
        if(file != nil) {
            materials = Array<IExternalMaterial>();
            var currentMaterial : WfMaterial? = nil;
            
            
            let buffer : UnsafeMutablePointer<CChar> = UnsafeMutablePointer<CChar>.alloc(MAX_LINE_LENGTH);
            //Read the mtl file line by line
            let bytesToRead : Int32 = Int32(sizeof(CChar) * MAX_LINE_LENGTH);
            while(fgets(buffer, bytesToRead, file!) != nil) {
                let linen = String(UTF8String: UnsafePointer<CChar>(buffer))
                let line = linen?.stringByReplacingOccurrencesOfString("\n", withString: "");
                let currentLine : Array<String> = line!.componentsSeparatedByString(GenericLoader.SPLIT_TOKEN);
                let prefix : String = currentLine[0];
                
                if(prefix.characters.count == 0) {
                    continue;
                }
                
                switch (prefix) {
                // Comment in the MTL file gets ignored
                case MtlPrefix.COMMENT:
                    break;
                // Gets the information that should parse a new material
                case MtlPrefix.NEW_MATERIAL:
                    currentMaterial = WfMaterial();
                    currentMaterial!.setName(currentLine[1]);
                    materials?.append(currentMaterial!);
                    break;
                case MtlPrefix.WEIGHT_SPECULAR_COLOR:
                    let weightSpecularColor = parseComponent(currentLine);
                    currentMaterial!.setWeightSpecularColor(weightSpecularColor);
                    break;
                case MtlPrefix.SPECULAR_COLOR:
                    let specularColor = parseRgbColor(currentLine);
                    currentMaterial!.setSpecularColor(specularColor);
                    break;
                case MtlPrefix.AMBIENT_COLOR:
                    let ambientColor = parseRgbColor(currentLine);
                    currentMaterial!.setAmbientColor(ambientColor);
                    break;
                case MtlPrefix.DIFFUSE_COLOR:
                    let diffuseColor = parseRgbColor(currentLine);
                    currentMaterial!.setDiffuseColor(diffuseColor);
                    break;
                case MtlPrefix.EMISSIVE_COLOR:
                    let emissiveColor = parseRgbColor(currentLine);
                    currentMaterial!.setEmissiveColor(emissiveColor);
                    break;
                case MtlPrefix.OPTICAL_DENSITY:
                    let opticalDensity = parseComponent(currentLine);
                    currentMaterial!.setOpticalDensity(opticalDensity);
                    break;
                case MtlPrefix.DISSOLVE_FACTOR:
                    let dissolveFactor = parseComponent(currentLine);
                    currentMaterial!.setDissolveFactor(dissolveFactor);
                    break;
                case MtlPrefix.DISSOLVE_FACTOR_INVERTED:
                    let dissolveFactorInverted = parseComponent(currentLine);
                    currentMaterial!.setDissolveFactor(1.0 - dissolveFactorInverted);
                    break;
                case MtlPrefix.ILLUMINATION_MODEL:
                    let illuminationModel : Float = parseComponent(currentLine);
                    currentMaterial!.setIlluminationModel(Int(illuminationModel));
                    break;
                case MtlPrefix.TRANSMISSION_FILTER:
                    let transmissionFilter = parseRgbColor(currentLine);
                    currentMaterial!.setTransmisionFactor(transmissionFilter);
                    break;
                case MtlPrefix.COLOR_TEXTURE_MAP:
                    let textureFileName = parseStringComponent(line!, currentLine);
                    currentMaterial!.setDiffuseTextureFileName(textureFileName);
                    break;
                case MtlPrefix.SPECULAR_COLOR_TEXTURE_MAP:
                    let specularFileName = parseStringComponent(line!, currentLine);
                    currentMaterial!.setSpecularTextureFileName(specularFileName);
                    break;
                case MtlPrefix.AMBIENT_COLOR_TEXTURE_MAP:
                    let ambientFileName = parseStringComponent(line!, currentLine);
                    currentMaterial!.setAmbientTextureFileName(ambientFileName);
                    break;
                case MtlPrefix.BUMP_TEXTURE_MAP:
                    fallthrough
                case MtlPrefix.BUMP_TEXTURE_MAP_V2:
                    fallthrough
                case MtlPrefix.BUMP_TEXTURE_MAP_V3:
                    let bumpFileName = parseStringComponent(line!, currentLine);
                    currentMaterial!.setBumpTextureFileName(bumpFileName);
                    break;
                case MtlPrefix.OPACITY_MAP:
                    let opacityFileName = parseStringComponent(line!, currentLine);
                    currentMaterial!.setOpacityTextureFileName(opacityFileName);
                    break;
                    
                    // The material defines something that is not supported by the
                // parser
                default:
                    print("Impossible to parse:\(line)")
                    break
                }
            }
            buffer.dealloc(MAX_LINE_LENGTH)
            fclose(file!);
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
    private static func buildMapOfMaterials(materials : [IExternalMaterial]?) -> Dictionary<String, IExternalMaterial>? {
        if ((materials == nil) || (materials?.isEmpty)!) {
            return nil;
        } else {
            var mapOfMaterials : Dictionary<String, IExternalMaterial> = Dictionary<String, IExternalMaterial>();
            for i : Int in 0 ..< (materials?.count)! {
                let material : IExternalMaterial = materials![i]
                mapOfMaterials[material.getName()!] = material;
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
    public static func loadObjModel(fileName : String) -> Dictionary<String, IExternalMaterial>? {
        return buildMapOfMaterials(MtlLoader.lLoadObjModel(fileName));
    }
}
