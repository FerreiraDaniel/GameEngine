#import <Foundation/Foundation.h>
#import "TexturedModel.h"
#import "Vector3f.h"

/**
 * Represents one entity in the 3D world
 */
@interface Entity : NSObject


/*3D model to be render*/
@property TexturedModel *model;

/*Position where the entity is*/
@property  Vector3f *position;

/*Rotation (X-axle) of the 3D model */
@property  float rotX;

/*Rotation (Y-axle) of the 3D model */
@property  float rotY;
/*Rotation (Z-axle) of the 3D model */
@property  float rotZ;

/*Scale of the model*/
@property  float scale;

/**
 * Initiator of the entity to be render in the 3D world
 *
 * @param aModel    Textured model
 * @param aPosition  Position where the model should be render
 * @param aRotX     Rotation of the model in the X axle
 * @param aRotY     Rotation of the model in the Y axle
 * @param aRotZ     Rotation of the model in the Z axle
 * @param aScale    Scale of the model
 */
- (id)init : (TexturedModel*) aModel : (Vector3f*) aPosition : (float) aRotX : (float) aRotY : (float) aRotZ : (float) aScale;

/**
 * Increases the position of the model using for that the specified components
 *
 * @param dx	X component to be increase
 * @param dy	Y component to be increase
 * @param dz	Z component to be increase
 */
- (void) increasePosition : (float) dx : (float) dy : (float) dz;

/**
 * Increases the rotation of the model using for that the specified components
 * @param dx	X component to be increase
 * @param dy	Y component to be increase
 * @param dz	Z component to be increase
 */
- (void) increaseRotation : (float) dx : (float) dy : (float) dz;


@end
