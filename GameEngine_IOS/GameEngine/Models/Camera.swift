import Foundation
import GLKit



/**
* Represents the the camera used by the user to see the
* 3D World
*/
open class Camera
{

    
    
    /**
    * The position where the camera is
    */
    var position : Vector3f;
    
    /**
    * Pitch is up and down (rotation around X-axis)
    */
    var pitch : Float;
    
    /**
    * Yaw is the angle when moving the head left and right (rotation around Y-axis)
    */
    var yaw : Float;
    
    /**
    * Roll, which we usually don't experience is when you tilt your head (rotation around Z-axis)
    */
    var   roll : Float;
    
    /**
    Initiator of camera
    *
    */
    public init() {
        self.position = Vector3f(x: 0.0, y: 1.0, z: 0.0);
        self.pitch = 0;
        self.yaw = -90;
        self.roll = 0
    }
}

