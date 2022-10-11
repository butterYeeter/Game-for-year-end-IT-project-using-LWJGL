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
        en.run();
    }
}
