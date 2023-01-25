package org.java3d.graphics;

import org.java3d.Game;

import java.util.Random;

public class Screen extends Render{

    private Render test;
    private Render3D render;

    public Screen(int width, int height){
        super(width, height);
        Random random =  new Random();
        render = new Render3D(width, height);
        test = new Render(256, 256);
        for(int i =0 ; i < 256 * 256; i++){
            test.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);
        }
    }

    public void render(Game game){
        for(int i = 0 ; i < width * height; i++){
            pixels[i] = 0; // 화면에 도형을 그리고 난 후의 잔상을 지운다.
        }
//        for(int i = 0; i < 50; i++) {
//            int anim = (int) (Math.sin((game.time + i) % 1000.0 / 100) * 100);
//            int anim2 = (int) (Math.cos((game.time + i) % 1000.0 / 100) * 100);
//            // draw(test, (width - 256) / 2 + anim, (height - 256) / 2 + anim2);
//        }

        render.floor(game);
        /*
        render.renderWall(0, 0.5, 1.5,1.5, 0); // 이건 항상 renderDistance 전에 호출해야 한다.
        render.renderWall(0, 0, 1,1.5, 0); // 이건 항상 renderDistance 전에 호출해야 한다.
        render.renderWall(0, 0.5, 1,1, 0); // 이건 항상 renderDistance 전에 호출해야 한다.
        render.renderWall(0.5, 0.5, 1,1.5, 0); // 이건 항상 renderDistance 전에 호출해야 한다.
        */
        render.renderDistanceLimiter(); // 먼 곳이 어둡게 만들어줌.
        // render.walls(); // 벽 생성은 render distance 보다 이후에 선언되야만 render distance가 적용되지 않는다.
        // render.renderWall(0, 0.5, 1.5, 0);
        draw(render, 0, 0);
    }
}
