import Foundation

/**
 * Responsible for creating the multiple GUIs to the user interact with 3D world
 */
public class WorldGUIsGenerator {
    
    /*Zoom that is going apply in the buttons of the gam*/
    private static let BUTTONS_ZOOM : Float = 0.07;
    
    /*Position of the buttons that are upper in the Y*/
    private static let UPPER_BUTTONS : Float = -0.7;
    
    /*Position of the buttons that are in the middle in the Y*/
    private static let MIDDLE_BUTTONS : Float = -0.8;
    
    /*Position of the buttons that are upper in the Y*/
    private static let BOTTOM_BUTTONS : Float = -0.9;
    
    /**
     *
     * @param loader
     *            The loader to load the texture of the GUI
     * @param rawMode
     *            Shape of the GUI
     * @param textureFileName
     *            The name of the file where the texture exists
     * @param xPosition
     *            xPosition of the GUI
     * @param yPosition
     *            yPosition of the GUI
     * @param scale
     *            The scale of the GUI
     * @param key               The key that GUI match if any
     *
     * @return The textured GUI to render GUI
     */
    private static func  getGUI(loader : Loader, _ rawMode : RawModel, _ textureFileName : String, _ xPosition : Float,
                                _ yPosition : Float, _ scale : Float, _ key :GamePadKey!) -> GuiTexture {
        // Load the texture of the GUI
        let textureId : Int = loader.loadTexture(textureFileName);
        // Create the position of the GUI
        let guiPosition : Vector2f = Vector2f(x: xPosition, y: yPosition);
        // The scale vector
        let guiScale : Vector2f  = Vector2f(x: scale, y: scale);
        // Create the textured GUI
        let guiTexture : GuiTexture = GuiTexture(rawMode, textureId, guiPosition, guiScale, key);
        
        return guiTexture;
    }
    
    /**
     * The GUIs of the scene
     *
     * @param loader
     *            The loader in charge of loading the textures of the terrains
     *
     * @return list of terrains of the scene
     */
    public static func getGUIs(loader : Loader) -> Array<GuiTexture> {
        let guiShape : GuiShape = GuiShape();
        let rawModel : RawModel = loader.load2DPositionsToVAO(guiShape.getVertices(),positionsLength: Int(guiShape.countVertices()));
        
        let leftButton = getGUI(loader, rawModel, "ic_pad_left", -0.8, MIDDLE_BUTTONS, BUTTONS_ZOOM, GamePadKey.left);
        let upButton = getGUI(loader, rawModel, "ic_pad_up", -0.7, UPPER_BUTTONS, BUTTONS_ZOOM, GamePadKey.up);
        let downButton = getGUI(loader, rawModel, "ic_pad_down", -0.7, BOTTOM_BUTTONS, BUTTONS_ZOOM, GamePadKey.down);
        let rightButton = getGUI(loader, rawModel, "ic_pad_right", -0.6, MIDDLE_BUTTONS, BUTTONS_ZOOM, GamePadKey.right);
        let squareButton = getGUI(loader, rawModel, "ic_pad_left", 0.6, MIDDLE_BUTTONS, BUTTONS_ZOOM, GamePadKey.square);
        let xButton = getGUI(loader, rawModel, "ic_pad_down", 0.7, BOTTOM_BUTTONS, BUTTONS_ZOOM, GamePadKey.x);
        let triangleButton = getGUI(loader, rawModel, "ic_pad_up", 0.7, UPPER_BUTTONS, BUTTONS_ZOOM, GamePadKey.triangle);
        let circleButton = getGUI(loader, rawModel, "ic_pad_right", 0.8, MIDDLE_BUTTONS, BUTTONS_ZOOM, GamePadKey.circle);

        
        let guis : Array<GuiTexture> = [leftButton,upButton,downButton, rightButton, squareButton, xButton, triangleButton, circleButton];
        return guis;
    }
}
