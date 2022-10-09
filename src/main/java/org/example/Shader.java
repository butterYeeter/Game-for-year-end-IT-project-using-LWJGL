package org.example;

//import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Shader 
{    
    int shaderProgram;

    Shader(String vSrc, String fSrc)
    {
        String vPath = "", fPath = "";
        
        try {
            vPath = loadAsString(vSrc);
            fPath = loadAsString(fSrc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println(fPath);
        int vShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vShader, vPath);
		glCompileShader(vShader);

		int fShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fShader, fPath);
		glCompileShader(fShader);

		shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vShader);
        glAttachShader(shaderProgram, fShader);
        glLinkProgram(shaderProgram);
    }

    public static String loadAsString(String filePath) throws IOException {
        Path fileName = Path.of(filePath);
        String str = Files.readString(fileName);
        return str;
    }

    public void uploadTex(String varName)
    {
        int location = glGetUniformLocation(shaderProgram, varName);
        glUseProgram(shaderProgram);
        glUniform1i(location, 1);
    }
}
