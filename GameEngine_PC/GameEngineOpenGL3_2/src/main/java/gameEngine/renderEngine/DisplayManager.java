package gameEngine.renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.GL11.*;

/**
 * Class responsible for handling the creation, setup and close of window
 */
public class DisplayManager {

	private static final int WIDTH = 1024;
	private static final int HEIGHT = 600;
	private static final int FPS_CAP = 120;

	/**
	 * Create a new window with width and height specified
	 */
	public static void createDisplay() {

		// Use a specified version of OpenGL - namely version 3.2
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);

		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Game engine OpenGl 3.2");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Say to have one update rate of 120 FPS
	 */
	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
	}

	/**
	 * Close the screen that was just created
	 */
	public static void closeDisplay() {
		Display.destroy();
	}

	/**
	 * 
	 * @return False if should keep render the window True otherwise
	 */
	public static boolean closeWasNotRequested() {
		return !Display.isCloseRequested();
	}

	/**
	 * Print the information of the system in the screen
	 */
	public static void printSystemInfo() {
		System.out.println("LWJGL: " + Sys.getVersion() + " / " + LWJGLUtil.getPlatformName());
		System.out.println("GL_VENDOR: " + glGetString(GL_VENDOR));
		System.out.println("GL_RENDERER: " + glGetString(GL_RENDERER));
		System.out.println("GL_VERSION: " + glGetString(GL_VERSION));
		System.out.println();
		System.out.println(
				"glLoadTransposeMatrixfARB() supported: " + GLContext.getCapabilities().GL_ARB_transpose_matrix);
	}

}
