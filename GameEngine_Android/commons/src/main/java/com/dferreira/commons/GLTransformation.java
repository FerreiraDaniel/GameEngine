package com.dferreira.commons;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Helps to create the matrix to pass in the render as GL V1 has but
 * Adapted for GL V2
 */
public class GLTransformation {

    /*The size of a side of the matrix*/
    @SuppressWarnings("FieldCanBeLocal")
    private final int MATRIX_SIDE = 4;


    @SuppressWarnings("FieldCanBeLocal")
    private final int FLOAT_SIZE = 4;
    /*The size of the matrix that is 4x4*/
    private final int MATRIX_SIZE = 16;

    private float[] mMatrix = new float[MATRIX_SIZE];
    private final FloatBuffer mMatrixFloatBuffer;

    /**
     * Simply allocates space for a matrix of [4x4] floats
     */
    public GLTransformation() {
        mMatrixFloatBuffer = ByteBuffer.allocateDirect(MATRIX_SIZE * FLOAT_SIZE)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
    }


    /**
     * Multiply the current matrix by a translation matrix
     *
     * @param x Specify the x coordinate of a translation vector
     * @param y Specify the y coordinate of a translation vector
     * @param z Specify the z coordinate of a translation vector
     */
    public void translate(float x, float y, float z) {
        //Set the last row of the transformation matrix to translate
        mMatrix[12] += (mMatrix[0] * x + mMatrix[4]
                * y + mMatrix[2 * 4] * z);
        mMatrix[13] += (mMatrix[1] * x + mMatrix[5]
                * y + mMatrix[2 * 4 + 1] * z);
        mMatrix[14] += (mMatrix[2] * x + mMatrix[6]
                * y + mMatrix[2 * 4 + 2] * z);
        mMatrix[15] += (mMatrix[3] * x + mMatrix[7]
                * y + mMatrix[2 * 4 + 3] * z);
    }

    /**
     * Multiply the current matrix by a rotation matrix
     *
     * @param angle Specifies the angle of rotation, in degrees.
     * @param x     Specify the factor in x coordinate
     * @param y     Specify the factor in y coordinate
     * @param z     Specify the factor in z coordinate
     */
    public void rotate(float angle, float x, float y, float z) {
        float mag = (float) Math.sqrt((double) (x * x + y * y + z * z));
        double yAngle = Math.toRadians(angle);


        float sinAngle = (float) Math.sin(yAngle);
        float cosAngle = (float) Math.cos(yAngle);
        if (mag > 0.0f) {
            float xx, yy, zz, xy, yz, zx, xs, ys, zs;
            float oneMinusCos;
            float[] rotMat = new float[MATRIX_SIZE];

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

            multiplyMatrix(rotMat, mMatrix);
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
    private void frustum(float left, float right, float bottom, float top,
                         float nearZ, float farZ) {
        float deltaX = right - left;
        float deltaY = top - bottom;
        float deltaZ = farZ - nearZ;
        float[] frustum = new float[MATRIX_SIZE];

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

        multiplyMatrix(frustum, mMatrix);
    }

    /**
     * Multiply the current matrix by a matrix that should transform the perspective of the player
     *
     * @param yAngle specifies the field of view angle, in degrees, in the Y direction.
     * @param aspect specifies the aspect ration that determines the field of view in the x direction. The aspect ratio is the ratio of x (width) to y (height).
     * @param nearZ  specifies the distance from the viewer to the near clipping plane (always positive).
     * @param farZ   specifies the distance from the viewer to the far clipping plane (always positive).
     */
    @SuppressWarnings("SameParameterValue")
    public void perspective(float yAngle, float aspect, float nearZ, float farZ) {
        double rAngle = Math.toRadians(yAngle);

        float frustumH = (float) Math.tan(rAngle) * nearZ;
        float frustumW = frustumH * aspect;

        frustum(-frustumW, frustumW, -frustumH, frustumH, nearZ, farZ);
    }

    /**
     * Multiply two matrix
     *
     * @param mtlA First matrix
     * @param mtlB Second matrix
     */
    private void multiplyMatrix(float[] mtlA, float[] mtlB) {
        float[] tmp = new float[MATRIX_SIZE];
        int i;

        for (i = 0; i < MATRIX_SIDE; i++) {
            tmp[i * MATRIX_SIDE] = (mtlA[i * 4] * mtlB[0])
                    + (mtlA[i * 4 + 1] * mtlB[4])
                    + (mtlA[i * 4 + 2] * mtlB[2 * 4])
                    + (mtlA[i * 4 + 3] * mtlB[3 * 4]);

            tmp[i * MATRIX_SIDE + 1] = (mtlA[i * 4] * mtlB[1])
                    + (mtlA[i * 4 + 1] * mtlB[5])
                    + (mtlA[i * 4 + 2] * mtlB[2 * 4 + 1])
                    + (mtlA[i * 4 + 3] * mtlB[3 * 4 + 1]);

            tmp[i * MATRIX_SIDE + 2] = (mtlA[i * 4] * mtlB[2])
                    + (mtlA[i * 4 + 1] * mtlB[6])
                    + (mtlA[i * 4 + 2] * mtlB[2 * 4 + 2])
                    + (mtlA[i * 4 + 3] * mtlB[3 * 4 + 2]);

            tmp[i * MATRIX_SIDE + 3] = (mtlA[i * 4] * mtlB[3])
                    + (mtlA[i * 4 + 1] * mtlB[7])
                    + (mtlA[i * 4 + 2] * mtlB[2 * 4 + 3])
                    + (mtlA[i * 4 + 3] * mtlB[3 * 4 + 3]);
        }

        mMatrix = tmp;
    }

    /**
     * The in the transformation the matrix that allow to get the transformations of the world
     *
     * @param modelView   Transformation over the model
     * @param perspective Transformation over the perspective
     */
    @SuppressWarnings("unused")
    public void multiplyMatrix(GLTransformation modelView, GLTransformation perspective) {
        this.multiplyMatrix(modelView.get(), perspective.get());
    }

    /**
     * Replace the current matrix with the identity matrix
     */
    public void loadIdentity() {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            mMatrix[i] = 0.0f;
        }

        //Set the main diagonal
        mMatrix[0] = 1.0f;
        mMatrix[5] = 1.0f;
        mMatrix[10] = 1.0f;
        mMatrix[15] = 1.0f;
    }

    /**
     * Multiples the matrix by on vector of scaling
     *
     * @param x scaling vector in the x-axle
     * @param y scaling vector in the y-axle
     * @param z scaling vector in the z-axle
     */
    public void scale(float x, float y, float z) {
        float[] scaleMatrix = new float[MATRIX_SIZE];

        for (int i = 0; i < MATRIX_SIZE; i++) {
            scaleMatrix[i] = 0.0f;
        }

        //Set the main diagonal
        scaleMatrix[0] = x;
        scaleMatrix[5] = y;
        scaleMatrix[10] = z;
        scaleMatrix[15] = 1.0f;

        this.multiplyMatrix(scaleMatrix, this.mMatrix);
    }

    /**
     * Set the translation in the matrix
     *
     * @param x translation vector in the x-axle
     * @param y translation vector in the y-axle
     * @param z translation vector in the z-axle
     */
    @SuppressWarnings("SameParameterValue")
    public void setTranslation(float x, float y, float z) {
        //The translation is the last column
        mMatrix[12] = x;
        mMatrix[13] = y;
        mMatrix[14] = z;
    }


    /**
     * @return current matrix in float buffer type
     */
    public FloatBuffer getAsFloatBuffer() {
        mMatrixFloatBuffer.put(mMatrix).position(0);
        return mMatrixFloatBuffer;
    }

    /**
     * @return current matrix
     */
    private float[] get() {
        return mMatrix;
    }

}