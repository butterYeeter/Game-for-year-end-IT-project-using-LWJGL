package org.example;

import java.io.IOException;
import static org.lwjgl.glfw.GLFW.*;

public class Player
{
    int textureIndex = 0;
    Texture[] currentAnimation;
    Engine en;

    Player(Engine engine) throws IOException {

        en = engine;

        Texture[] idle = {
                new Texture("src/main/resources/idle01.png", en),
                new Texture("src/main/resources/idle02.png", en),
                new Texture("src/main/resources/idle03.png", en)
        };

        currentAnimation = idle;



    }

    int x, y;

    public void update()
    {
        if(textureIndex + 1 >= currentAnimation.length * 12) textureIndex = 0;
        currentAnimation[textureIndex / 12].render(x, y, 80, 115);
        textureIndex++;

        if(glfwGetKey(en.window, GLFW_KEY_W) == GLFW_PRESS) y += 15;
        if(glfwGetKey(en.window, GLFW_KEY_S) == GLFW_PRESS) y -= 15;
        if(glfwGetKey(en.window, GLFW_KEY_A) == GLFW_PRESS) x -= 15;
        if(glfwGetKey(en.window, GLFW_KEY_D) == GLFW_PRESS) x += 15;
    }
}
