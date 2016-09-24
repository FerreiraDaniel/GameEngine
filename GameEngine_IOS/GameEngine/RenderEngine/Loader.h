#import <Foundation/Foundation.h>
#import <OpenGLES/ES2/glext.h>
#import "IShape.h"
#import "RawModel.h"
#import "RenderConstants.h"
#import <GLKit/GLKit.h>
#import "EntityShaderConstants.h"
#import "TextureData.h"

@interface Loader : NSObject

- (id)init;
- (RawModel*) loadToVAO1: (id<IShape>) shape;

/**
 * Load a list of 3D positions to VAO
 *
 * @param positions
 *            Positions to load
 * @para positionsLength
 *            Number of positions to load in the vertex array object
 *
 * @return The rawModel pointing to the created VAO
 */
- (RawModel*) load3DPositionsToVAO1 : (float*) positions : (int) positionsLength;


- (void) defineTextureFunctionFilters : (int) target;
@end
