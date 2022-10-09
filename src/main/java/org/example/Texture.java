package org.example;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.*;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImageResize.*;

public class Texture 
{
    String vPath = "/home/adminq/Documents/Game/src/main/resources/vertex.glsl";
	String fPath = "/home/adminq/Documents/Game/src/main/resources/fragment.glsl";
    Shader s;
    Texture(String imgPath) throws IOException
    {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        s = new Shader(vPath, fPath);
        glGenTextures();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer image = stbi_load(imgPath, width, height, channels, 0);
        if (image != null) {
            if (channels.get(0) == 3) 
            {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
            } else if (channels.get(0) == 4) 
            {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0),0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            } else 
            {
                assert false : "Error: (Texture) Unknown number of channesl '" + channels.get(0) + "'";
            }
        } else {
            assert false : "Error: (Texture) Could not load image '" + imgPath + "'";
        }

        s.uploadTex("tex0");
    }

    public void render()
    {
        float x = -0.4f, y = -0.7f;
        int height = 800, width = 800;


        float vertices[] = {
                x,                 y + (height / 800), 0.0f,           0.0f, 0.0f, 0.0f, 0.0f,           0.0f, 0.0f,
                x,                 y,                  0.0f,           0.0f, 0.0f, 0.0f, 0.0f,           0.0f, 1.0f,
                x + (width / 800), y,                  0.0f ,          0.0f, 0.0f, 0.0f, 0.0f,           1.0f, 1.0f,
                x + (width / 800), y + (height / 800), 0.0f,           0.0f, 0.0f, 0.0f, 0.0f,           1.0f, 0.0f
        };

        int indices[] = {0, 2, 1, 0, 3, 2};

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


        glVertexAttribPointer(0, 3, GL_FLOAT, false, 36, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 36, 12);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 36, 24);
        glEnableVertexAttribArray(2);


        glUseProgram(s.shaderProgram);
        glBindTexture(GL_TEXTURE_2D, 1);

        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
    }
}
