package com.dferreira.commons.gl_render;

import android.opengl.GLES10;
import android.opengl.GLES20;
import android.util.Log;

import com.dferreira.commons.generic_render.ShaderProgram;

/**
 * Allow to load a shader program (fragment and vertex shader)
 */
class GLSLUtils {

    private final static String TAG = "GLSLUtils";

    /**
     * Load and compiles the shader into device memory
     *
     * @param type      Type of shader (GL_VERTEX_SHADER or GL_FRAGMENT_SHADER)
     * @param shaderSrc a string with source code of the shader to compile
     * @return 0 -> There was an error
     * not 0 -> Id of the shader compiled
     */
    private static int compileShader(int type, String shaderSrc) {
        int shaderId;
        int[] compiled = new int[1];

        // Create the shader object
        shaderId = GLES20.glCreateShader(type);

        if (shaderId == 0)
            return 0;

        // Load the shader source
        GLES20.glShaderSource(shaderId, shaderSrc);

        // Compile the shader
        GLES20.glCompileShader(shaderId);

        // Check the compile status
        GLES20.glGetShaderiv(shaderId, GLES20.GL_COMPILE_STATUS, compiled, 0);


        if (compiled[0] == 0) {
            Log.e(TAG, GLES20.glGetShaderInfoLog(shaderId));
            GLES20.glDeleteShader(shaderId);
            return 0;
        }
        return shaderId;
    }


    /**
     * @param vertexShaderSrc Source code of the vertex shader
     * @param fragShaderSrc   Source code of the fragment shader
     * @return 0 -> There was an error
     * not 0 -> Id of the program loaded
     */
    static ShaderProgram loadProgram(String vertexShaderSrc, String fragShaderSrc) {
        int vertexShader;
        int fragmentShader;
        int programId;


        // Load the vertex/fragment shader(s)
        vertexShader = compileShader(GLES20.GL_VERTEX_SHADER, vertexShaderSrc);
        if (vertexShader == GLES10.GL_FALSE) {
            Log.e(TAG, "Was not possible compile the vertex shader");

            return null;
        }


        fragmentShader = compileShader(GLES20.GL_FRAGMENT_SHADER, fragShaderSrc);
        if (fragmentShader == GLES10.GL_FALSE) {
            Log.e(TAG, "Was not possible compile the fragment shader");
            GLES20.glDeleteShader(vertexShader);
            return null;
        }

        // Create the program object
        programId = GLES20.glCreateProgram();

        if (programId == GLES10.GL_FALSE)
            return null;

        GLES20.glAttachShader(programId, vertexShader);
        GLES20.glAttachShader(programId, fragmentShader);


        /* Build the program shader not linked yet */
        ShaderProgram program = new ShaderProgram();
        program.setVertexShaderId(vertexShader);
        program.setFragmentShaderId(fragmentShader);
        program.setProgramId(programId);

        return program;
    }

    /**
     * Link the program shader with their vertex shader and fragment shader
     *
     * @param shaderProgram The program shader not linked yet
     * @return False = Not linked True = Linked
     */
    static boolean linkProgram(ShaderProgram shaderProgram) {
        int[] linked = new int[1];

        // Link the program all together
        GLES20.glLinkProgram(shaderProgram.getProgramId());

        // Check the link status
        GLES20.glGetProgramiv(shaderProgram.getProgramId(), GLES20.GL_LINK_STATUS, linked, 0);


        if (linked[0] == GLES10.GL_FALSE) {
            Log.e(TAG, "Error linking program:");
            Log.e(TAG, GLES20.glGetProgramInfoLog(shaderProgram.getProgramId()));
            GLES20.glDeleteProgram(shaderProgram.getProgramId());
            return false;
        } else {
            // Free up no longer needed shader resources
            GLES20.glDeleteShader(shaderProgram.getVertexShaderId());
            GLES20.glDeleteShader(shaderProgram.getFragmentShaderId());
            return true;
        }
    }
}