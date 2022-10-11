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

public class Texture 
{
    String vPath = "/home/scriptline/Game-for-year-end-IT-project-using-LWJGL/src/main/resources/vertex.glsl";
	String fPath = "/home/scriptline/Game-for-year-end-IT-project-using-LWJGL/src/main/resources/fragment.glsl";
    public Shader s;

    public int index;
    Texture(String imgPath, Engine engine) throws IOException {
        index = engine.textureIndex;
        engine.textureIndex += 1;

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        s = new Shader(vPath, fPath);

        int texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

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
        glActiveTexture(GL_TEXTURE0);
        s.uploadTex("tex0", 0);
    }

    public void render(float _x, float _y, float width, float height) {

        float x = _x / 400;
        float y = _y / 400;

        float vertices[] = {
                x,                 y + (height / 800), 0.0f,           0.0f, 0.0f, 0.0f, 0.0f,           0.0f, 0.0f,
                x,                 y,                  0.0f,           0.0f, 0.0f, 0.0f, 0.0f,           0.0f, 1.0f,
                x + (width / 800), y,                  0.0f ,          0.0f, 0.0f, 0.0f, 0.0f,           1.0f, 1.0f,
                x + (width / 800), y + (height / 800), 0.0f,           0.0f, 0.0f, 0.0f, 0.0f,           1.0f, 0.0f
        };

        int[] indices = {
                0, 2, 1,
                0, 3, 2,
        };

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(indices.length);
        elementBuffer.put(indices).flip();

        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        int positionsSize = 3;
        int colorSize = 4;
        int texCoordSize = 2;

        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize + colorSize + texCoordSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, texCoordSize, GL_FLOAT, false, vertexSizeBytes, (positionsSize + colorSize) * Float.BYTES);
        glEnableVertexAttribArray(2);

        glUseProgram(s.shaderProgram);
        glBindTexture(GL_TEXTURE_2D, index + 1);
        glBindVertexArray(vao);

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
    }
}
