#import <Foundation/Foundation.h>
#import "IShape.h"


#define SIZE 500.0f

@interface SkyBoxShape  : NSObject <IShape>

/**
 * Initiator of the sky box shape
 */
- (id)init;

@end
