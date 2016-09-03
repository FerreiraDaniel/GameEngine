#import <Foundation/Foundation.h>
#import <math.h>

/**
 * Helps to create the matrix to pass in the render as GL V1 has but
 * Adapted for GL V2
 */
@interface GLTransformation : NSObject



/**
 * Multiply the current matrix by a translation matrix
 *
 * @param tx Specify the x coordinate of a translation vector
 * @param ty Specify the y coordinate of a translation vector
 * @param tz Specify the z coordinate of a translation vector
 */
- (void) glTranslate : (float) tx : (float) ty : (float) tz;


/**
 * Multiply the current matrix by a rotation matrix
 *
 * @param angle Specifies the angle of rotation, in degrees.
 * @param x     Specify the factor in x coordinate
 * @param y     Specify the factor in y coordinate
 * @param z     Specify the factor in z coordinate
 */
- (void) glRotate : (float) angle : (float) x : (float) y : (float) z;



/**
 * @param yViewAngle specifies the field of view angle, in degrees, in the Y direction.
 * @param aspect     specifies the aspect ration that determines the field of view in the x direction. The aspect ratio is the ratio of x (width) to y (height).
 * @param nearZ      specifies the distance from the viewer to the near clipping plane (always positive).
 * @param farZ       specifies the distance from the viewer to the far clipping plane (always positive).
 */
- (void) gluPerspective : (float) yViewAngle : (float) aspect : (float) nearZ : (float) farZ;



/**
 * The in the transformation the matrix that allow to get the transformations of the world
 *
 * @param modelView   Transformation over the model
 * @param perspective Transformation over the perspective
 */
- (void) multiplyMatrix : (GLTransformation*) modelView :  (GLTransformation*) perspective;

/**
 * Replace the current matrix with the identity matrix
 */
- (void) glLoadIdentity;

/**
 * Multiples the matrix by on vector of scaling
 * @param sx 	scaling vector in the x-axle
 * @param sy	scaling vector in the y-axle
 * @param sz 	scaling vector in the z-axle
 */
- (void) glScale : (float) sx : (float) sy : (float) sz;


/**
 * Set the translation in the matrix
 *
 * @param tx 	translation vector in the x-axle
 * @param ty	translation vector in the y-axle
 * @param tz 	translation vector in the z-axle
 */
- (void) setTranslation : (float) tx : (float) ty : (float) tz;


/**
 * @return current matrix
 */
- (float*) getMatrix;



@end
