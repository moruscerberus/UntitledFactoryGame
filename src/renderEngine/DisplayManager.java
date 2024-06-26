package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import toolbox.Logger;

public class DisplayManager {
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;
	private static boolean FULLSCREEN = false;
	
	public static void createDisplay(){		
		ContextAttribs attribs = new ContextAttribs(3,2)
		.withForwardCompatible(true)
		.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Untitled Factory Game");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0,0, WIDTH, HEIGHT);
	}
	
	public static void updateDisplay() throws LWJGLException {

		if(Keyboard.isKeyDown(Keyboard.KEY_F11)) {
			FULLSCREEN =! FULLSCREEN;
			Logger.info("DisplayManager", "Toggled Fullscreen value is " + FULLSCREEN);
		}

        Display.setFullscreen(FULLSCREEN);
		Display.sync(FPS_CAP);
		Display.update();


	}


	
	public static void closeDisplay(){
		
		Display.destroy();
		
	}

}
