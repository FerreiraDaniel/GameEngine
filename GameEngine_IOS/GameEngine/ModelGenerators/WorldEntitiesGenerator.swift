import Foundation
import GLKit

/**
 * Responsible for creating the multiple entities of the 3D world
 */
open class WorldEntitiesGenerator : GenericEntitiesGenerator{
    
    fileprivate static let NUMBER_OF_TREES : Int = 10;
    fileprivate static let NUMBER_OF_BANANA_TREES : Int = 5;
    fileprivate static let NUMBER_OF_FERNS : Int = 100;
    fileprivate static let NUMBER_OF_GRASS : Int = 10;
    fileprivate static let NUMBER_OF_FLOWERS  : Int = 20;
    fileprivate static let NUMBER_OF_MARBLES : Int = 10;
    
    
    
    /**
     * Get one entity in a certain position
     *
     * @param genericEntity
     *            Generic entity
     * @param position
     *            Position where is to put the entity in the 3D world
     *
     * @return the entity to render
     */
    fileprivate static func getEntity(_ genericEntity : GenericEntity,  position : Vector3f) -> Entity {
        let rotation : Float = 0;
        let scale : Float = 1.0;
        let entity : Entity = Entity(genericEntity: genericEntity ,
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
    fileprivate static func getEntitiesMap() -> Dictionary<DefaultModelGenerator, Int> {
        
        /* Fern model */
        let fernModel = DefaultModelGenerator(objectType: TEntity.fern, objectName: "fern", scale: 3.0, hasTransparency: true, normalsPointingUp: true);
        
        /* Tree model */
        let treeModel = DefaultModelGenerator(objectType: TEntity.tree, objectName: "tree", scale: 30.0, hasTransparency: false, normalsPointingUp: false);
        
        /*Banana tree*/
        let bananaTreeModel = DefaultModelGenerator(objectType: TEntity.banana_tree, objectName: "banana_tree", scale: 3.0, hasTransparency: true, normalsPointingUp: false);
        
        
        /* grass model */
        let grassModel = DefaultModelGenerator(objectType: TEntity.grass, objectName: "grass", scale: 3.0, hasTransparency: true, normalsPointingUp: true);
        
        /* flower model */
        let flowerModel = DefaultModelGenerator(objectType: TEntity.flower, objectName: "flower", scale: 3.0, hasTransparency: true, normalsPointingUp: true);
        
        /*Marble model*/
        let marbleModel = DefaultModelGenerator(objectType: TEntity.marble, objectName: "marble", scale: 5.0, hasTransparency: false, normalsPointingUp: false);
        
        
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
    open static func getEntities(_ loader : Loader, terrain : Terrain) -> Array<Entity> {
        let entitiesMap : Dictionary<DefaultModelGenerator, Int> = WorldEntitiesGenerator.getEntitiesMap();
        
        //Alloccate the entities list
        var entities : Array<Entity> = Array<Entity>();
        for (key, numberOfObjs) in entitiesMap {
            let groupsOfMaterials = GenericEntitiesGenerator.getTexturedObj(loader, objName: key.objectName, hasTransparency: key.hasTransparency, normalsPointingUp: key.normalsPointingUp);
            //Prepare generic entity begin
            let genericEntity : GenericEntity =  GenericEntity(groupsOfMaterials, key.objectType);
            //Prepare generic entity end
            for _ in 0 ..< numberOfObjs {
                let xPosition : Float = 20.0 + Float(arc4random_uniform(400));
                let zPosition : Float = Float(arc4random_uniform(400))
                let yPosition : Float = terrain.getHeightOfTerrain(xPosition, worldZ: zPosition);
                
                let entityPosition : Vector3f = Vector3f(x: xPosition, y: yPosition, z: zPosition);
                let entity = getEntity(genericEntity, position: entityPosition);
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
    open static func getLight() -> Light {
        let lightPosition : Vector3f = Vector3f(x: 10.0 , y: 100.0 , z: 10.0)
        let lightColor : ColorRGB = ColorRGB(r: 1.0 , g: 1.0 , b: 1.0)
        
        let light : Light = Light(position: lightPosition , color: lightColor);
        return light
    }
}
