package org.example;

import org.lwjgl.*;
import org.lwjgl.opengl.*;

import java.io.IOException;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;


public class Engine {

	// The window handle
	long window;

	public int textureIndex;
	private int wWidth, wHeight;

	void init(int wWidth, int wHeight)
	{
		this.wWidth = wWidth;
		this.wHeight = wHeight;
		if ( !glfwInit() )
		{
			System.out.println("Unable to initialize GLFW");
			System.exit(0);
		}

		// Configure GLFW
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);


		window = glfwCreateWindow(wWidth, wHeight  , "OOGA BOOGA", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true);
		});


		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		glfwSwapInterval(1);
	}

	public static String loadAsString(String filePath) throws IOException {
        Path fileName = Path.of(filePath);
        String str = Files.readString(fileName);
        return str;
    }

	public boolean windowOpen() {
		return !(glfwWindowShouldClose(window));
	}

	public void clear(float r, float g, float b) {
		glClearColor(r, g, b, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
	}

	public void update() {
		glfwSwapBuffers(window);
		glfwPollEvents();
	}
}