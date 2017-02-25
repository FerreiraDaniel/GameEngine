import Foundation

/**
 * Represents one texture to be show in the UI of the game (In 2D)
 */
open class GuiTexture {
    
    /**
     * Raw model of the entity
     */
    var rawModel : RawModel;
    
    /**
     * Identifier of the texture
     */
    var textureId : Int
    
    /**
     * Position of the texture between (0,0) and (1.0,1.0)
     */
    var position : Vector2f
    
    /**
     * Scale factor of the GUI
     */
    var scale : Vector2f
    
    /**
     * The game pad key that the gui texture will trigger if pressed
     * Note if null nothing will be trigger
     */
    var gamePadKey : GamePadKey!;
    
    /**
     *
     * @param rawModel   The rawModel that will be used by the guiTexture
     * @param textureId  Identifier of the texture
     * @param position   Position of the texture between (0,0) and (1.0,1.0)
     * @param scale      Scale factor of the texture
     * @param gamePadKey The reference to the key that will be trigger
     */
    public init(_ rawModel : RawModel, _ textureId : Int, _ position : Vector2f , _ scale :Vector2f, _ gamePadKey : GamePadKey!) {
        self.rawModel = rawModel;
        self.textureId = textureId;
        self.position = position;
        self.scale = scale;
        self.gamePadKey = gamePadKey;
    }
    
    /**
     * @param locationX x-coordinate of the location passed
     * @param locationY y-coordinate of the location passed
     *
     * @return  False do not contains the location passed
     *          True contains the location passed
     */
    open func containsLocation(_ locationX : Float, _ locationY : Float) -> Bool {
        return (locationX >= (position.x - scale.x)) &&
            (locationX <= (position.x + scale.x)) &&
            ((locationY >= (position.y - scale.y)) && ((locationY <= (position.y + scale.y))));
    }
}
