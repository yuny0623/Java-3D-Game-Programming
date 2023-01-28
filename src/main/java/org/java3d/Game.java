package org.java3d;

import org.java3d.Entity.mob.Player;
import org.java3d.input.InputHandler;
import org.java3d.level.Level;

public class Game {
    public int time;
    public Player player;
    public Player[] players;
    public Level level;

    public Game(InputHandler input){
        player = new Player(input);
        level = new Level(80, 80);
        level.addEntity(player);
    }

    public void tick(){
        time++;
        level.tick();
    }
}
