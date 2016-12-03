import Foundation

/**
 * Responsible for creating the creating the player(s) of the scene
 */
public class WorldPlayersGenerator {
    /**
     * @return The model with information to generate a player
     */
    private static func getPlayerModel() -> DefaultModelGenerator {
        /* Player model */
        let playerModel = DefaultModelGenerator(objectName: "player", textureName: "player", scale: 0.5, hasTransparency: false, normalsPointingUp: false);
        return playerModel;
    }
    
    /**
     * @param loader loader that will load the entities of the 3D world
     * @return The player that is going to be used in the scene
     */
    public static func getPlayer(loader : Loader) -> Player {
        let model : DefaultModelGenerator = getPlayerModel();
        let texturedObj : RawModelMaterial = GenericEntitiesGenerator.getTexturedObj(loader, objName: model.objectName, textureName: model.textureName, hasTransparency: model.hasTransparency, normalsPointingUp: model.normalsPointingUp);
        let xPosition : Float = 20.0;
        let zPosition : Float = 0.0;
        let playerPosition : Vector3f = Vector3f(x: xPosition, y: -1.0, z: zPosition);
        let genericEntity : GenericEntity = GenericEntity(texturedObj);
        let player : Player = Player(genericEntity: genericEntity,
                                     position: playerPosition, // Position
            rotX: 0.0, rotY: 0.0, rotZ: 0.0, // Rotation
            scale: model.scale // Scale
        )
        
        return player;
    }
}
