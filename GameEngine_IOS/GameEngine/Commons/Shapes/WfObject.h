#import <Foundation/Foundation.h>
#import "IShape.h"

/**
 *	Represents one entity with a shape
 *defined by one waveFront file
 */
@interface WfObject : NSObject <IShape>

/**
 * Inicializes the waveFront file
 */
- (id)init :
(float*) aVertices :
(int) aCountVertices :
(float*) aTextureCoordinates :
(int) aCountTextureCoordinates :
(float*) aNormals :
(int) aCountNormals :
(unsigned short*) aIndices :
(int) aCountIndices;

@end
