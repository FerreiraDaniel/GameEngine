import Foundation

/**
 * Represents one entity in the 3D world
 */
public class Entity  {
    
    /**
     * Keys: Have the name of the group The name of the material group for
     * instance harm
     */
    var genericEntity : GenericEntity;
    
    /*Position where the entity is*/
    var  position : Vector3f
    
    /*Rotation (X-axle) of the 3D model */
    var  rotX : Float;
    
    /*Rotation (Y-axle) of the 3D model */
    var  rotY : Float
    /*Rotation (Z-axle) of the 3D model */
    var rotZ : Float
    
    /*Scale of the model*/
    var scale : Float
    
    
    /**
     * Initiator of the entity to be render in the 3D world
     *
     * @param genericEntity  Reference to a generic entity with the description of the model
     * @param position       Position where the model should be render
     * @param rotX           Rotation of the model in the X axle
     * @param rotY           Rotation of the model in the Y axle
     * @param rotZ           Rotation of the model in the Z axle
     * @param scale          Scale of the model
     */
    public init(genericEntity : GenericEntity, position : Vector3f, rotX : Float, rotY : Float, rotZ : Float, scale : Float) {
        self.genericEntity = genericEntity;
        self.position = position;
        self.rotX = rotX;
        self.rotY = rotY;
        self.rotZ = rotZ;
        self.scale = scale;
    }
    
    
    /**
     * Increases the position of the model using for that the specified components
     *
     * @param dx	X component to be increase
     * @param dy	Y component to be increase
     * @param dz	Z component to be increase
     */
    public func increasePosition(dx : Float, dy : Float, dz : Float) {
        self.position.x += dx;
        self.position.y += dy;
        self.position.z += dz;
    }
    
    /**
     * Increases the rotation of the model using for that the specified components
     * @param dx	X component to be increase
     * @param dy	Y component to be increase
     * @param dz	Z component to be increase
     */
    public func increaseRotation(dx : Float, dy : Float, dz : Float) {
        self.rotX += dx;
        self.rotY += dy;
        self.rotZ += dz;
    }
}
