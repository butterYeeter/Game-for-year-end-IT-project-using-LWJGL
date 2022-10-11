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
	static long window;

	public int textureIndex;
	private int wWidth, wHeight;
	public void run() {
		textureIndex = 1;
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init(600, 600);
		try {
			render();
		} catch (IOException e) {
			e.printStackTrace();
		}

		glfwDestroyWindow(window);
		glfwTerminate();
	}

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
		// Enable v-sync
		glfwSwapInterval(1);
	}

	public  void render() throws IOException {


		Texture tex = new Texture("/home/adminq/Documents/Game/src/main/resources/maple.png", this);
		Texture text = new Texture("/home/adminq/Documents/Game/src/main/resources/texture.png", this);
		Texture gun = new Texture("/home/adminq/Documents/Game/src/main/resources/gun.png", this);

		while ( !glfwWindowShouldClose(window) ) {
			glClearColor(0.2f, 0.0f, 1.0f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			// glUseProgram(s.shaderProgram);
			// glBindVertexArray(vao);
			// glDrawElements(GL_TRIANGLES,6, GL_UNSIGNED_INT, 0);
			// /glDrawArrays(GL_TRIANGLES, 0, 3);
			tex.render(-100, 0, 1252, 486);
			text.render(-400, -400, 200, 200);
			gun.render(0, 0, 800, 800);
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	public static String loadAsString(String filePath) throws IOException {
        Path fileName = Path.of(filePath);
        String str = Files.readString(fileName);
        return str;
    }

	public static boolean shouldWindowClose()
	{
		return glfwWindowShouldClose(window);
	}
}