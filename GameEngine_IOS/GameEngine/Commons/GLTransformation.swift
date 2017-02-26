import Foundation


/// Helps to create the matrix to pass in the render as GL V1 has but adapted for GL V2
open class GLTransformation {
    
    /// The size of the matrix that is 4x4
    fileprivate let MATRIX_SIZE : Int = 16
    
    //The data of the matrix
    fileprivate var data : [Float]
    
    
    /// Initialize transformation
    public init()
    {
        self.data = Array<Float>(repeating: 0.0, count: MATRIX_SIZE)
    }
    
    /// Multiply the current matrix by a translation matrix
    ///
    /// - Parameters:
    ///   - x: Specify the x coordinate of a translation vector
    ///   - y: Specify the y coordinate of a translation vector
    ///   - z: Specify the z coordinate of a translation vector
    open func translate(x : Float, y : Float, z : Float) {
        //Set the last row of the transformation matrix to translate
        data[12] += (data[0] * x + data[4]
            * y + data[2 * 4] * z);
        data[13] += (data[1] * x + data[5]
            * y + data[2 * 4 + 1] * z);
        data[14] += (data[2] * x + data[6]
            * y + data[2 * 4 + 2] * z);
        data[15] += (data[3] * x + data[7]
            * y + data[2 * 4 + 3] * z);
    }
    
    /// Multiply two matrix and put the result in the data of the self object
    ///
    /// - Parameters:
    ///   - matA: First matrix
    ///   - matB: Second matrix
    fileprivate func multiplyMatrix(matA : Array<Float>, matB : Array<Float>)  {
        var tmp : [Float] = Array<Float>(repeating: 0.0, count: MATRIX_SIZE);
        
        for i : Int  in 0 ..< 4 {
            //Entire row
            let a0 : Float = matA[i * 4];
            let a1 : Float = matA[i * 4 + 1];
            let a2 : Float = matA[i * 4 + 2];
            let a3 : Float = matA[i * 4 + 3];
            
            for j : Int in 0 ..< 4 {
                //Entire column
                let b0 : Float = matB[0 * 4 + j]
                let b1 : Float = matB[1 * 4 + j]
                let b2 : Float = matB[2 * 4 + j]
                let b3 : Float = matB[3 * 4 + j]
                
                //Result
                let c0 : Float = (a0 * b0)
                let c1 : Float = (a1 * b1)
                let c2 : Float = (a2 * b2)
                let c3 : Float = (a3 * b3)
                
                tmp[i * 4 + j] =  c0 + c1 + c2 + c3
            }
        }
        
        //Copy temporary table to mMatrix
        for i : Int in 0 ..< MATRIX_SIZE {
            data[i] = tmp[i];
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
    open func rotate(angle : Float, x : Float, y : Float, z : Float) {
        let mag : CFloat = sqrt(x * x + y * y + z * z)
        
        let radians : CFloat = angle * Float(M_PI / 180.0)
        
        let sinAngle : Float = sinf(radians);
        let cosAngle : Float = cosf(radians);
        
        if (mag > 0.0) {
            var rotMat : [Float] = Array<Float>(repeating: 0.0, count: MATRIX_SIZE);
            
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
            
            
            self.multiplyMatrix(matA: rotMat, matB: data);
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
    fileprivate func frustum (left: Float, right: Float, bottom: Float, top: Float, nearZ: Float, farZ: Float) {
        let deltaX : Float = right - left;
        let deltaY : Float = top - bottom;
        let deltaZ : Float = farZ - nearZ;
        var frustum : [Float] = Array<Float>(repeating: 0.0, count: MATRIX_SIZE);
        
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
        
        self.multiplyMatrix(matA: frustum, matB: data);
    }
    
    
    
    /**
     * @param yViewAngle specifies the field of view angle, in degrees, in the Y direction.
     * @param aspect     specifies the aspect ration that determines the field of view in the x direction. The aspect ratio is the ratio of x (width) to y (height).
     * @param nearZ      specifies the distance from the viewer to the near clipping plane (always positive).
     * @param farZ       specifies the distance from the viewer to the far clipping plane (always positive).
     */
    open func perspective(yViewAngle : Float, aspect : Float, nearZ : Float, farZ : Float){
        
        let yAngle : Float = Math.toRadians(yViewAngle)
        
        let frustumH : Float = tan(yAngle) * nearZ;
        let frustumW : Float = frustumH * aspect;
        
        self.frustum(left: -frustumW, right: frustumW, bottom: -frustumH, top: frustumH, nearZ: nearZ, farZ: farZ)
    }
    
    
    
    /// Replaces the current matrix with the identity matrix
    open func loadIdentity(){
        for i : Int  in 0 ..<  MATRIX_SIZE {
            self.data[i] = 0.0;
        }
        
        //Set the main diagonal
        data[0] = 1.0;
        data[5] = 1.0;
        data[10] = 1.0;
        data[15] = 1.0;
    }
    
    /**
     * Multiples the matrix by on vector of scaling
     * @param sx 	scaling vector in the x-axle
     * @param sy	scaling vector in the y-axle
     * @param sz 	scaling vector in the z-axle
     */
    open func scale(x : Float, y : Float, z : Float) {
        var scaleMatrix : [Float] = Array<Float>(repeating: 0.0, count: MATRIX_SIZE);
        
        //Set the main diagonal
        scaleMatrix[0] = x;
        scaleMatrix[5] = y;
        scaleMatrix[10] = z;
        scaleMatrix[15] = 1.0;
        
        self.multiplyMatrix(matA: scaleMatrix, matB: data);
    }
    
    
    /**
     * Set the translation in the matrix
     *
     * @param tx 	translation vector in the x-axle
     * @param ty	translation vector in the y-axle
     * @param tz 	translation vector in the z-axle
     */
    open func setTranslation(_ tx : Float, ty: Float, tz : Float) {
        //The translation is the last row
        data[12] = tx;
        data[13] = ty;
        data[14] = tz;
    }
    
    
    /**
     * @return current matrix
     */
    open func getMatrix() ->  Array<Float> {
        return self.data;
    }
}
