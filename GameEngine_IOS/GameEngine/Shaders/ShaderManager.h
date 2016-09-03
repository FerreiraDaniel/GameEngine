#import <Foundation/Foundation.h>
#import "GLTransformation.h"
#import "Vector3f.h"

@interface ShaderManager : NSObject

/**
 * Initialize of the program shader manager
 *
 * @param vertexFile   File with vertex description
 * @param fragmentFile File with fragment description
 */
- (id)init: (NSString*) vertexFile :(NSString*) fragmentFile;
- (void) bindAttributes;
- (void) getAllUniformLocations;
- (void) bindAttribute: (int) attributeIndex : (char*) variableName;
- (int) getUniformLocation: (const char*) uniformName;
- (void) loadInt : (int) location : (int) value;
- (void) loadFloat : (int) location : (float) value;
- (void) loadVector : (int) location : (Vector3f*) vector;
- (void) loadBoolean :(int) location : (BOOL) value;
- (void) loadMatrix :(int) location : (GLTransformation*) matrix;
- (void) start;
- (void) stop;
- (void) dealloc;

@end
