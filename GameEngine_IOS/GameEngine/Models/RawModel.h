#import <Foundation/Foundation.h>

@interface RawModel1 : NSObject

/**
 * Buffer of indices of the model
 */
@property (readonly) unsigned short int *indicesData;


/**
 Initiator of the raw model
 *
 * @param aIndicesData
                Data of the indices
 * @param aIndicesCount the
 *            number of vertex
 */
- (id)init : (unsigned short*) aIndicesData : (int) aIndicesCount ;
@end
