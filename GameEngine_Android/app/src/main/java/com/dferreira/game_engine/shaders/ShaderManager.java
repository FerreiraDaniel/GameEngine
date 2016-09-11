package com.dferreira.game_engine.shaders;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.dferreira.commons.GLSLUtils;
import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.IEnum;
import com.dferreira.commons.ShaderProgram;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.utils.LoadUtils;

import java.nio.FloatBuffer;
import java.util.List;

/**
 * Generic shader manager with methods to load vertex shader and fragment shader files
 * as well as useful methods to bind arguments
 */
public abstract class ShaderManager {

    private static final String TAG = "ShaderManager";
    private final ShaderProgram shaderProgram;


    /**
     * Constructor of the program shader manager
     *
     * @param context      Context where the game engine will be created
     * @param vertexFile   Identifier of the file with vertex description
     * @param fragmentFile Identifier of the file with fragment description
     */
    @SuppressWarnings({"SameParameterValue", "WeakerAccess"})
    public ShaderManager(Context context, int vertexFile, int fragmentFile) {

        String vertexShaderSrc = LoadUtils.readTextFromRawResource(context, vertexFile);
        String fragShaderSrc = LoadUtils.readTextFromRawResource(context, fragmentFile);
        this.shaderProgram = GLSLUtils.loadProgram(vertexShaderSrc, fragShaderSrc);

        if (this.shaderProgram == null) {
            Log.e(TAG, "Was impossible compile the program shader");
            return;
        }
        bindAttributes();
        boolean linked = GLSLUtils.linkProgram(shaderProgram);
        if (linked) {
            getAllUniformLocations();
        }
    }

    /**
     * Called to bind the attributes to the program shader
     */
    private void bindAttributes() {
        List<IEnum> attributes = getAttributes();
        if ((attributes != null) && (!attributes.isEmpty())) {
            for (IEnum attribute : attributes) {
                bindAttribute(attribute.getValue(), attribute.toString());
            }
        }
    }

    /**
     * Called to bind the attributes to the program shader
     */
    protected abstract List<IEnum> getAttributes();

    /**
     * Called to ensure that all the shader managers get their uniform locations
     */
    protected abstract void getAllUniformLocations();

    /**
     * Bind one attribute
     *
     * @param attributeIndex Index of the attribute to bind
     * @param variableName   Name of the attribute to bind
     */
    private void bindAttribute(int attributeIndex, String variableName) {
        GLES20.glBindAttribLocation(shaderProgram.getProgramId(), attributeIndex, variableName);
    }


    /**
     * Get the position of one uniform variable in the program shader
     *
     * @param uniformName
     *            the name of the uniform variable as appears in the shader code
     *
     * @return the position of the uniform variable in program shader
     */
    protected int getUniformLocation(Enum<?> uniformName) {
        int location = GLES20.glGetUniformLocation(shaderProgram.getProgramId(), uniformName.toString());
        if (location < 0) {
            Log.e(TAG, "Was not possible to load the location : " + uniformName);
        }
        return location;
    }

    /**
     * Load a integer value to be used in the shader script
     *
     * @param location
     *            location of the shader variable in the script
     * @param value
     *            The value to load
     */
    protected void loadInt(int location, int value) {
        GLES20.glUniform1i(location, value);
    }

    /**
     * Load a float value to be used in the shader script
     *
     * @param location location of the shader variable in the script
     * @param value    value to load
     */
    protected void loadFloat(int location, float value) {
        GLES20.glUniform1f(location, value);
    }

    /**
     * Load a vector to be used in the shader script
     *
     * @param location location of the shader variable in the script
     * @param vector The vector to load
     */
    protected void loadVector(int location, Vector3f vector) {
        GLES20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    /**
     * Load a boolean value to be used in the shader script
     *
     * @param location The location of the shader variable in the script
     * @param value    value to load
     */
    protected void loadBoolean(int location, boolean value) {
        float toLoad = value ? 1 : 0;
        GLES20.glUniform1f(location, toLoad);
    }

    /**
     * Load a matrix to be used in the shader script
     *
     * @param location The location of the shader variable in the script
     * @param matrix   Matrix to load
     */
    protected void loadMatrix(int location, GLTransformation matrix) {
        FloatBuffer matrixFb = matrix.getAsFloatBuffer();

        GLES20.glUniformMatrix4fv(location, 1, false, matrixFb);

    }


    /**
     * Indicates that should start to use a certain program shader
     */
    public void start() {
        if (shaderProgram != null) {
            GLES20.glUseProgram(shaderProgram.getProgramId());
        }
    }

    /**
     * Indicate that should not use a certain program no more
     */
    public void stop() {
        GLES20.glUseProgram(0);
    }

    /**
     * Clean the program shader from memory
     */
    public void cleanUp() {
        this.stop();
        int vertexShaderID = shaderProgram.getVertexShaderId();
        int fragmentShaderID = shaderProgram.getFragmentShaderId();
        int programId = shaderProgram.getProgramId();

        GLES20.glDetachShader(programId, vertexShaderID);
        GLES20.glDetachShader(programId, fragmentShaderID);
        GLES20.glDeleteShader(vertexShaderID);
        GLES20.glDeleteShader(fragmentShaderID);
        GLES20.glDeleteProgram(programId);
    }
}
