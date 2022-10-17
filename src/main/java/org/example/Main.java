package org.example;

import org.json.simple.parser.ParseException;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWImage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
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
		DoubleBuffer mX = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer mY = BufferUtils.createDoubleBuffer(1);


		List<Object[]> map = en.mapLoad(Json.parse("src/main/resources/assets/map1.json"));



		HashMap<String, Texture> wow = new HashMap<>();
		wow.put("tile01.png", new Texture("src/main/resources/assets/tile01.png", en));
		wow.put("tile02.png", new Texture("src/main/resources/assets/tile02.png", en));
		wow.put("tile03.png", new Texture("src/main/resources/assets/tile03.png", en));
		wow.put("tile04.png", new Texture("src/main/resources/assets/tile04.png", en));
		wow.put("tile05.png", new Texture("src/main/resources/assets/tile05.png", en));
		wow.put("tile06.png", new Texture("src/main/resources/assets/tile06.png", en));
		wow.put("tile07.png", new Texture("src/main/resources/assets/tile07.png", en));
		wow.put("tile08.png", new Texture("src/main/resources/assets/tile08.png", en));
		wow.put("tile09.png", new Texture("src/main/resources/assets/tile09.png", en));
		wow.put("tile10.png", new Texture("src/main/resources/assets/tile10.png", en));
		wow.put("tile11.png", new Texture("src/main/resources/assets/tile11.png", en));
		wow.put("tile12.png", new Texture("src/main/resources/assets/tile12.png", en));
		wow.put("tile13.png", new Texture("src/main/resources/assets/tile13.png", en));
		wow.put("tile14.png", new Texture("src/main/resources/assets/tile14.png", en));
		wow.put("tile15.png", new Texture("src/main/resources/assets/tile15.png", en));
		wow.put("tile16.png", new Texture("src/main/resources/assets/tile16.png", en));
		wow.put("tile17.png", new Texture("src/main/resources/assets/tile17.png", en));
		wow.put("tile18.png", new Texture("src/main/resources/assets/tile18.png", en));
		wow.put("tile19.png", new Texture("src/main/resources/assets/tile19.png", en));
		wow.put("tile20.png", new Texture("src/main/resources/assets/tile20.png", en));
		wow.put("tile21.png", new Texture("src/main/resources/assets/tile21.png", en));
		wow.put("tile22.png", new Texture("src/main/resources/assets/tile22.png", en));

		Texture bU = new Texture("src/main/resources/assets/button01.png", en);
		Texture bP = new Texture("src/main/resources/assets/button02.png", en);
		int count = 0;


		while (en.windowOpen()) {
			en.clear(0.0f, 0.0f, 0.0f);

			for(Object[] tile : map) {
//				System.out.println(tile[2]);
				wow.get(tile[2]).render((float)tile[0] - p1.camera[0], (float)tile[1] - p1.camera[1], 64, 64, false);
			}

			glfwGetCursorPos(en.window, mX, mY);
			if(glfwGetMouseButton(en.window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS && mX.get(0) <= 364 && mX.get(0) >= 225 && mY.get(0) <= 580 && mY.get(0) >= 530)
			{
				bP.render(-300, -1132, 256 / 0.9f, 88 / 0.9f, false);
				if(glfwGetMouseButton(en.window, GLFW_MOUSE_BUTTON_LAST)== GLFW_MOUSE_BUTTON_LEFT) count++;
				if(count % 2 == 0) {map = en.mapLoad(Json.parse("src/main/resources/assets/map1.json")); count = 0;} else map = en.mapLoad(Json.parse("src/main/resources/assets/map.json"));
//				System.out.printf("%.2f, %.2fwowo\n", mX.get(0), mY.get(0));
			} else {
				bU.render(-300, -1132, 256 / 0.9f, 88 / 0.9f, false);
//				System.out.printf("%.2f, %.2f\n", mX.get(0), mY.get(0));
			}
			p1.update(map);
            en.update();
		}
		glfwDestroyWindow(en.window);
		glfwTerminate();
    }
}
