import Foundation

/**
 * Player that is going to be used in the scene
 */
public class Player : Entity {
    
    /*Distance that the user is going to run in one second*/
    private let RUN_SPEED : Float = 20.0;
    /*Angle that the user will turn in one second*/
    private let TURN_SPEED : Float = 160.0;
    /*The gravity that is going to push the player back to the ground*/
    private let GRAVITY : Float = -0.5;
    /*Power that the player is going to be push up when is jumping*/
    private let JUMP_POWER : Float = 0.3;
    
    /*The current speed of the player*/
    private var currentSpeed : Float;
    
    /*The current speed turning*/
    private var currentTurnSpeed : Float;
    
    /*The speed which the player is going up*/
    private var upwardsSpeed : Float;
    
    /*Indicates if is jumping or not*/
    private var isJumping : Bool
    
    /**
     * Initiator of the player to be render in the 3D world
     *
     * @param model    Textured model
     * @param position position where the model should be render
     * @param rotX     Rotation of the model in the X axle
     * @param rotY     Rotation of the model in the Y axle
     * @param rotZ     Rotation of the model in the Z axle
     * @param scale    Scale of the model
     */
    public override init(genericEntity : GenericEntity, position : Vector3f, rotX : Float, rotY : Float, rotZ : Float, scale : Float) {
        self.currentSpeed = 0.0
        self.currentTurnSpeed = 0.0
        self.upwardsSpeed = 0.0
        self.isJumping = false;
        super.init(genericEntity: genericEntity, position: position, rotX: rotX, rotY: rotY, rotZ: rotZ, scale: scale);
    }
    
    /**
     * Check the input of the user in order to update the current values of the movement
     * @param terrain Reference to the terrain
     */
    private func checkInputs(terrain : Terrain) {
        if (GamePad.isKeyDown(GamePadKey.circle)) {
            //Go in front
            self.currentSpeed = -RUN_SPEED;
        } else if (GamePad.isKeyDown(GamePadKey.x)) {
            //Go backwards
            self.currentSpeed = RUN_SPEED;
        } else {
            //Stay where it is
            self.currentSpeed = 0;
        }
        if(GamePad.isKeyDown(GamePadKey.left)) {
            //Rotate counterclockwise
            self.currentTurnSpeed = -TURN_SPEED;
        } else if (GamePad.isKeyDown(GamePadKey.right)) {
            //Rotate clockwise
            self.currentTurnSpeed = TURN_SPEED;
        } else {
            //Do not rotate nothing
            self.currentTurnSpeed = 0;
        }
        
        /*if (GamePad.isKeyDown(GamePad.KEY_TRIANGLE)) {
         self.jump(terrain);
         }*/
    }
    
    /**
     * Move the player and rotate it around the scene
     *
     * @param timeToRender The time that took to render the last frame in seconds like that the movement of the player is
     *                     frame rate independent
     */
    private func moveAndRotate(timeToRender : Float) {
        let turnByFrame : Float = currentTurnSpeed * timeToRender;
        self.increaseRotation(0.0, dy: turnByFrame, dz: 0.0);
        let distance : Float = currentSpeed * timeToRender;
        let angleRadians : Float = super.rotY * Float(M_PI / 180.0)
        let dx : Float = -(distance * sinf(angleRadians))
        let dz : Float = (distance * cosf(angleRadians))
        super.increasePosition(dx, dy: 0.0, dz: dz);
    }
    
    /**
     * When the player is above the terrain height make it to fall down
     *
     * @param timeToRender The time that took to render the last frame in seconds like that the movement of the player is
     *                     frame rate independent
     * @param terrain      Reference to the terrain to compute where is going to fall
     */
    private func fallDown(timeToRender : Float, terrain : Terrain) {
        let terrainHeight = terrain.getHeightOfTerrain(position.x, worldZ: position.z);
        
        
        if (((position.y > terrainHeight) || (upwardsSpeed > 0)) && isJumping) {
            upwardsSpeed += GRAVITY * timeToRender;
            super.increasePosition(0.0, dy: upwardsSpeed, dz: 0.0);
        } else {
            super.position.y = terrainHeight;
        }
        
        //Ends the jump
        if(isJumping) && (position.y <= terrainHeight) {
            isJumping = false
        }
    }
    
    /**
     * Set the upward speed of the player in order make it jump
     * @param terrain Reference to the terrain in order to compute the height that is going to jump
     */
    private func jump(terrain : Terrain) {
        let terrainHeight = terrain.getHeightOfTerrain(position.x, worldZ: position.z);
        if (position.y <= terrainHeight) {
            upwardsSpeed = terrainHeight + JUMP_POWER;
            isJumping = true
        }
    }
    
    /**
     * Move the player due the the keys that are pressed in the keyBoard
     *
     * @param timeToRender The time that took to render the last frame in seconds like that the movement of the player is
     *                     frame rate independent
     * @param terrain	Terrain used to determine the height where the player is going to fall/stay
     */
    public func move(timeToRender : Float, terrain : Terrain) {
        checkInputs(terrain);
        moveAndRotate(timeToRender);
        fallDown(timeToRender, terrain: terrain);
    }
    
    
    /**
     * @return the current speed of the player
     */
    public func getCurrentSpeed() -> Float {
        return self.currentSpeed;
    }
}
