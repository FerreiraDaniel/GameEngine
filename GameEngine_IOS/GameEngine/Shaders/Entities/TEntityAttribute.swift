import Foundation

/**
* Enumeration of attributes to the entity shader
*/
public enum TEntityAttribute : Int {
    case
    
    /**
    * Position where to location attribute is going to be bind in the shader
    * program
    */
    position = 0,
    
    /**
    * Position where to texture attribute is going to be bind in the program
    * shader
    */
    textureCoords,
    
    /**
    * Position where the normal vector are going to be bind in the program
    * shader
    */
    normal,
    
    /**
    * Id of attribute the position where the light of scene is
    */
    lightPosition,
    
    /**
    * Id of attribute the color where the light of scene have
    */
    lightColor,
    
    /**
    * Number of entity attributes
    */
    numOfAttributes;
}
