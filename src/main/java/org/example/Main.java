package org.example;

import java.io.IOException;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class Main
{
    public static void main(String args[]) throws IOException {
        Engine en = new Engine();
        en.init(800, 800);

		Texture gun = new Texture("src/main/resources/player_walk1.png", en);
		Texture gun2 = new Texture("src/main/resources/texture.png", en);
		Texture gun3 = new Texture("src/main/resources/player_walk1.png", en);

		while (en.windowOpen()) {
            en.clear(0.0f, 0.0f, 0.0f);

			gun.render(0, 0, 400, 400);
			gun2.render(100, 0, 400, 400);
			gun3.render(200, 0, 400, 400);

            en.update();
		}
		glfwDestroyWindow(en.window);
		glfwTerminate();
    }
}
