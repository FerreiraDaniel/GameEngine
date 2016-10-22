import Foundation

/**
* Represents a third person camera used by the user to see the 3D World
*/
public class ThirdPersonCamera : Camera {
    
    /**
    * Distance between camera and the player controlled by the user
    */
    private var distanceFromPlayer : Float;
    
    /**
    * Angle around the player
    */
    private var angleAroundPlayer : Float;
    
    /**
    * Initializer of the camera of the scene
    */
    public override init() {
        self.distanceFromPlayer = 15.0;
        self.angleAroundPlayer = 0.0;
        super.init();
    }
    
    /**
    * Uses the position of the player to update the position of the camera
    *
    * @param player Reference to that the camera is going to follow
    */
    public func update(Player player: Player) {
        let horizontalDistance = getHorizontalDistance();
        let verticalDistance = getVerticalDistance();
        self.calculateCameraPosition(horizontalDistance, verticalDistance: verticalDistance, player: player);
        self.yaw = (180 - player.rotY + angleAroundPlayer);
    }
    
    /**
    * Compute the position where the camera should be to follow the player
    *
    * @param horizontalDistance Horizontal distance
    * @param verticalDistance   Vertical distance
    * @param player             Player of the scene
    */
    private func calculateCameraPosition(horizontalDistance: Float, verticalDistance : Float, player : Player) {
        let theta = player.rotY + angleAroundPlayer;
        let rTheta = Math.toRadians(theta);
        
        let offsetX : Float = (horizontalDistance * sin(rTheta));
        let offsetZ : Float = (horizontalDistance * cos(rTheta));
        position.x = player.position.x + offsetX;
        position.z = player.position.z - offsetZ;
        position.y = (player.position.y + 10.0) + verticalDistance;
    }
    
    
    /**
    * @return Horizontal distance from the camera to the player
    */
    private func getHorizontalDistance() -> Float {
        return (distanceFromPlayer * Math.cos(Math.toRadians(self.pitch)));
    }
    
    /**
    * @return Vertical distance from the camera to the player
    */
    private func getVerticalDistance() -> Float {
        return (distanceFromPlayer * Math.sin(Math.toRadians(self.pitch)));
    }
}
