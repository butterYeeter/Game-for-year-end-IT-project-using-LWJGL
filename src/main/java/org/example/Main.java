package org.example;

import java.io.IOException;
import static org.lwjgl.glfw.GLFW.*;

public class Main
{
    public static void main(String args[]) throws IOException {
        Engine en = new Engine();
        en.init(800, 800);

		Player p1 = new Player(en);
		int x = 0, y = 0;
		while (en.windowOpen()) {
            en.clear(0.5f, 0.0f, 0.5f);
			p1.update();
            en.update();
		}
		glfwDestroyWindow(en.window);
		glfwTerminate();
    }
}
