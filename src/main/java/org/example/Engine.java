package org.example;

import org.json.simple.JSONArray;
import org.lwjgl.*;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.system.MemoryUtil.*;



public class Engine {

	// The window handle
	long window;

	public int textureIndex;
	public int wWidth, wHeight;

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


	double crntTime = 0.0;
	double lastTime = 0.0;
	double deltaT;
	double fps;
	int counter;
	String title;
	public void update() {
		crntTime = glfwGetTime();
		deltaT = crntTime - lastTime;
		counter++;
		if(crntTime >= 1.0 / 30)
		{
			fps = 1.0 / deltaT * counter;
			//fps = Math.round((fps * 10000)) / 10000;
			title = "OOGA BOOGA\t" + fps;
			glfwSetWindowTitle(window, title);
			lastTime = crntTime;
			counter = 0;
		}
		glfwSwapBuffers(window);
		glfwPollEvents();
	}

	public void createCursor(String imgPath)
	{
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		ByteBuffer image = stbi_load(imgPath, width, height, channels, 0);

		GLFWImage glfwImage = new GLFWImage(BufferUtils.createByteBuffer(GLFWImage.SIZEOF));
		glfwImage.set(width.get(0), height.get(0), image);
		long cursor = glfwCreateCursor(glfwImage, 0, 0);
		glfwSetCursor(window, cursor);
	}

	public List<Object[]> mapLoad(JSONArray map)
	{
		List<Object[]> returnData = new ArrayList<Object[]>();

		Iterator iterator = map.iterator();
		while (iterator.hasNext()) {
			Object data = iterator.next();
			long x = (long)(((JSONArray)(data)).get(0));
			long y = (long)(((JSONArray)(data)).get(1));
			String name = (String)(((JSONArray)(data)).get(4));

			returnData.add(new Object[]{((float)x * 8) - 400, -(((float)y * 8) - 400), (String)name});
		}
		return returnData;
	}
}