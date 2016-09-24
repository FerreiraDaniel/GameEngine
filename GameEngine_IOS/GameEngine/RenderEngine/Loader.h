#import <Foundation/Foundation.h>
#import <OpenGLES/ES2/glext.h>
#import "IShape.h"
#import "RawModel.h"
#import "RenderConstants.h"
#import <GLKit/GLKit.h>
#import "EntityShaderConstants.h"
#import "TextureData.h"
#import "GameEngine-Swift.h"

@interface Loader : NSObject

- (id)init;
- (RawModel*) loadToVAO: (id<IShape>) shape;

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
- (RawModel*) load3DPositionsToVAO : (float*) positions : (int) positionsLength;

/**
 *  Load one texture from a file and set it in openGL
 *
 * @param fileName
 *            Name of the file to load without the .png extension in the end
 *
 * @return Identifier of the texture loaded
 */
- (int) loadTexture : (NSString*) fileName;

/**
 * Loads a cubic texture
 *
 * @param fileNames
 *            Names of the file to load without the .png extension in the
 *            end
 *
 * @return Identifier of the texture cubic texture loaded
 */
- (int) loadTCubeMap : (NSArray*) fileNames;
@end
