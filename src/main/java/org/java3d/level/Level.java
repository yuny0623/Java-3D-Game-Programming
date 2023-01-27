package org.java3d.level;

import org.java3d.graphics.Sprite;

import java.util.Random;

public class Level {
    public Block[] blocks;
    public final int width, height;

    public Level(int width, int height){
        this.width = width;
        this.height = height;
        blocks = new Block[width * height];
        Random random = new Random();
        for(int y = 0; y < height; y ++){
            for(int x = 0; x < width; x++){
                Block block = null;
                if(random.nextInt(10) == 0){ // 이 nextInt의 bound값을 크게 하면 block들이 좀 더 널찍하게 생성됨.
                    block = new SolidBlock();
                }else{
                    block = new Block();
                    if(random.nextInt(5) == 0) {
                        block.addSprite(new Sprite(0, 0, 0));
                    }
                }
                blocks[x + y * width] = block;
            }
        }
    }

    public Block create(int x, int y){
        if(x < 0 || y < 0 || x >= width || y >= height){
            return Block.solidWall;
        }
        return blocks[x + y * width];
    }
}

