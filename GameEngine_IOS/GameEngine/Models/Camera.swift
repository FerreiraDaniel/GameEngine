import Foundation
import GLKit



/**
* Represents the the camera used by the user to see the
* 3D World
*/
public class Camera
{
    private let ANGLE_OF_ROTATION : Float =   0.4;
    private let STEP : Float =  2.0;
    
    
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
        self.yaw = -120;
        self.roll = 0
    }
    
    /**
    * Read the keys pressed by the used and updates the
    * position of the camera
    */
    public func move() {
        let pitchR : Float = GLKMathDegreesToRadians(self.pitch);
        let yawR : Float =  GLKMathDegreesToRadians(self.yaw);
        
        if(GamePad.isKeyDown(GamePadEnum.KEY_UP)) {
            self.position.x += (-STEP * sin(yawR));
            self.position.y += (+STEP * sin(pitchR));
            self.position.z += (-STEP * sin(pitchR)) + (-STEP * cos(yawR));
        }
        
        if(GamePad.isKeyDown(GamePadEnum.KEY_DOWN)) {
            self.position.x += (+STEP * sin(yawR));
            self.position.y += (-STEP * sin(pitchR));
            self.position.z += (+STEP * sin(pitchR)) + (+STEP * cos(yawR));
        }
    }
    
    /**
    * Rotate the camera that the user sees
    */
    public func rotate() {
        /*Looks down*/
        //if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
        //_pitch -= ANGLE_OF_ROTATION;
        //}
        
        /*Looks up*/
        //if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
        //_pitch += ANGLE_OF_ROTATION;
        //}
        
        /*Looks left*/
        if(GamePad.isKeyDown(GamePadEnum.KEY_LEFT)) {
            self.yaw += ANGLE_OF_ROTATION;
        }
        
        /*Looks right*/
        if(GamePad.isKeyDown(GamePadEnum.KEY_RIGHT)) {
            self.yaw -= ANGLE_OF_ROTATION;
        }
    }
}

