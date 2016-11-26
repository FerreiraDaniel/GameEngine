import Foundation

/**
 * Represents one texture to be show in the UI of the game (In 2D)
 */
public class GuiTexture {
    
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
    var  position : Vector2f
    
    /**
     * Scale factor of the GUI
     */
    var  scale : Vector2f
    
    /**
     * @param textureId
     *            Identifier of the texture
     * @param position
     *            Position of the texture between (0,0) and (1.0,1.0)
     * @param scale
     *            Scale factor of the texture
     */
    public init(_ rawModel : RawModel, _ textureId : Int, _ position : Vector2f , _ scale :Vector2f ) {
        self.rawModel = rawModel;
        self.textureId = textureId;
        self.position = position;
        self.scale = scale;
    }
}
