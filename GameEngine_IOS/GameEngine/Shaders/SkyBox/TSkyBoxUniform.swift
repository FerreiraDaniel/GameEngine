import Foundation

/**
* Uniforms in the shader program of the skyBox
*/
public enum TSkyBoxUniform : Int {
    case
    
    /**
    * Location of the projection matrix in the program shader
    */
    projectionMatrix = 0,
    
    /**
    * Location of the view matrix in the program shader
    */
    viewMatrix,
    
    /**
    * Meta-data used maintain the existing locations
    */
    numOfSkyBoxLocations;
        
}