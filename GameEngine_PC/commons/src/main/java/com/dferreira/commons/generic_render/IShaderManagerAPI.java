package com.dferreira.commons.generic_render;

import com.dferreira.commons.ColorRGB;
import com.dferreira.commons.ColorRGBA;
import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.Vector3f;
import com.dferreira.commons.generic_resources.TextEnum;

/**
 * Interface that should be implemented by the interface that makes the access to the
 * Program shader
 */

public interface IShaderManagerAPI {
    /**
     * @param vertexShader Source code of the vertex shader
     * @param fragShader   Source code of the fragment shader
     * @return 0 -> There was an error
     * not 0 -> Id of the program loaded
     */
    ShaderProgram loadProgram(TextEnum vertexShader, TextEnum fragShader);

    /**
     * Link the program shader with their vertex shader and fragment shader
     *
     * @param shaderProgram The program shader not linked yet
     * @return False = Not linked True = Linked
     */
    boolean linkProgram(ShaderProgram shaderProgram);

    /**
     * Bind one attribute
     *
     * @param shaderProgram  The program shader for which is to bind the attribute
     * @param attributeIndex Index of the attribute to bind
     * @param variableName   Name of the attribute to bind
     */
    void glBindAttributeLocation(ShaderProgram shaderProgram, int attributeIndex, String variableName);

    /**
     * Get the position of one uniform variable in the program shader
     *
     * @param shaderProgram The program shader
     * @param uniformName   the name of the uniform variable as appears in the shader code
     * @return the position of the uniform variable in program shader
     */
    int getUniformLocation(ShaderProgram shaderProgram, Enum<?> uniformName);

    /**
     * Load a integer value to be used in the shader script
     *
     * @param location location of the shader variable in the script
     * @param value    The value to load
     */
    void loadInt(int location, int value);

    /**
     * Load a float value to be used in the shader script
     *
     * @param location location of the shader variable in the script
     * @param value    value to load
     */
    void loadFloat(int location, float value);


    /**
     * Load a 3D vector to be used in the shader script
     *
     * @param location location of the shader variable in the script
     * @param vector   The vector to load
     */
    void loadVector(int location, Vector3f vector);

    /**
     * Load a color RGB to be used in the shader script
     *
     * @param location location of the shader variable in the script
     * @param color    The color to load
     */
    void loadColorRGB(int location, ColorRGB color);

    /**
     * Load a color RGBA to be used in the shader script
     *
     * @param location location of the shader variable in the script
     * @param color    The color to load
     */
    void loadColorRGBA(int location, ColorRGBA color);

    /**
     * Load a boolean value to be used in the shader script
     *
     * @param location The location of the shader variable in the script
     * @param value    value to load
     */
    void loadBoolean(int location, boolean value);

    /**
     * Load a matrix to be used in the shader script
     *
     * @param location The location of the shader variable in the script
     * @param matrix   Matrix to load
     */
    void loadMatrix(int location, GLTransformation matrix);

    /**
     * Indicates that the next actions are over the specified shader program
     *
     * @param shaderProgram The shader program to start to use
     */
    void start(ShaderProgram shaderProgram);

    /**
     * Indicate that should not use a certain program no more
     */
    void stop();

    /**
     * Makes all the preparations to delete a program shader
     * and then delete it
     *
     * @param shaderProgram The program shader to delete
     */
    void deleteProgram(ShaderProgram shaderProgram);
}
