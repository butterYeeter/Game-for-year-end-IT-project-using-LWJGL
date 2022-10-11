package org.example;

import java.io.IOException;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class Main
{
    public void run() throws IOException {
        Engine en = new Engine();
        en.init(800, 800);

		Texture gun = new Texture("/home/scriptline/Game-for-year-end-IT-project-using-LWJGL/src/main/resources/player_walk1.png", en);
		Texture gun2 = new Texture("/home/scriptline/Game-for-year-end-IT-project-using-LWJGL/src/main/resources/texture.png", en);
		Texture gun3 = new Texture("/home/scriptline/Game-for-year-end-IT-project-using-LWJGL/src/main/resources/texture.png", en);

		while ( !glfwWindowShouldClose(en.window) ) {
			glClearColor(0.2f, 0.0f, 1.0f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			
			gun.render(0, 0, 400, 400);
			glfwSwapBuffers(en.window);
			glfwPollEvents();
		}
		glfwDestroyWindow(en.window);
		glfwTerminate();
    }
    public static void main(String args[]) throws IOException
    {
        new Main().run();
    }
}
