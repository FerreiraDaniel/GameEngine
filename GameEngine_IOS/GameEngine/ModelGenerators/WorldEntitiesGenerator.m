
#import "WorldEntitiesGenerator.h"

/**
 * Responsible for creating the multiple entities of the 3D world
 */
@implementation WorldEntitiesGenerator

const int NUMBER_OF_TREES = 10;
const int NUMBER_OF_BANANA_TREES = 10;
const int NUMBER_OF_FERNS = 10;
const int NUMBER_OF_GRASS = 5;
const int NUMBER_OF_FLOWERS = 10;

/**
 *
 * @return A source of light to the scene
 */
+ (Light*) getLight {
    Vector3f *lightPosition = [[Vector3f alloc] init : 10.0f : 100.0f : 10.0f];
    Vector3f *lightColor = [[Vector3f alloc] init: 1.0f : 1.0f : 1.0f];
    
    return [[Light alloc] init: lightPosition : lightColor];
}

/**
 * Load a textured model
 *
 * @param loader
 *            the loader of the texture
 *
 * @return the textured model of the dragon
 */
+ (TexturedModel*) getTexturedObj : (Loader*) loader : (NSString*) objName : (NSString*) textureName : (Boolean) hasTransparency : (Boolean) normalsPointingUp {
    id<IShape> shape = [OBJLoader loadObjModel : objName];
    id<IShape> shape2 = [OBJLoaderSwift loadObjModel:objName];

    
    RawModel* rawModel = [loader loadToVAO: shape];
    
    int textureId = [loader loadTexture : textureName];
    ModelTexture *texture = [[ModelTexture alloc] init: textureId];
    [texture setShineDamper: 10.0f];
    [texture setReflectivity : 1.0f];
    
    //TexturedModel
    TexturedModel* texturedModel = [[TexturedModel alloc] init: rawModel : texture : hasTransparency : normalsPointingUp];
    
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
+ (Entity*) getEntity : (TexturedModel*) texturedEntity : (Vector3f*) position {
    float rotation = 0;
    float scale = 1.0f;
    Entity *entity = [[Entity alloc] init: texturedEntity : position : rotation : rotation : rotation : scale];
    return entity;
}

/**
 * Get the default values of the entities that are going make the world
 */
+ (NSDictionary*) getEntitiesMap {
    
    /* Fern model */
    DefaultModelGenerator *fernModel = [[DefaultModelGenerator alloc] init];
    [fernModel setObjectName: @"fern"];
    [fernModel setTextureName : @"fern"];
    [fernModel setScale : 1.0f];
    [fernModel setHasTransparency : true];
    [fernModel setNormalsPointingUp : true];
    
    /* Tree model */
    DefaultModelGenerator *treeModel = [[DefaultModelGenerator alloc] init];
    [treeModel setObjectName : @"tree"];
    [treeModel setTextureName : @"tree"];
    [treeModel setScale : 10.0f];
    [treeModel setHasTransparency : false];
    [treeModel setNormalsPointingUp : false];
    
    /*Banana tree*/
    DefaultModelGenerator *bananaTreeModel = [[DefaultModelGenerator alloc] init];
    [bananaTreeModel setObjectName : @"banana_tree"];
    [bananaTreeModel setTextureName :@"banana_tree"];
    [bananaTreeModel setScale : 1.0f];
    [bananaTreeModel setHasTransparency : true];
    [bananaTreeModel setNormalsPointingUp :false];
    
    /* grass model */
    DefaultModelGenerator *grassModel = [[DefaultModelGenerator alloc] init];
    [grassModel setObjectName : @"grass" ];
    [grassModel setTextureName : @"grass"];
    [grassModel setScale :1.0f];
    [grassModel setHasTransparency : true];
    [grassModel setNormalsPointingUp :true];
    
    /* flower model */
    DefaultModelGenerator *flowerModel = [[DefaultModelGenerator alloc] init];
    [flowerModel setObjectName : @"flower" ];
    [flowerModel setTextureName : @"flower" ];
    [flowerModel setScale : 1.0f];
    [flowerModel setHasTransparency : true];
    [flowerModel setNormalsPointingUp :true];
    
    
    /* Entity map of all the existing entities */
    return [NSDictionary dictionaryWithObjectsAndKeys:
            [NSNumber numberWithInt: NUMBER_OF_BANANA_TREES] ,bananaTreeModel,
            [NSNumber numberWithInt: NUMBER_OF_FERNS],fernModel,
            [NSNumber numberWithInt: NUMBER_OF_TREES],treeModel,
            [NSNumber numberWithInt: NUMBER_OF_GRASS],grassModel,
            [NSNumber numberWithInt: NUMBER_OF_FLOWERS],flowerModel,
             nil];
}

/*
 * @param loader
 *            loader that will load the entities of the 3D world
 *
 * @return The entities that will compose the 3D world
 */
+ (NSMutableArray*) getEntities : (Loader*) loader {
    NSDictionary* entitiesMap = [self getEntitiesMap];
    
    __block int totalModels = 0;
    [entitiesMap enumerateKeysAndObjectsUsingBlock:^(DefaultModelGenerator* key, NSNumber* value, BOOL* stop) {
        totalModels += value.intValue;
    }];
    //Alloccate the entities list
    __block NSMutableArray *entities = [[NSMutableArray alloc] initWithCapacity: totalModels];
    __block int count = 0;
    
    [entitiesMap enumerateKeysAndObjectsUsingBlock:^(DefaultModelGenerator* key, NSNumber* value, BOOL* stop) {
        TexturedModel* texturedModel = [self getTexturedObj: loader : key.objectName : key.textureName : key.hasTransparency : key.normalsPointingUp];
        int size = [value intValue];
        for(int i = 0;i < size; i++) {
            float xPosition = 20.0f + (rand() % 400);
            float zPosition =  (rand() % 400);
            Vector3f *entityPosition = [[Vector3f alloc] init : xPosition : 0.0f : zPosition];
            Entity *entity = [self getEntity: texturedModel : entityPosition];
            [entity setScale : [key scale]];
            entities[count++] = entity;
        }
    }];
    return entities;
}



@end
