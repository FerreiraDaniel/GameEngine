#import "Vector3f.h"

@implementation Vector3f


- (id)init : (float) aX : (float) aY : (float) aZ
{
    self = [super init : aX : aY];
    if (self) {
        [self setZ: aZ];
    }
    return self;
}

@end
