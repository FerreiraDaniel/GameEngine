import Foundation
import GLKit

/**
* Responsible for creating the multiple entities of the 3D world
*/
public class WorldEntitiesGenerator : GenericEntitiesGenerator{
    
    private static let NUMBER_OF_TREES : Int = 10;
    private static let NUMBER_OF_BANANA_TREES : Int = 5;
    private static let NUMBER_OF_FERNS : Int = 100;
    private static let NUMBER_OF_GRASS : Int = 10;
    private static let NUMBER_OF_FLOWERS  : Int = 20;
    private static let NUMBER_OF_MARBLES : Int = 10;

    
    
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
        let entity : Entity = Entity(model: texturedEntity ,
                                     position: position ,
                                     rotX: rotation ,
                                     rotY: rotation ,
                                     rotZ: rotation ,
                                     scale: scale);
        
        return entity;
    }
    
    /**
    * Get the default values of the entities that are going make the world
    */
    private static func getEntitiesMap() -> Dictionary<DefaultModelGenerator, Int> {
        
        /* Fern model */
        let fernModel = DefaultModelGenerator(objectName: "fern", textureName: "fern", scale: 3.0, hasTransparency: true, normalsPointingUp: true);
        
        /* Tree model */
        let treeModel = DefaultModelGenerator(objectName: "tree", textureName: "tree", scale: 30.0, hasTransparency: false, normalsPointingUp: false);
        
        /*Banana tree*/
        let bananaTreeModel = DefaultModelGenerator(objectName: "banana_tree", textureName: "banana_tree", scale: 3.0, hasTransparency: true, normalsPointingUp: false);
        
        
        /* grass model */
        let grassModel = DefaultModelGenerator(objectName: "grass", textureName: "grass", scale: 3.0, hasTransparency: true, normalsPointingUp: true);
        
        /* flower model */
        let flowerModel = DefaultModelGenerator(objectName: "flower", textureName: "flower", scale: 3.0, hasTransparency: true, normalsPointingUp: true);
        
        /*Marble model*/
        let marbleModel = DefaultModelGenerator(objectName: "marble", textureName: "marble", scale: 5.0, hasTransparency: false, normalsPointingUp: false);


        /* Entity map of all the existing entities */
        let valReturn : Dictionary<DefaultModelGenerator, Int> = [
            bananaTreeModel : WorldEntitiesGenerator.NUMBER_OF_BANANA_TREES,
            fernModel : WorldEntitiesGenerator.NUMBER_OF_FERNS,
            treeModel : WorldEntitiesGenerator.NUMBER_OF_TREES,
            grassModel : WorldEntitiesGenerator.NUMBER_OF_GRASS,
            flowerModel : WorldEntitiesGenerator.NUMBER_OF_FLOWERS,
            marbleModel : WorldEntitiesGenerator.NUMBER_OF_MARBLES
        ];
        
        return valReturn;
    }
    
    /*
     * @param loader  loader that will load the entities of the 3D world
     * @param terrain The terrain used to determine the height position
    *
    * @return The entities that will compose the 3D world
    */
    public static func getEntities(loader : Loader, terrain : Terrain) -> Array<Entity> {
        let entitiesMap : Dictionary<DefaultModelGenerator, Int> = WorldEntitiesGenerator.getEntitiesMap();
        
        //Alloccate the entities list
        var entities : Array<Entity> = Array<Entity>();
        for (key, numberOfObjs) in entitiesMap {
            let texturedModel = GenericEntitiesGenerator.getTexturedObj(loader, objName: key.objectName, textureName: key.textureName, hasTransparency: key.hasTransparency, normalsPointingUp: key.normalsPointingUp);
            for _ in 0 ..< numberOfObjs {
                let xPosition : Float = 20.0 + Float(arc4random_uniform(400));
                let zPosition : Float = Float(arc4random_uniform(400))
                let yPosition : Float = terrain.getHeightOfTerrain(xPosition, worldZ: zPosition);
                
                let entityPosition : Vector3f = Vector3f(x: xPosition, y: yPosition, z: zPosition);
                let entity = getEntity(texturedModel, position: entityPosition);
                entity.scale = Float(arc4random_uniform(UInt32(key.scale)));
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
}
