#import "Vector2f.h"

@implementation Vector2f


- (id)init : (float) aX : (float) aY
{
    self = [super init];
    if (self) {
        [self setX: aX];
        [self setY: aY];
    }
    return self;
}

@end
