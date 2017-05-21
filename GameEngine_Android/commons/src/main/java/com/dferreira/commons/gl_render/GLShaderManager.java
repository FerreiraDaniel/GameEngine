package com.dferreira.commons.gl_render;

import android.opengl.GLES20;

import com.dferreira.commons.ColorRGB;
import com.dferreira.commons.ColorRGBA;
import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.generic_render.IShaderManagerAPI;
import com.dferreira.commons.generic_render.ShaderProgram;
import com.dferreira.commons.Vector3f;

import java.nio.FloatBuffer;

/**
 * Make the required calls to manage the program shader used in OpenGL
 */
class GLShaderManager implements IShaderManagerAPI {


    /**
     * @param vertexShaderSrc Source code of the vertex shader
     * @param fragShaderSrc   Source code of the fragment shader
     * @return 0 -> There was an error
     * not 0 -> Id of the program loaded
     */
    @Override
    public ShaderProgram loadProgram(String vertexShaderSrc, String fragShaderSrc) {
        return GLSLUtils.loadProgram(vertexShaderSrc, fragShaderSrc);
    }

    /**
     * Link the program shader with their vertex shader and fragment shader
     *
     * @param shaderProgram The program shader not linked yet
     * @return False = Not linked True = Linked
     */
    @Override
    public boolean linkProgram(ShaderProgram shaderProgram) {
        return GLSLUtils.linkProgram(shaderProgram);
    }

    /**
     * Bind one attribute
     *
     * @param shaderProgram  The program shader for which is to bind the attribute
     * @param attributeIndex Index of the attribute to bind
     * @param variableName   Name of the attribute to bind
     */
    @Override
    public void glBindAttributeLocation(ShaderProgram shaderProgram, int attributeIndex, String variableName) {
        GLES20.glBindAttribLocation(shaderProgram.getProgramId(), attributeIndex, variableName);
    }

    /**
     * Get the position of one uniform variable in the program shader
     *
     * @param uniformName the name of the uniform variable as appears in the shader code
     * @return the position of the uniform variable in program shader
     */
    @Override
    public int getUniformLocation(ShaderProgram shaderProgram, Enum<?> uniformName) {
        return GLES20.glGetUniformLocation(shaderProgram.getProgramId(), uniformName.toString());
    }

    /**
     * Load a integer value to be used in the shader script
     *
     * @param location location of the shader variable in the script
     * @param value    The value to load
     */
    @Override
    public void loadInt(int location, int value) {
        GLES20.glUniform1i(location, value);
    }

    /**
     * Load a float value to be used in the shader script
     *
     * @param location location of the shader variable in the script
     * @param value    value to load
     */
    @Override
    public void loadFloat(int location, float value) {
        GLES20.glUniform1f(location, value);
    }

    /**
     * Load a 3D vector to be used in the shader script
     *
     * @param location location of the shader variable in the script
     * @param vector   The vector to load
     */
    @Override
    public void loadVector(int location, Vector3f vector) {
        GLES20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    /**
     * Load a color RGB to be used in the shader script
     *
     * @param location location of the shader variable in the script
     * @param color    The color to load
     */
    @Override
    public void loadColorRGB(int location, ColorRGB color) {
        GLES20.glUniform3f(location, color.r, color.g, color.b);
    }

    /**
     * Load a color RGBA to be used in the shader script
     *
     * @param location location of the shader variable in the script
     * @param color    The color to load
     */
    @Override
    public void loadColorRGBA(int location, ColorRGBA color) {
        GLES20.glUniform4f(location, color.r, color.g, color.b, color.a);
    }

    /**
     * Load a boolean value to be used in the shader script
     *
     * @param location The location of the shader variable in the script
     * @param value    value to load
     */
    @Override
    public void loadBoolean(int location, boolean value) {
        float toLoad = value ? 1 : 0;
        GLES20.glUniform1f(location, toLoad);
    }

    /**
     * Load a matrix to be used in the shader script
     *
     * @param location The location of the shader variable in the script
     * @param matrix   Matrix to load
     */
    @Override
    public void loadMatrix(int location, GLTransformation matrix) {
        FloatBuffer matrixFb = matrix.getAsFloatBuffer();

        GLES20.glUniformMatrix4fv(location, 1, false, matrixFb);
    }

    /**
     * Indicates that the next actions are over the specified shader program
     *
     * @param shaderProgram The shader program to start to use
     */
    @Override
    public void start(ShaderProgram shaderProgram) {
        if (shaderProgram != null) {
            GLES20.glUseProgram(shaderProgram.getProgramId());
        }
    }

    /**
     * Indicate that should not use a certain program no more
     */
    @Override
    public void stop() {
        GLES20.glUseProgram(0);
    }

    /**
     * Makes all the preparations to delete a program shader
     * and then delete it
     *
     * @param shaderProgram The program shader to delete
     */
    @Override
    public void deleteProgram(ShaderProgram shaderProgram) {
        int vertexShaderID = shaderProgram.getVertexShaderId();
        int fragmentShaderID = shaderProgram.getFragmentShaderId();
        int programId = shaderProgram.getProgramId();

        GLES20.glUseProgram(0);
        GLES20.glDetachShader(programId, vertexShaderID);
        GLES20.glDetachShader(programId, fragmentShaderID);
        GLES20.glDeleteShader(vertexShaderID);
        GLES20.glDeleteShader(fragmentShaderID);
        GLES20.glDeleteProgram(programId);
    }
}
