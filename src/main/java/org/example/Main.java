package org.example;

import org.json.simple.parser.ParseException;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWImage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Main
{
	static 	Engine en = new Engine();
	public static void main(String args[]) throws IOException, ParseException, FileNotFoundException {
		double[] pos;
        en.init(600, 600);
		Player p1 = new Player(en);

		en.createCursor("src/main/resources/assets/cursor.png");

		List<Object[]> map = en.mapLoad(Json.parse());
		Texture square = new Texture("src/main/resources/assets/tile01.png", en);

		while (en.windowOpen()) {
			en.clear(0.0f, 0.0f, 0.0f);

			for(Object[] tile : map) {
				square.render((float)tile[0] - 2000 - p1.camera[0], (float)tile[1] + 1500 - p1.camera[1], 64, 64, false);
			}

			p1.update(map);
            en.update();
		}
		glfwDestroyWindow(en.window);
		glfwTerminate();
    }
}
