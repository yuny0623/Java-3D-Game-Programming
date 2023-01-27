package org.java3d.level;

import org.java3d.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Block {

    public boolean solid = false;
    public static Block solidWall = new SolidBlock();

    public List<Sprite> sprites = new ArrayList<>();

    public void addSprite(Sprite sprite){
        sprites.add(sprite);
    }
}
