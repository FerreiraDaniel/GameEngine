#import "DefaultModelGenerator.h"

@implementation DefaultModelGenerator

- (id)copyWithZone:(NSZone *)zone {
    DefaultModelGenerator *result = [[[self class] allocWithZone:zone] init];
    
    result -> _hasTransparency = self.hasTransparency;
    result -> _normalsPointingUp = self.normalsPointingUp;
    result -> _objectName = self.objectName;
    result -> _scale = self.scale;
    result -> _textureName = self.textureName;
    return result;
}

@end
