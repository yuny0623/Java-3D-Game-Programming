package org.java3d;

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
    public static final String TITLE = "java3d 0.02";

    private Thread thread;
    private boolean running = false;
    private Screen screen;
    private Game game;
    private BufferedImage img;
    private int[] pixels;

    public Display(){
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        screen = new Screen(WIDTH, HEIGHT);
        game = new Game();
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
        int frames = 0; // amount of frames
        double unprocessedSeconds = 0;
        long previousTime = System.nanoTime();
        double secondsPerTick = 1 / 60.0;
        int tickCount = 0;
        boolean ticked = false;

        while(running){
            // fps counter
            long currentTime = System.nanoTime();
            long passedTime = currentTime - previousTime;
            previousTime = currentTime;
            unprocessedSeconds += passedTime / 1000000000.0;

            while(unprocessedSeconds > secondsPerTick){
                tick();
                unprocessedSeconds -= secondsPerTick;
                ticked = true;
                tickCount++;
                if(tickCount % 60 == 0){
                    System.out.println(frames + "fps");
                    previousTime += 1000;
                    frames = 0;
                }
            }
            if(ticked){
                render();
                frames++;
            }
            render();
            frames++;
        }
    }

    // handle time. frame extra...
    private void tick(){
        game.tick();
    }

    // rendering
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3); // because 3 dimension
            return;
        }

        screen.render(game);

        for(int i = 0; i < WIDTH * HEIGHT; i++){
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Display game = new Display();
        JFrame frame = new JFrame();

        frame.add(game);
        // frame.pack(); // border 에러 때문에 pack()은 setResizable 이후에 와야함.
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        System.out.println("Running...");

        game.start();
    }
}
