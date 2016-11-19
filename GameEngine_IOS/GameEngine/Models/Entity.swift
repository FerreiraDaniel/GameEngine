import Foundation

/**
 * Represents one entity in the 3D world
 */
public class Entity  {
    
    
    /*3D model to be render*/
    var model : TexturedModel
    
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
     * 2D vector that is going to determine where the texture of the entity is
     * going to start
     */
    private var textureOffset : Vector2f;
    
    /**
     * Initiator of the entity to be render in the 3D world
     *
     * @param model          Textured model
     * @param position       Position where the model should be render
     * @param rotX           Rotation of the model in the X axle
     * @param rotY           Rotation of the model in the Y axle
     * @param rotZ           Rotation of the model in the Z axle
     * @param scale          Scale of the model
     * @param textureIndex   Indicates which of the textures is going to use
     */
    public init(model: TexturedModel, position : Vector3f, rotX : Float, rotY : Float, rotZ : Float, scale : Float, textureIndex: Int) {
        self.model = model;
        self.position = position;
        self.rotX = rotX;
        self.rotY = rotY;
        self.rotZ = rotZ;
        self.scale = scale;
        self.textureOffset = Entity.computeTextureOffset(textureIndex, atlasFactor: self.model.texture.atlasFactor);
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
    
    /**
     * @param textureIndex
     *            the index of the texture to set
     */
    public func setTextureIndex(textureIndex : Int) {
        self.textureOffset = Entity.computeTextureOffset(textureIndex, atlasFactor: self.model.texture.atlasFactor);
    }
    
    /**
     * @return A number between 0 and 1 that is going indicate where the texture
     *         will have have there x-axle start based upon the texture atlas
     *         factor
     */
    private static func computeTextureXOffset(textureIndex : Int, atlasFactor : Int) -> Float {
        if (textureIndex == 0) {
            return 0.0;
        } else {
            let atlasFactor :Int = atlasFactor;
            let column = textureIndex % (atlasFactor);
            // Return the value in percentage of the total
            return Float(column) / (Float(atlasFactor));
        }
    }
    
    /**
     * @return A number between 0 and 1 that is going indicate where the texture
     *         will have have there y-axle start based upon the texture atlas
     *         factor
     */
    private static func computeTextureYOffset(textureIndex : Int, atlasFactor : Int) -> Float {
        if (textureIndex == 0) {
            return 0.0;
        } else {
            let atlasFactor :Int = atlasFactor;
            let row = textureIndex / (atlasFactor);
            // Return the value in percentage of the total
            return Float(row) / (Float(atlasFactor));
        }
    }
    
    /**
     *
     * @return A vector with coordinates where the texture starts
     */
    private static func computeTextureOffset(textureIndex : Int, atlasFactor : Int) -> Vector2f {
        return Vector2f(x: computeTextureXOffset(textureIndex, atlasFactor: atlasFactor), y: computeTextureYOffset(textureIndex, atlasFactor: atlasFactor));
    }
    
    /**
     * @return the offset of the texture of the entity
     */
    public func getTextureOffset() -> Vector2f {
        return self.textureOffset;
    }
}
