#import "RawModel.h"

@implementation RawModel



- (id)init : (int) aVaoId : (unsigned short*) aIndicesData : (int) aIndicesCount {
    self = [super init];
    if (self) {
        self -> _vaoId = aVaoId;
        self -> _indicesData = aIndicesData;
        self -> _indicesCount = aIndicesCount;
        
        //Allocate and fill the indices memory
        if(aIndicesData != nil) {
            int countIndicesBytes = sizeof(int) * aIndicesCount;
            self->_indicesData = calloc(aIndicesCount, sizeof(int));
            memcpy(self->_indicesData, aIndicesData, countIndicesBytes);
        }
    }
    return self;
}

/**
 *Desaloc the space used by the pointers of the object
 */
- (void)dealloc
{
    free(self->_indicesData);
}

@end
