package org.java3d;

import org.java3d.graphics.Screen;
import org.java3d.gui.Launcher;
import org.java3d.input.Controller;
import org.java3d.input.InputHandler;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Display extends Canvas implements Runnable{
    private static final Long serialVersionUID = 1L;

    public static int width = 800;
    public static int height = 600;

    public static final String TITLE = "Java3D 0.02";

    private Thread thread;
    private boolean running = false;
    private Screen screen;
    private Game game;
    private BufferedImage img;
    private int[] pixels;

    private InputHandler input;

    private int newX = 0 ; // 마우스 좌표
    private int oldX = 0 ; // 마우스 이전 좌표
    private int fps;       // 프레임률
    public static int MouseSpeed;

    public static int selection = 0;
    static Launcher launcher;

    public Display(){
        Dimension size = new Dimension(getGameWidth(), getGameHeight());
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        screen = new Screen(getGameWidth(), getGameHeight());
        game = new Game();
        img = new BufferedImage(getGameWidth(), getGameHeight(), BufferedImage.TYPE_INT_RGB);   // RGB로 세팅
        pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData(); // 버퍼를 통해 변환
        input = new InputHandler();

        addKeyListener(input);
        addFocusListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
    }

    public static Launcher getLauncherInstance(){
        if(launcher == null){
            launcher = new Launcher(0);
        }
        return launcher;
    }

    public static int getGameWidth(){
        return width;
    }

    public static int getGameHeight(){
        return height;
    }



    public synchronized void start(){
        if(running) {
            return;
        }
        running = true;
        thread = new Thread(this, "game");
        thread.start();
    }

    public synchronized void stop(){
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
        long previousTime = System.nanoTime();
        double ns = 1000000000.0 / 60.0;
        double delta =0;
        int frames = 0; // amount of frames
        int updates = 0;
        long timer = System.currentTimeMillis();
        requestFocus(); // 마우스를 클릭하지 않고도 게임 창에 이미 진입하게 해줌. 미리 Focus를 맞춰줌.

        // fps counter
        while(running){
            long currentTime = System.nanoTime();
            delta += (currentTime - previousTime) / ns;
            previousTime = currentTime;

            if(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            while(System.currentTimeMillis() - timer > 1000) { // 1000 means 1 sec
                timer += 1000;
                System.out.println(updates + "ups, " + frames + "fps");
                fps = frames;
                frames = 0;
                updates = 0;
            }
        }
    }

    // handle time. frame extra...
    private void tick(){ // what tick method does updates our game.
        game.tick(input.key);

        newX = InputHandler.MouseX;
        if(newX > oldX){
            Controller.turnRight = true;
        }
        if(newX < oldX) {
            Controller.turnLeft = true;
        }
        if(newX == oldX){
            Controller.turnLeft = false;
            Controller.turnRight = false;
        }
        MouseSpeed = Math.abs(newX - oldX);
        oldX = newX;
    }


    // rendering
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3); // because we work in 3 dimension
            return;
        }

        screen.render(game);

        for(int i = 0; i < getGameWidth() * getGameHeight(); i++){
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, getGameWidth(), getGameHeight(), null);
        g.setFont(new Font("Verdana", 2, 40)); // style은 글자체를 의미한다. bold나 italic등등
        g.setColor(Color.WHITE); // 폰트의 색을 저장한다.
        g.drawString(fps + " FPS", 15, 50); // 글자를 그린다.
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        getLauncherInstance();
    }
}
