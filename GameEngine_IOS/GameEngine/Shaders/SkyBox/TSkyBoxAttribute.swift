import Foundation

/**
* Enumeration of attributes to the skyBox shader
*/
public enum TSkyBoxAttribute : Int {
    case
    
    /**
    * Position where to location attribute is going to be bind in the shader
    * program
    */
    position = 0,
    
    /*
     * Number of attributes in the skybox shader
     */
    numberOfAttributes;
}