package org.java3d;

import org.java3d.input.Controller;
import org.java3d.level.Level;

import java.awt.event.KeyEvent;

public class Game {
    public int time;
    public Controller controls;
    public Level level;

    public Game(){
        controls = new Controller();
        level = new Level(80, 80);
    }

    public void tick(boolean[] key){
        time++;
        boolean forward = key[KeyEvent.VK_W];
        boolean back = key[KeyEvent.VK_S];
        boolean left = key[KeyEvent.VK_A];
        boolean right = key[KeyEvent.VK_D];

        boolean rright = key[KeyEvent.VK_RIGHT];
        boolean rleft = key[KeyEvent.VK_LEFT];

        boolean jump = key[KeyEvent.VK_SPACE];
        boolean crouch = key[KeyEvent.VK_CONTROL]; // 앉기
        boolean run = key[KeyEvent.VK_SHIFT];

        controls.tick(forward, back, left, right, rleft, rright, jump, crouch, run);
    }
}
