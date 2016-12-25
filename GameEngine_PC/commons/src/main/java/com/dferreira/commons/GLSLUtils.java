package com.dferreira.commons;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 * Allow to load a shader program (fragment and vertex shader)
 */
public class GLSLUtils {

	/* Maximum length of the log */
	private static final int STR_MAX_LEN = 1000;

	/**
	 * Load and compiles the shader into device memory
	 *
	 * @param type
	 *            Type of shader (GL_VERTEX_SHADER or GL_FRAGMENT_SHADER)
	 * @param shaderSrc
	 *            a string with source code of the shader to compile
	 * @return 0 -> There was an error not 0 -> Id of the shader compiled
	 */
	private static int compileShader(int type, String shaderSrc) {
		int shaderId;
		int compileStatus;

		// Create the shader object
		shaderId = GL20.glCreateShader(type);

		if (shaderId == 0)
			return 0;

		// Load the shader source
		GL20.glShaderSource(shaderId, shaderSrc);

		// Compile the shader
		GL20.glCompileShader(shaderId);

		// Check the compile status
		compileStatus = GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS);

		if (compileStatus == GL11.GL_FALSE) {
			System.err.println(GL20.glGetShaderInfoLog(shaderId, STR_MAX_LEN));
			System.err.println("Could not compile shader");
			GL20.glDeleteShader(shaderId);
			return 0;
		}
		return shaderId;
	}

	/**
	 * Compiles the shader sources and attache them to the program
	 * 
	 * @param vertexShaderSrc
	 *            Source code of the vertex shader
	 * @param fragShaderSrc
	 *            Source code of the fragment shader
	 * @return 0 -> There was an error not 0 -> Id of the program loaded
	 */
	public static ShaderProgram loadProgram(String vertexShaderSrc, String fragShaderSrc) {
		int vertexShader;
		int fragmentShader;
		int programId;

		// Load the vertex/fragment shader(s)
		vertexShader = compileShader(GL20.GL_VERTEX_SHADER, vertexShaderSrc);
		if (vertexShader == GL11.GL_FALSE)
			return null;

		fragmentShader = compileShader(GL20.GL_FRAGMENT_SHADER, fragShaderSrc);
		if (fragmentShader == GL11.GL_FALSE) {
			GL20.glDeleteShader(vertexShader);
			return null;
		}

		// Create the program object
		programId = GL20.glCreateProgram();

		if (programId == GL11.GL_FALSE)
			return null;

		GL20.glAttachShader(programId, vertexShader);
		GL20.glAttachShader(programId, fragmentShader);

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
	 * 
	 * @return False = Not linked True = Linked
	 */
	public static boolean linkProgram(ShaderProgram shaderProgram) {
		int linkStatus;

		// Link the program all together
		GL20.glLinkProgram(shaderProgram.getProgramId());

		// Check the link status
		linkStatus = GL20.glGetProgrami(shaderProgram.getProgramId(), GL20.GL_LINK_STATUS);

		if (linkStatus == GL11.GL_FALSE) {
			System.err.println("Error linking program:");
			System.out.println(GL20.glGetShaderInfoLog(shaderProgram.getProgramId(), STR_MAX_LEN));
			GL20.glDeleteProgram(shaderProgram.getProgramId());
			return false;
		} else {
			// Free up no longer needed shader resources
			GL20.glDeleteShader(shaderProgram.getVertexShaderId());
			GL20.glDeleteShader(shaderProgram.getFragmentShaderId());
			return true;
		}
	}

}
