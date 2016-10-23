import Foundation

/**
* Helps to create the matrix to pass in the render as GL V1 has but
* Adapted for GL V2
*/
public class GLTransformation {
    
    private let GL_TRANSFORMATION_MATRIX_SIZE : Int = 16
    
    private var mMatrix : [Float]
    
    
    /**
    * The initialize GL Transformation
    */
    public init()
    {
        self.mMatrix = Array<Float>(count: GL_TRANSFORMATION_MATRIX_SIZE, repeatedValue: 0.0)
    }
    
    /**
    * Multiply the current matrix by a translation matrix
    *
    * @param tx Specify the x coordinate of a translation vector
    * @param ty Specify the y coordinate of a translation vector
    * @param tz Specify the z coordinate of a translation vector
    */
    public func glTranslate(tx : Float, ty : Float, tz : Float) {
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
    * Multiply two matrix
    *
    * @param srcA First matrix
    * @param srcB Second matrix
    *
    */
    private func lMultiplyMatrix( srcA : Array<Float>, srcB : Array<Float>)  {
        var tmp : [Float] = Array<Float>(count: GL_TRANSFORMATION_MATRIX_SIZE, repeatedValue: 0.0);
        var i : Int = 0;
        var j : Int = 0;
        
        for (i  = 0; i < 4; i += 1) {
            //Entire row
            let a0 : Float = srcA[i * 4];
            let a1 : Float = srcA[i * 4 + 1];
            let a2 : Float = srcA[i * 4 + 2];
            let a3 : Float = srcA[i * 4 + 3];
            
            for(j = 0;j < 4; j += 1) {
                //Entire column
                let b0 : Float = srcB[0 * 4 + j]
                let b1 : Float = srcB[1 * 4 + j]
                let b2 : Float = srcB[2 * 4 + j]
                let b3 : Float = srcB[3 * 4 + j]
                
                //Result
                let c0 : Float = (a0 * b0)
                let c1 : Float = (a1 * b1)
                let c2 : Float = (a2 * b2)
                let c3 : Float = (a3 * b3)
                
                tmp[i * 4 + j] =  c0 + c1 + c2 + c3
            }
        }
        
        //Copy temp to mMatrix
        for(i = 0; i < GL_TRANSFORMATION_MATRIX_SIZE; i += 1) {
            mMatrix[i] = tmp[i];
        }
    }
    
    
    
    /**
    * Multiply the current matrix by a rotation matrix
    *
    * @param angle Specifies the angle of rotation, in degrees.
    * @param x     Specify the factor in x coordinate
    * @param y     Specify the factor in y coordinate
    * @param z     Specify the factor in z coordinate
    */
    public func glRotate(angle : Float, x : Float, y : Float, z : Float) {
        let mag : CFloat = sqrt(x * x + y * y + z * z)
        
        let radians : CFloat = angle * Float(M_PI / 180.0)
        
        let sinAngle : Float = sinf(radians);
        let cosAngle : Float = cosf(radians);
        
        if (mag > 0.0) {
            var rotMat : [Float] = Array<Float>(count: GL_TRANSFORMATION_MATRIX_SIZE, repeatedValue: 0.0);
            
            //Compute the normal vector
            let nx = x / mag;
            let ny = y / mag;
            let nz = z / mag;
            
            let xx : Float = nx * nx;
            let yy : Float = ny * ny;
            let zz : Float = nz * nz;
            let xy : Float = nx * ny;
            let yz : Float = ny * nz;
            let zx : Float = nz * nx;
            let xs : Float = nx * sinAngle;
            let ys : Float = ny * sinAngle;
            let zs : Float = nz * sinAngle;
            let oneMinusCos : Float = 1.0 - cosAngle;
            
            //First row
            rotMat[0] = (oneMinusCos * xx) + cosAngle;
            rotMat[1] = (oneMinusCos * xy) - zs;
            rotMat[2] = (oneMinusCos * zx) + ys;
            rotMat[3] = 0.0;
            
            //Second row
            rotMat[4] = (oneMinusCos * xy) + zs;
            rotMat[5] = (oneMinusCos * yy) + cosAngle;
            rotMat[6] = (oneMinusCos * yz) - xs;
            rotMat[7] = 0.0;
            
            //Third row
            rotMat[8] = (oneMinusCos * zx) - ys;
            rotMat[9] = (oneMinusCos * yz) + xs;
            rotMat[10] = (oneMinusCos * zz) + cosAngle;
            rotMat[11] = 0.0;
            
            //Fourth row
            rotMat[12] = 0.0;
            rotMat[13] = 0.0;
            rotMat[14] = 0.0;
            rotMat[15] = 1.0;
            
            
            self.lMultiplyMatrix(rotMat,srcB: mMatrix);
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
    private func glFrustum (left : Float, right : Float, bottom : Float, top : Float, nearZ : Float, farZ : Float) {
        let deltaX : Float = right - left;
        let deltaY : Float = top - bottom;
        let deltaZ : Float = farZ - nearZ;
        var frustum : [Float] = Array<Float>(count: GL_TRANSFORMATION_MATRIX_SIZE, repeatedValue: 0.0);
        
        if ((nearZ <= 0.0) || (farZ <= 0.0) || (deltaX <= 0.0)
            || (deltaY <= 0.0) || (deltaZ <= 0.0)) {
                return
        }
        
        //First row
        frustum[0] = 2.0 * nearZ / deltaX;
        frustum[1] = 0.0;
        frustum[2] = 0.0;
        frustum[3] = 0.0;
        
        //Second row
        frustum[4] = 0.0;
        frustum[5] = 2.0 * nearZ / deltaY;
        frustum[6] = 0.0;
        frustum[7] = 0.0;
        
        //Third row
        frustum[8] = (right + left) / deltaX;
        frustum[9] = (top + bottom) / deltaY;
        frustum[10] = -(nearZ + farZ) / deltaZ;
        frustum[11] = -1.0;
        
        //Fourth row
        frustum[12] = 0.0;
        frustum[13] = 0.0;
        frustum[14] = -2.0 * nearZ * farZ / deltaZ;
        frustum[15] = 0.0;
        
        self.lMultiplyMatrix(frustum, srcB : mMatrix);
    }
    
    
    
    /**
    * @param yViewAngle specifies the field of view angle, in degrees, in the Y direction.
    * @param aspect     specifies the aspect ration that determines the field of view in the x direction. The aspect ratio is the ratio of x (width) to y (height).
    * @param nearZ      specifies the distance from the viewer to the near clipping plane (always positive).
    * @param farZ       specifies the distance from the viewer to the far clipping plane (always positive).
    */
    public func  gluPerspective(yViewAngle : Float, aspect : Float, nearZ : Float, farZ : Float){
        
        let yAngle : Float = Math.toRadians(yViewAngle)
        
        let frustumH : Float = tan(yAngle) * nearZ;
        let frustumW : Float = frustumH * aspect;
        
        self.glFrustum(-frustumW, right: frustumW, bottom: -frustumH, top: frustumH, nearZ: nearZ, farZ: farZ)
    }
    
    
    /**
    * Replace the current matrix with the identity matrix
    */
    public func  glLoadIdentity(){
        var i : Int = 0;
        
        for (i = 0; i < GL_TRANSFORMATION_MATRIX_SIZE; i += 1) {
            self.mMatrix[i] = 0.0;
        }
        
        //Set the main diagonal
        mMatrix[0] = 1.0;
        mMatrix[5] = 1.0;
        mMatrix[10] = 1.0;
        mMatrix[15] = 1.0;
    }
    
    /**
    * Multiples the matrix by on vector of scaling
    * @param sx 	scaling vector in the x-axle
    * @param sy	scaling vector in the y-axle
    * @param sz 	scaling vector in the z-axle
    */
    public func  glScale(sx : Float, sy : Float, sz : Float) {
        //Set the main diagonal
        mMatrix[0] = sx * mMatrix[0];
        mMatrix[5] = sy * mMatrix[5];
        mMatrix[10] = sz * mMatrix[10];
        mMatrix[15] = 1.0 * mMatrix[15];
    }
    
    
    /**
    * Set the translation in the matrix
    *
    * @param tx 	translation vector in the x-axle
    * @param ty	translation vector in the y-axle
    * @param tz 	translation vector in the z-axle
    */
    public func setTranslation(tx : Float, ty: Float, tz : Float) {
        //The translation is the last row
        mMatrix[12] = tx;
        mMatrix[13] = ty;
        mMatrix[14] = tz;
    }
    
    
    /**
    * @return current matrix
    */
    public func getMatrix() ->  Array<Float> {
        return self.mMatrix;
    }
}
