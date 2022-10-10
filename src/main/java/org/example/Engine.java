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

	public void run() {
		textureIndex = 1;
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		try {
			render();
		} catch (IOException e) {
			e.printStackTrace();
		}

		glfwDestroyWindow(window);
		glfwTerminate();
	}

	void init() {

		if ( !glfwInit() )
		{
			System.out.println("Unable to initialize GLFW");
			System.exit(0);
		}

		// Configure GLFW
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);


		window = glfwCreateWindow(700, 700, "OOGA BOOGA", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true);
		});


		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);
	}

	private void render() throws IOException {
		GL.createCapabilities();

		/*float vertices[] = {
			0.5f, -0.5f, 0.0f,			1.0f, 0.0f, 0.0f,
			0.5f, 0.5f, 0.0f,			0.0f, 1.0f, 0.0f,
			-0.5f, 0.5f, 0.0f,			0.0f, 0.0f, 1.0f,
			-0.5f, -0.5f, 0.0f,			0.3f, 0.0f, 0.8f
		};

		int indices[] = {0, 1, 2, 0, 2, 3};
		

		String vPath = "/home/adminq/Documents/Game/src/main/resources/vertex.glsl";
		String fPath = "/home/adminq/Documents/Game/src/main/resources/fragment.glsl";

		Shader s = new Shader(vPath, fPath);

		int vbo, vao, ebo;

		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
		vertexBuffer.put(vertices).flip();
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
		indicesBuffer.put(indices).flip();

		ebo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 24, 0);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 24, 12);
		glEnableVertexAttribArray(1);


		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

		System.out.println(s.shaderProgram);*/

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
	}

	public static String loadAsString(String filePath) throws IOException {
        Path fileName = Path.of(filePath);
        String str = Files.readString(fileName);
        return str;
    }
}