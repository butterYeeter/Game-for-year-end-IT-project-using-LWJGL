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
    String vPath = "src/main/resources/vertex.glsl";
	String fPath = "src/main/resources/fragment.glsl";
    public Shader s;
    Engine en;
    int vao, vbo, ebo;

    public int index;
    Texture(String imgPath, Engine engine) throws IOException {
        index = engine.textureIndex;
        en = engine;
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

        float x = 0;
        float y = 0;

        vertices = new float[]{
                x, y , 0.0f,                                 0.0f, 0.0f, 0.0f, 0.0f,           0.0f, 0.0f,
                x, y, 0.0f,                                                         0.0f, 0.0f, 0.0f, 0.0f,           0.0f, 1.0f,
                x, y, 0.0f ,                                  0.0f, 0.0f, 0.0f, 0.0f,           1.0f, 1.0f,
                x, y, 0.0f,           0.0f, 0.0f, 0.0f, 0.0f,           1.0f, 0.0f
        };


        int[] indices = {
                0, 2, 1,
                0, 3, 2,
        };

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();

         vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(indices.length);
        elementBuffer.put(indices).flip();

         ebo = glGenBuffers();
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


        glActiveTexture(GL_TEXTURE0);
        s.uploadTex("tex0", 0);
    }


    float vertices[];
    public void render(float _x, float _y, float width, float height, boolean flip) {

        float x = _x / en.wWidth / 2;
        float y = _y / en.wHeight / 2;

        vertices = new float[]{
                x, y + (height / en.wHeight), 0.0f,                                 0.0f, 0.0f, 0.0f, 0.0f,           0.0f, 0.0f,
                x, y, 0.0f,                                                         0.0f, 0.0f, 0.0f, 0.0f,           0.0f, 1.0f,
                x + (width / en.wWidth), y, 0.0f ,                                  0.0f, 0.0f, 0.0f, 0.0f,           1.0f, 1.0f,
                x + (width / en.wWidth), y + (height / en.wHeight), 0.0f,           0.0f, 0.0f, 0.0f, 0.0f,           1.0f, 0.0f
        };

        if(flip) {
            vertices = new float[] {
                    x, y + (height / en.wHeight), 0.0f,                                 0.0f, 0.0f, 0.0f, 0.0f,           1.0f, 0.0f,
                    x, y, 0.0f,                                                         0.0f, 0.0f, 0.0f, 0.0f,           1.0f, 1.0f,
                    x + (width / en.wWidth), y, 0.0f ,                                  0.0f, 0.0f, 0.0f, 0.0f,           0.0f, 1.0f,
                    x + (width / en.wWidth), y + (height / en.wHeight), 0.0f,           0.0f, 0.0f, 0.0f, 0.0f,           0.0f, 0.0f
            };
        }

        int[] indices = {
                0, 2, 1,
                0, 3, 2,
        };

        glBindVertexArray(vao);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(indices.length);
        elementBuffer.put(indices).flip();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        glUseProgram(s.shaderProgram);
        glBindTexture(GL_TEXTURE_2D, index + 1);

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
    }
}
