package org.java3d;

import org.java3d.graphics.Render;
import org.java3d.graphics.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Display extends Canvas implements Runnable{
    private static final Long serialVersionUID = 1L;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "java3d 0.01";

    private Thread thread;
    private boolean running = false;
    private Screen screen;
    private BufferedImage img;
    private Render render;
    private int[] pixels;

    public Display(){
        screen = new Screen(WIDTH, HEIGHT);
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); // RGB로 세팅
        pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData(); // 버퍼를 통해 변환
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
            // tick(); // handle time. frame extra...
            render(); // rendering
        }
    }

    private void tick(){

    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3); // because 3 dimension
            return;
        }

        screen.render();

        for(int i = 0; i < WIDTH * HEIGHT; i++){
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();

        System.out.println("draw!");
        bs.show();
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
