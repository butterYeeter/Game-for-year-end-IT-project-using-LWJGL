package org.example;

import org.json.simple.parser.ParseException;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWImage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Main
{
	static 	Engine en = new Engine();
	public static void main(String args[]) throws IOException, ParseException, FileNotFoundException {
		double[] pos;
        en.init(800, 600);
		Pet pet = new Pet(en);

		en.createCursor("src/main/resources/assets/cursor.png");
		DoubleBuffer mX = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer mY = BufferUtils.createDoubleBuffer(1);

		Texture background = new Texture("src/main/resources/assets/house1.png", en);

		Texture bU1 = new Texture("src/main/resources/assets/button01.png", en);
		Texture bP1 = new Texture("src/main/resources/assets/button02.png", en);

		double time = en.deltaT;
		double scale = 0.5;
		boolean button1;
		boolean leftclick;


		while (en.windowOpen()) {
			en.clear(0.0f, 0.0f, 0.0f);
			background.render(-1600, -1200, 1600, 1200,false);
			glfwGetCursorPos(en.window, mX, mY);
			leftclick = glfwGetMouseButton(en.window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS;
			button1 = leftclick && mX.get(0) < 224 && mX.get(0) > 100 && mY.get(0) < 542 && mY.get(0) > 506;

			System.out.printf("%.2f, %.2f\n", mX.get(0), mY.get(0));
			System.out.println(pet.hunger);

			pet.update();

			if(button1) {
				bP1.render(-1200, -1000, 324, 132, false);
				pet.hunger += 5;
			} else bU1.render(-1200, -1000, 324, 132, false);

            en.update();
		}
		glfwDestroyWindow(en.window);
		glfwTerminate();
    }
}
