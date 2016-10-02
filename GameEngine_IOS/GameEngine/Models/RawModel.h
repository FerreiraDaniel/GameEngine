#import <Foundation/Foundation.h>

@interface RawModel1 : NSObject

/**
 * Identifier of the vertex array object of the raw model
 */
@property (readonly) int vaoId;

/**
 * Buffer of indices of the model
 */
@property (readonly) unsigned short int *indicesData;

/**
 * Number of indices of the raw model
 */
@property (readonly) int indicesCount;


/**
 Initiator of the raw model
 *
 * @param aVaoId the
 *            vaoId assigned by openGL
 * @param aIndicesData
                Data of the indices
 * @param aIndicesCount the
 *            number of vertex
 */
- (id)init : (int) aVaoId : (unsigned short*) aIndicesData : (int) aIndicesCount;
@end
