import Foundation
import GLKit

/**
* Responsible for creating the multiple entities of the 3D world
*/
public class WorldEntitiesGenerator {
    
    private static let NUMBER_OF_TREES : Int = 10;
    private static let NUMBER_OF_BANANA_TREES : Int = 10;
    private static let NUMBER_OF_FERNS : Int = 10;
    private static let NUMBER_OF_GRASS : Int = 5;
    private static let NUMBER_OF_FLOWERS  : Int = 10;
    
    /**
    * Load a textured model
    *
    * @param loader
    *            the loader of the texture
    *
    * @return the textured model of the dragon
    */
    private static func getTexturedObj(loader : Loader, objName : String, textureName : String, hasTransparency : Bool, normalsPointingUp : Bool) -> TexturedModel  {
        let shape : IShape = OBJLoader.loadObjModel(objName);
        
        let rawModel : RawModel = loader.loadToVAO(shape);
        
        let textureId : Int = loader.loadTexture(textureName)
        
        let texture : ModelTexture = ModelTexture(textureId);
        texture.shineDamper = 10.0
        texture.reflectivity = 1.0
        
        //TexturedModel
        let texturedModel : TexturedModel = TexturedModel(rawModel: rawModel, texture: texture, hasTransparency: hasTransparency, normalsPointingUp: normalsPointingUp);
        
        return texturedModel;
        
        
    }
    
    /**
    * Get one entity in a certain position
    *
    * @param texturedEntity
    *            Model of the entity to render
    * @param position
    *            Position where is to put the entity in the 3D world
    *
    * @return the entity to render
    */
    private static func getEntity(texturedEntity : TexturedModel,  position : Vector3f) -> Entity {
        let rotation : Float = 0;
        let scale : Float = 1.0;
        let entity : Entity = Entity(model: texturedEntity , position: position , rotX: rotation , rotY: rotation , rotZ: rotation , scale: scale);
        
        return entity;
    }
    
    /**
    * Get the default values of the entities that are going make the world
    */
    private static func getEntitiesMap() -> Dictionary<DefaultModelGenerator, Int> {
        
        /* Fern model */
        let fernModel = DefaultModelGenerator(objectName: "fern", textureName: "fern", scale: 1.0, hasTransparency: true, normalsPointingUp: true);
        
        /* Tree model */
        let treeModel = DefaultModelGenerator(objectName: "tree", textureName: "tree", scale: 10.0, hasTransparency: false, normalsPointingUp: false);
        
        /*Banana tree*/
        let bananaTreeModel = DefaultModelGenerator(objectName: "banana_tree", textureName: "banana_tree", scale: 1.0, hasTransparency: true, normalsPointingUp: false);
        
        
        /* grass model */
        let grassModel = DefaultModelGenerator(objectName: "grass", textureName: "grass", scale: 1.0, hasTransparency: true, normalsPointingUp: true);
        
        /* flower model */
        let flowerModel = DefaultModelGenerator(objectName: "flower", textureName: "flower", scale: 1.0, hasTransparency: true, normalsPointingUp: true);
        
        
        /* Entity map of all the existing entities */
        let valReturn : Dictionary<DefaultModelGenerator, Int> = [
            bananaTreeModel : WorldEntitiesGenerator.NUMBER_OF_BANANA_TREES,
            fernModel : WorldEntitiesGenerator.NUMBER_OF_FERNS,
            treeModel : WorldEntitiesGenerator.NUMBER_OF_TREES,
            grassModel : WorldEntitiesGenerator.NUMBER_OF_GRASS,
            flowerModel : WorldEntitiesGenerator.NUMBER_OF_FLOWERS
        ];
        
        return valReturn;
    }
    
    /*
    * @param loader
    *            loader that will load the entities of the 3D world
    *
    * @return The entities that will compose the 3D world
    */
    public static func getEntities(loader : Loader) -> Array<Entity> {
        let entitiesMap : Dictionary<DefaultModelGenerator, Int> = WorldEntitiesGenerator.getEntitiesMap();
        
        //Alloccate the entities list
        var entities : Array<Entity> = Array<Entity>();
        for (key, size) in entitiesMap {
            let texturedModel = WorldEntitiesGenerator.getTexturedObj(loader, objName: key.objectName, textureName: key.textureName, hasTransparency: key.hasTransparency, normalsPointingUp: key.normalsPointingUp);
            for(var i  = 0; i < size;i++) {
                let xPosition : Float = 20.0 + Float(rand() % 400);
                let zPosition : Float = Float(rand() % 400)
                let entityPosition : Vector3f = Vector3f(x: xPosition, y: 0.0, z: zPosition);
                let entity = getEntity(texturedModel, position: entityPosition);
                entity.scale = key.scale
                entities.append(entity);
            }
        }
        
        return entities;
    }
    
    /**
    *
    * @return A source of light to the scene
    */
    public static func getLight() -> Light {
        let lightPosition : Vector3f = Vector3f(x: 10.0 , y: 100.0 , z: 10.0)
        let lightColor : Vector3f = Vector3f(x: 1.0 , y: 1.0 , z: 1.0)
        
        let light : Light = Light(position: lightPosition , color: lightColor);
        return light
    }
    
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
        let texturedObj : TexturedModel = getTexturedObj(loader, objName: model.objectName, textureName: model.textureName, hasTransparency: model.hasTransparency, normalsPointingUp: model.normalsPointingUp);
        let xPosition : Float = 20.0;
        let zPosition : Float = 0.0;
        let playerPosition : Vector3f = Vector3f(x: xPosition, y: -1.0, z: zPosition);
        let player : Player = Player(model: texturedObj,
            position: playerPosition, // Position
            rotX: 0.0, rotY: 0.0, rotZ: 0.0, // Rotation
            scale: model.scale // Scale
        )
        
        return player;
    }
}