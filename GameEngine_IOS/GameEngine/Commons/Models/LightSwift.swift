import Foundation

/**
* Represents one light source in the scene
*/
public class LightSwift : NSObject {
    
    /**
    * Position where the light will exist
    */
    var position : Vector3fSwift;
    
    /**
    * The intensity of the light in the different components
    */
    var color : Vector3fSwift;
    
    /**
    * The constructor of the light entity
    *
    * @param aPosition
    *            Position where the light will exist
    * @param aColor
    *            The intensity of the light in the different components
    */
    public init(position : Vector3fSwift, color : Vector3fSwift) {
        self.position = position
        self.color = color
    }
    
}
