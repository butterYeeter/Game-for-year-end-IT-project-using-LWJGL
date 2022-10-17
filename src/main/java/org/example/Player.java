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
    boolean flip = false;
    float movex, movey;
    float[] rect1 = {0, 0, 400, 400};
    float[] rect2 = new float[]{0, 0, 400, 400};
    public void update(List<Object[]> map)
    {
        movex = 0;
        movey = 0;
        camera[0] += (x - camera[0]) / 20;
        camera[1] += (y - camera[1]) / 20;

        if(glfwGetKey(en.window, GLFW_KEY_W) == GLFW_PRESS) {
            movey = 12;
            s = false;
            d = false;
            flip = false;
        }
        if(glfwGetKey(en.window, GLFW_KEY_S) == GLFW_PRESS) {
            movey = -12;
            s = true;
            d = false;
            flip = false;
        }
        if(glfwGetKey(en.window, GLFW_KEY_A) == GLFW_PRESS) {
            movex = -12;
            d = true;
            s = false;
            flip = true;
        }
        if(glfwGetKey(en.window, GLFW_KEY_D) == GLFW_PRESS) {
            movex = 12;
            s = false;
            d = true;
            flip = false;
        }

        if(glfwGetKey(en.window, GLFW_KEY_D) == 0 &&
                glfwGetKey(en.window, GLFW_KEY_W) == 0 &&
                glfwGetKey(en.window, GLFW_KEY_S) == 0 &&
                glfwGetKey(en.window, GLFW_KEY_A) == 0){
            s = false;
            d = false;
        }

        //rect1[0] -= 1f;
        move(movex, movey, map);

        if(s) {
            currentAnimation = ap.forward;
        } else if(d) {
            currentAnimation = ap.right;
        }else currentAnimation = ap.idle;

        if(textureIndex + 1 >= currentAnimation.length * 6) textureIndex = 0;
        currentAnimation[textureIndex / 6].render(x - camera[0], y - camera[1], 85 / 1.2f, 135 / 1.2f, flip);
        textureIndex++;
    }


    private boolean aabb(float[] tile, float[] player)
    {
        float rect1_x = player[0];
        float rect1_y = player[1];
        float rect1_w = player[2];
        float rect1_h = player[3];
        
        float rect2_x = tile[0];
        float rect2_y = tile[1];
        float rect2_w = tile[2];
        float rect2_h = tile[3];

        if (rect1_x < rect2_x + rect2_w &&
            rect1_x + rect1_w > rect2_x &&
            rect1_y < rect2_y + rect2_h &&
            rect1_h + rect1_y > rect2_y
        ) {

            return true;
        }
        
        return false;
    }



    private List<Object[]> getCollisions(List<Object[]> map)
    {
        List<Object[]> collidingTiles = new ArrayList<Object[]>();
        float[] playerBoundingBox = {x, y, 85, 135};
        for(Object[] pos:map)
        {
            float[] tileBoundingBox = new float[]{(float)pos[0], (float)pos[1] , 64, 64};
            if(aabb(playerBoundingBox, tileBoundingBox) && !(pos[2].equals("tile22.png")) && !(pos[2].equals("tile21.png")))
            {   
                collidingTiles.add(pos);
            }
        }
        return collidingTiles;
    }


    private void move(float mx, float my, List<Object[]> world) {

        x += mx;
        List<Object[]> collidingTiles = getCollisions(world);
//        System.out.println(collidingTiles.size());
        for (Object[] pos:collidingTiles) {
            if (mx > 0) { //right: x > 0 playerMovement: [-3, 0]
                x = (float)pos[0] - 90;
            }
            else if (mx < 0) { //left: x < 0
                x = (float)pos[0] + 74;
            }
        }


        y += my;
        collidingTiles = getCollisions(world);
        for (Object[] pos:collidingTiles) {
            if (my > 0) {
                y = (float)pos[1] - 135;
            }
            else if (my < 0) {
                y = (float)pos[1] + 64;
            }
        }
    }
}
