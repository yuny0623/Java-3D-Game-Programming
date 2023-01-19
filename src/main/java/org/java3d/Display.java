package org.java3d;

import org.java3d.graphics.Render;

import javax.swing.*;
import java.awt.*;

public class Display extends Canvas implements Runnable{
    private static final Long serialVersionUID = 1L;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "java3d 0.01";

    private Thread thread;
    private boolean running = false;
    private Render render;

    public Display(){
        render = new Render(WIDTH, HEIGHT);
    }

    private void start(){
        if(running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();

        System.out.println("Working!");
    }

    private void stop(){
        if(!running){
            return;
        }
        running = false;
        try {
            thread.join();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }
    public void run(){
        while(running){
            tick(); // handle time. frame extra...
            render(); // rendering
        }
    }

    private void tick(){

    }
    private void render(){

    }

    public static void main(String[] args) {
        Display game = new Display();
        JFrame frame = new JFrame();

        frame.add(game);
        frame.pack();
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        System.out.println("Running...");

        game.start();
    }
}
