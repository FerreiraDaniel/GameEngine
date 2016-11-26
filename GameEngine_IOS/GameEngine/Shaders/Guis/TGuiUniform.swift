import Foundation

/**
 * Uniforms in the shader program of the GUIs
 */
public enum TGuiUniform : Int {
    case
    
    /**
     * Location of the transformation matrix in the program shader
     */
    transformationMatrix = 0,
    
    
    /**
     * Meta-data used maintain the existing locations
     */
    numOfGUILocations;
}
