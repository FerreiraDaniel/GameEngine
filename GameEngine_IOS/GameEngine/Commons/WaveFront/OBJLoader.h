#import <Foundation/Foundation.h>
#import "IShape.h"
#import "Vector2f.h"
#import "Vector3f.h"
#import "PolygonalFace.h"
#import "WfObject.h"

@interface OBJLoader : NSObject

+ (id<IShape>) loadObjModel : (NSString*) objFileName;

@end
