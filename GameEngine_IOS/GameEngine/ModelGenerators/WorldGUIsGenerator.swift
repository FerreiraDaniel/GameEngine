import Foundation

/**
 * Responsible for creating the multiple GUIs to the user interact with 3D world
 */
public class WorldGUIsGenerator {
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
     *
     * @return The textured GUI to render GUI
     */
    private static func  getGUI(loader : Loader, RawModel rawMode : RawModel, textureFileName : String, xPosition : Float,
                                yPosition : Float, scale : Float) -> GuiTexture {
        // Load the texture of the GUI
        let textureId : Int = loader.loadTexture(textureFileName);
        // Create the position of the GUI
        let guiPosition : Vector2f = Vector2f(x: xPosition, y: yPosition);
        // The scale vector
        let guiScale : Vector2f  = Vector2f(x: scale, y: scale);
        // Create the textured GUI
        let guiTexture : GuiTexture = GuiTexture(rawMode, textureId, guiPosition, guiScale);
        
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
        let gameEngineLogo : GuiTexture = getGUI(loader, RawModel: rawModel, textureFileName: "game_engine_logo", xPosition: -0.0, yPosition: 0.8, scale: 0.2);
        
        
        let guis : Array<GuiTexture> = [gameEngineLogo];
        return guis;
    }
}
