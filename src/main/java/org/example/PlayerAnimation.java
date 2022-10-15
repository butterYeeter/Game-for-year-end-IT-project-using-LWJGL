package org.example;

import java.io.IOException;

public class PlayerAnimation {

    Texture[] idle, forward, right;
    PlayerAnimation(Engine en) throws IOException
    {
        idle = new Texture[]{
                new Texture("src/main/resources/assets/idle01.png", en),
                new Texture("src/main/resources/assets/idle02.png", en),
                new Texture("src/main/resources/assets/idle03.png", en)
        };
        forward = new Texture[]{
                new Texture("src/main/resources/assets/walkS01.png", en),
                new Texture("src/main/resources/assets/walkS02.png", en),
                new Texture("src/main/resources/assets/walkS03.png", en),
                new Texture("src/main/resources/assets/walkS04.png", en),
                new Texture("src/main/resources/assets/walkS05.png", en),
                new Texture("src/main/resources/assets/walkS06.png", en)
        };

        right = new Texture[]{
                new Texture("src/main/resources/assets/walkR01.png", en),
                new Texture("src/main/resources/assets/walkR02.png", en),
                new Texture("src/main/resources/assets/walkR03.png", en),
                new Texture("src/main/resources/assets/walkR04.png", en),
                new Texture("src/main/resources/assets/walkR05.png", en),
                new Texture("src/main/resources/assets/walkR06.png", en)
        };
    }
}
