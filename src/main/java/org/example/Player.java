package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Player
{
    float[] camera;

    int textureIndex = 0;
    Texture[] currentAnimation;
    Engine en;

    Texture[] idle, forward, right;
    PlayerAnimation ap;
    Player(Engine engine) throws IOException {
        en = engine;
        PlayerAnimation ep = new PlayerAnimation(en);
        ap = ep;
        currentAnimation = ap.idle;
        camera = new float[]{0,0};
    }





    float x, y;
    boolean s = false;
    boolean d = false;
    float movex, movey;
    public void update(List<Object[]> map)
    {
        movex = 0;
        movey = 0;
        camera[0] += (x - camera[0]) / 20;
        camera[1] += (y - camera[1]) / 20;

        if(glfwGetKey(en.window, GLFW_KEY_W) == GLFW_PRESS) {
            movey = 3;
            y += movey * en.deltaT;
            s = false;
            d = false;
        }
        if(glfwGetKey(en.window, GLFW_KEY_S) == GLFW_PRESS) {
            movey = -3;
            y += movey * en.deltaT;
            s = true;
            d = false;
        }
        if(glfwGetKey(en.window, GLFW_KEY_A) == GLFW_PRESS) {
            movex = -3;
            x += movex * en.deltaT;
            d = false;
            s = false;
        }
        if(glfwGetKey(en.window, GLFW_KEY_D) == GLFW_PRESS) {
            movex = 3;
            x += movex * en.deltaT;
            s = false;
            d = true;
        }

        move(movex, movey, map);


        if(s) {
            currentAnimation = ap.forward;
        } else if(d) {
            currentAnimation = ap.right;
        }else currentAnimation = ap.idle;

        if(textureIndex + 1 >= currentAnimation.length * 6) textureIndex = 0;
        currentAnimation[textureIndex / 6].render(x - camera[0], y - camera[1], 80, 115, false);
        textureIndex++;
    }


    private boolean aabb(float[] tile, float[] player)
    {
        if(tile[0] < player[0] + player[2] && tile[0] + tile[2] > player[1] && tile[1] < player[1] + player[3] && tile[1] + tile[3] > player[1])
        {
            return true;
        }
        return false;
    }



    private List<Object[]> getCollisions(List<Object[]> map)
    {
        List<Object[]> collidingTiles = new ArrayList<Object[]>();
        float[] playerBoundingBox = {x, y, 80, 115};
        for(Object[] pos:map)
        {
            float[] tileBoundingBox = new float[]{(float)pos[0], (float)pos[1], 64, 64};
            if(aabb(playerBoundingBox, tileBoundingBox))
            {
                collidingTiles.add(pos);
            }
        }
        return collidingTiles;
    }


    private void move(float mx, float my, List<Object[]> world) {

        ////////////////////////////// X
        x += mx;
        List<Object[]> collidingTiles = getCollisions(world);
        for (Object[] pos:collidingTiles) {
            if (mx > 0) { //right: x > 0 playerMovement: [-3, 0]
                x = (float)pos[0] - 65;
            }
            else if (mx < 0) { //left: x < 0
                x = (float)pos[0] + 65;
            }
        }
        ////////////////////////////// END OF X


        ///////////////////////////// Y
        y += my;
        collidingTiles = getCollisions(world);
        for (Object[] pos:collidingTiles) {
            if (my > 0) {
                y = (float)pos[1] - 65;
            }
            else if (my < 0) {
                y = (float)pos[1] + 65;
            }
        }
        ///////////////////////////// END OF Y
    }
}
