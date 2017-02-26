import Foundation

/**
 * Responsible for creating the creating the player(s) of the scene
 */
open class WorldPlayersGenerator {
    /**
     * @return The model with information to generate a player
     */
    fileprivate static func getPlayerModel() -> DefaultModelGenerator {
        /* Player model */
        let playerModel = DefaultModelGenerator(objectType: TEntity.player ,objectName: "player", scale: 1.0, hasTransparency: false, normalsPointingUp: false);
        return playerModel;
    }
    
    /**
     * @param loader loader that will load the entities of the 3D world
     * @return The player that is going to be used in the scene
     */
    open static func getPlayer(_ loader : Loader) -> Player {
        let model : DefaultModelGenerator = getPlayerModel();
        let xPosition : Float = 20.0;
        let zPosition : Float = 0.0;
        let playerPosition : Vector3f = Vector3f(x: xPosition, y: -1.0, z: zPosition);
        //Prepare generic entity begin
        let groupsOfMaterials : Dictionary<String, MaterialGroup> = GenericEntitiesGenerator.getTexturedObj(loader, objName: model.objectName, hasTransparency: model.hasTransparency, normalsPointingUp: model.normalsPointingUp);
        let genericEntity : GenericEntity =  GenericEntity(groupsOfMaterials, model.objectType);
        //Prepare generic entity end
        let player : Player = Player(genericEntity: genericEntity,
                                     position: playerPosition, // Position
            rotX: 0.0, rotY: 0.0, rotZ: 0.0, // Rotation
            scale: model.scale // Scale
        )
        
        return player;
    }
}
