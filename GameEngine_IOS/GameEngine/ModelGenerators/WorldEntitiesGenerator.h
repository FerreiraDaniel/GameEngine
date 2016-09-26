#import <Foundation/Foundation.h>
#import "IShape.h"
#import "TexturedModel.h"
#import "Entity.h"
#import "Vector3f.h"
#import "Light.h"
#import "DefaultModelGenerator.h"
#import "GameEngine-Swift.h"


/**
 * Responsible for creating the multiple entities of the 3D world
 */
@interface WorldEntitiesGenerator : NSObject

/*
 * @param loader
 *            loader that will load the entities of the 3D world
 *
 * @return The entities that will compose the 3D world
 */
+ (NSMutableArray*) getEntities : (Loader*) loader;

/**
 *
 * @return A source of light to the scene
 */
+ (Light*) getLight;

@end
