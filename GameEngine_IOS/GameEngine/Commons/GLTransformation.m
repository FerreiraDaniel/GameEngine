#import "GLTransformation.h"

@implementation GLTransformation
{
    @private
        float mMatrix[16];
}

#define GL_TRANSFORMATION_MATRIX_SIZE 16

/**
 * The initialize GL Transformation
 
 */
- (id)init
{
    self = [super init];
    if (self) {
    }
    return self;
}


/**
 * Multiply the current matrix by a translation matrix
 *
 * @param tx Specify the x coordinate of a translation vector
 * @param ty Specify the y coordinate of a translation vector
 * @param tz Specify the z coordinate of a translation vector
 */
- (void) glTranslate : (float) tx : (float) ty : (float) tz {
    //Set the last row of the transformation matrix to translate
    mMatrix[12] += (mMatrix[0] * tx + mMatrix[4]
                    * ty + mMatrix[2 * 4] * tz);
    mMatrix[13] += (mMatrix[1] * tx + mMatrix[5]
                    * ty + mMatrix[2 * 4 + 1] * tz);
    mMatrix[14] += (mMatrix[2] * tx + mMatrix[6]
                    * ty + mMatrix[2 * 4 + 2] * tz);
    mMatrix[15] += (mMatrix[3] * tx + mMatrix[7]
                    * ty + mMatrix[2 * 4 + 3] * tz);
}

/**
 * Multiply the current matrix by a rotation matrix
 *
 * @param angle Specifies the angle of rotation, in degrees.
 * @param x     Specify the factor in x coordinate
 * @param y     Specify the factor in y coordinate
 * @param z     Specify the factor in z coordinate
 */
- (void) glRotate : (float) angle : (float) x : (float) y : (float) z {
    float sinAngle, cosAngle;
    float mag = (float) sqrt((double) (x * x + y * y + z * z));
    
    sinAngle = (float) sin(angle * M_PI / 180.0);
    cosAngle = (float) cos(angle * M_PI / 180.0);
    if (mag > 0.0f) {
        float xx, yy, zz, xy, yz, zx, xs, ys, zs;
        float oneMinusCos;
        float rotMat[GL_TRANSFORMATION_MATRIX_SIZE];
        
        x /= mag;
        y /= mag;
        z /= mag;
        
        xx = x * x;
        yy = y * y;
        zz = z * z;
        xy = x * y;
        yz = y * z;
        zx = z * x;
        xs = x * sinAngle;
        ys = y * sinAngle;
        zs = z * sinAngle;
        oneMinusCos = 1.0f - cosAngle;
        
        //First row
        rotMat[0] = (oneMinusCos * xx) + cosAngle;
        rotMat[1] = (oneMinusCos * xy) - zs;
        rotMat[2] = (oneMinusCos * zx) + ys;
        rotMat[3] = 0.0F;
        
        //Second row
        rotMat[4] = (oneMinusCos * xy) + zs;
        rotMat[5] = (oneMinusCos * yy) + cosAngle;
        rotMat[6] = (oneMinusCos * yz) - xs;
        rotMat[7] = 0.0F;
        
        //Third row
        rotMat[8] = (oneMinusCos * zx) - ys;
        rotMat[9] = (oneMinusCos * yz) + xs;
        rotMat[10] = (oneMinusCos * zz) + cosAngle;
        rotMat[11] = 0.0F;
        
        //Fourth row
        rotMat[12] = 0.0F;
        rotMat[13] = 0.0F;
        rotMat[14] = 0.0F;
        rotMat[15] = 1.0F;
        
        [self lMultiplyMatrix:  rotMat : mMatrix];
    }
}

/**
 * Multiply the current matrix by a perspective matrix
 *
 * @param left   Specify the coordinate for the vertical clipping plane.
 * @param right  Specify the coordinate for the right vertical clipping plane.
 * @param bottom Specify the coordinate for the bottom horizontal clipping plane.
 * @param top    Specify the coordinate for the horizontal clipping plane.
 * @param nearZ  Specify the distance to the near clipping plane. Distances must be positive.
 * @param farZ   Specify the distance to the far depth clipping plane. Distances must be positive.
 */
- (void) glFrustum : (float) left : (float) right : (float) bottom : (float) top :
(float) nearZ : (float) farZ {
    float deltaX = right - left;
    float deltaY = top - bottom;
    float deltaZ = farZ - nearZ;
    float frustum[GL_TRANSFORMATION_MATRIX_SIZE];
    
    if ((nearZ <= 0.0f) || (farZ <= 0.0f) || (deltaX <= 0.0f)
        || (deltaY <= 0.0f) || (deltaZ <= 0.0f))
        return;
    
    //First row
    frustum[0] = 2.0f * nearZ / deltaX;
    frustum[1] = 0.0f;
    frustum[2] = 0.0f;
    frustum[3] = 0.0f;
    
    //Second row
    frustum[4] = 0.0f;
    frustum[5] = 2.0f * nearZ / deltaY;
    frustum[6] = 0.0f;
    frustum[7] = 0.0f;
    
    //Third row
    frustum[8] = (right + left) / deltaX;
    frustum[9] = (top + bottom) / deltaY;
    frustum[10] = -(nearZ + farZ) / deltaZ;
    frustum[11] = -1.0f;
    
    //Fourth row
    frustum[12] = 0.0f;
    frustum[13] = 0.0f;
    frustum[14] = -2.0f * nearZ * farZ / deltaZ;
    frustum[15] = 0.0f;
    
    [self lMultiplyMatrix : frustum : mMatrix];
}

/**
 * @param yViewAngle specifies the field of view angle, in degrees, in the Y direction.
 * @param aspect     specifies the aspect ration that determines the field of view in the x direction. The aspect ratio is the ratio of x (width) to y (height).
 * @param nearZ      specifies the distance from the viewer to the near clipping plane (always positive).
 * @param farZ       specifies the distance from the viewer to the far clipping plane (always positive).
 */
- (void) gluPerspective : (float) yViewAngle : (float) aspect : (float) nearZ : (float) farZ {
    float frustumW, frustumH;
    
    frustumH = (float) tan(yViewAngle / 360.0 * M_PI) * nearZ;
    frustumW = frustumH * aspect;
    
    [self glFrustum: -frustumW : frustumW : -frustumH : frustumH : nearZ : farZ];
}

/**
 * Multiply two matrix
 *
 * @param srcA First matrix
 * @param srcB Second matrix
 *
 */
- (void) lMultiplyMatrix : (float*) srcA : (float*) srcB {
    float tmp[GL_TRANSFORMATION_MATRIX_SIZE];
    int i;
    
    for (i = 0; i < 4; i++) {
        tmp[i * 4] = (srcA[i * 4] * srcB[0])
        + (srcA[i * 4 + 1] * srcB[4])
        + (srcA[i * 4 + 2] * srcB[2 * 4])
        + (srcA[i * 4 + 3] * srcB[3 * 4]);
        
        tmp[i * 4 + 1] = (srcA[i * 4] * srcB[1])
        + (srcA[i * 4 + 1] * srcB[5])
        + (srcA[i * 4 + 2] * srcB[2 * 4 + 1])
        + (srcA[i * 4 + 3] * srcB[3 * 4 + 1]);
        
        tmp[i * 4 + 2] = (srcA[i * 4] * srcB[2])
        + (srcA[i * 4 + 1] * srcB[6])
        + (srcA[i * 4 + 2] * srcB[2 * 4 + 2])
        + (srcA[i * 4 + 3] * srcB[3 * 4 + 2]);
        
        tmp[i * 4 + 3] = (srcA[i * 4] * srcB[3])
        + (srcA[i * 4 + 1] * srcB[7])
        + (srcA[i * 4 + 2] * srcB[2 * 4 + 3])
        + (srcA[i * 4 + 3] * srcB[3 * 4 + 3]);
    }
    
    //Copy temp to mMatrix
    for(i = 0; i < GL_TRANSFORMATION_MATRIX_SIZE; i++) {
        mMatrix[i] = tmp[i];
    }
}

/**
 * The in the transformation the matrix that allow to get the transformations of the world
 *
 * @param modelView   Transformation over the model
 * @param perspective Transformation over the perspective
 */
- (void) multiplyMatrix : (GLTransformation*) modelView :  (GLTransformation*) perspective {
    [self lMultiplyMatrix: [modelView getMatrix] : [perspective getMatrix]];
}

/**
 * Replace the current matrix with the identity matrix
 */
- (void) glLoadIdentity {
    for (int i = 0; i < GL_TRANSFORMATION_MATRIX_SIZE; i++)
        mMatrix[i] = 0.0f;
    
    //Set the main diagonal
    mMatrix[0] = 1.0f;
    mMatrix[5] = 1.0f;
    mMatrix[10] = 1.0f;
    mMatrix[15] = 1.0f;
}

/**
 * Multiples the matrix by on vector of scaling
 * @param sx 	scaling vector in the x-axle
 * @param sy	scaling vector in the y-axle
 * @param sz 	scaling vector in the z-axle
 */
- (void) glScale : (float) sx : (float) sy : (float) sz {
    //Set the main diagonal
    mMatrix[0] = sx * mMatrix[0];
    mMatrix[5] = sy * mMatrix[5];
    mMatrix[10] = sz * mMatrix[10];
    mMatrix[15] = 1.0f * mMatrix[15];
}


/**
 * Set the translation in the matrix
 *
 * @param tx 	translation vector in the x-axle
 * @param ty	translation vector in the y-axle
 * @param tz 	translation vector in the z-axle
 */
- (void) setTranslation : (float) tx : (float) ty : (float) tz {
    //The translation is the last column
    mMatrix[12] = tx;
    mMatrix[13] = ty;
    mMatrix[14] = tz;
}

/**
 * @return current matrix
 */
- (float*) getMatrix {
    return mMatrix;
}

@end
