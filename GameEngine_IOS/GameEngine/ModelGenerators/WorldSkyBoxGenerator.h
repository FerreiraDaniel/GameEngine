#import <Foundation/Foundation.h>
#import "Loader.h"
#import "SkyBoxShape.h"
#import "SkyBox.h"

/**
 * Generate the sky box of the 3D scene
 */
@interface WorldSkyBoxGenerator : NSObject

/**
 * The sky of the 3D scene
 *
 * @param loader
 *            object that is going to read the textures of the sky
 *
 * @return A reference to the sky box loaded
 */
+ (SkyBox*) getSky : (Loader*) loader;
@end
