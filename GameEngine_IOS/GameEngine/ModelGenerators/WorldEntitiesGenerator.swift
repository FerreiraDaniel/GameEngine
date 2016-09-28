import Foundation

/**
* Responsible for creating the multiple entities of the 3D world
*/
public class WorldEntitiesGenerator : NSObject {
    
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
        
        let texture : ModelTexture = ModelTexture(Int32(textureId));
        texture.shineDamper = 10.0
        texture.reflectivity = 1.0
        
        //TexturedModel
        let texturedModel : TexturedModel = TexturedModel(rawModel, texture, hasTransparency, normalsPointingUp);
        
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
        let entity : Entity = Entity(texturedEntity , position , rotation , rotation , rotation , scale);
        
        return entity;
    }
    
    /**
    * Get the default values of the entities that are going make the world
    */
    private static func getEntitiesMap() -> Dictionary<DefaultModelGeneratorSwift, Int> {
        
        /* Fern model */
        let fernModel = DefaultModelGeneratorSwift(objectName: "fern", textureName: "fern", scale: 1.0, hasTransparency: true, normalsPointingUp: true);
        
        /* Tree model */
        let treeModel = DefaultModelGeneratorSwift(objectName: "tree", textureName: "tree", scale: 10.0, hasTransparency: false, normalsPointingUp: false);
        
        /*Banana tree*/
        let bananaTreeModel = DefaultModelGeneratorSwift(objectName: "banana_tree", textureName: "banana_tree", scale: 1.0, hasTransparency: true, normalsPointingUp: false);
        
        
        /* grass model */
        let grassModel = DefaultModelGeneratorSwift(objectName: "grass", textureName: "grass", scale: 1.0, hasTransparency: true, normalsPointingUp: true);
        
        /* flower model */
        let flowerModel = DefaultModelGeneratorSwift(objectName: "flower", textureName: "flower", scale: 1.0, hasTransparency: true, normalsPointingUp: true);
        
        
        /* Entity map of all the existing entities */
        let valReturn : Dictionary<DefaultModelGeneratorSwift, Int> = [
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
        let entitiesMap : Dictionary<DefaultModelGeneratorSwift, Int> = WorldEntitiesGenerator.getEntitiesMap();
        
        //Alloccate the entities list
        var entities : Array<Entity> = Array<Entity>();
        for (key, size) in entitiesMap {
            let texturedModel = WorldEntitiesGenerator.getTexturedObj(loader, objName: key.objectName, textureName: key.textureName, hasTransparency: key.hasTransparency, normalsPointingUp: key.normalsPointingUp);
            for(var i  = 0; i < size;i++) {
                let xPosition : Float = 20.0 + Float(rand() % 400);
                let zPosition : Float = Float(rand() % 400)
                let entityPosition : Vector3f = Vector3f(xPosition, 0.0, zPosition);
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
        let lightPosition : Vector3f = Vector3f(10.0 , 100.0 , 10.0)
        let lightColor : Vector3f = Vector3f(1.0 , 1.0 , 1.0)
        
        let light : Light = Light(lightPosition , lightColor);
        return light
    }
}