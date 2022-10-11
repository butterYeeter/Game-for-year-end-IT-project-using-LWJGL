package org.example;

import java.io.IOException;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class Main
{
    public static void main(String args[]) throws IOException
    {
        Engine en = new Engine();
        en.init(800, 800);
       // en.render();
       // en.init(800, 800);
        //Texture tex = new Texture("/home/adminq/Documents/Game/src/main/resources/maple.png", en);
        Texture tex = new Texture("/home/adminq/Downloads/Game/src/main/resources/texture.png", en);

        //en.run();

        while ( !glfwWindowShouldClose(en.window) ) {
            glClearColor(0.2f, 0.0f, 1.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            // glUseProgram(s.shaderProgram);
            // glBindVertexArray(vao);
            // glDrawElements(GL_TRIANGLES,6, GL_UNSIGNED_INT, 0);
            // /glDrawArrays(GL_TRIANGLES, 0, 3);
            tex.render(-100, -100, 400, 400);
            //text.render(-400, -400, 200, 200);
            //gun.render(0, 0, 800, 800);
            glfwSwapBuffers(en.window);
            glfwPollEvents();
        }
    }
}
