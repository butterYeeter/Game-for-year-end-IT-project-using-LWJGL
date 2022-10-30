package org.example;

import java.io.IOException;

public class Pet
{
    float[] camera;

    int textureIndex = 0;
    Texture[] currentAnimation;
    Engine en;
    int hunger = 100;
    double time = 0;

    Texture[] idle;
    Pet(Engine engine) throws IOException {
        en = engine;
        idle = new Texture[]{
                new Texture("src/main/resources/assets/walkR01.png", en),
                new Texture("src/main/resources/assets/walkR02.png", en),
                new Texture("src/main/resources/assets/walkR03.png", en),
                new Texture("src/main/resources/assets/walkR04.png", en),
                new Texture("src/main/resources/assets/walkR05.png", en),
                new Texture("src/main/resources/assets/walkR06.png", en)
        };
        currentAnimation = idle;
    }

    float x, y = -1000;
    public void update()
    {
        if(x > 1600) x = -1800; else x += 10;
        if(textureIndex + 1 >= currentAnimation.length * 6) textureIndex = 0;
        currentAnimation[textureIndex / 6].render(x, y, 170, 270, false);
        textureIndex++;
        if(hunger > 0)
        {
            if(time > 0.3)
            {
                hunger -= 5;
                time = 0;
            } else time += en.deltaT;
        }

        if(hunger > 100) hunger = 100;
    }
}
