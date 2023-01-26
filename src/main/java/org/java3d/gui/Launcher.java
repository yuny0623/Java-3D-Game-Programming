package org.java3d.gui;

import org.java3d.Configuration;
import org.java3d.Display;
import org.java3d.RunGame;
import org.java3d.input.InputHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Launcher extends Canvas implements Runnable{
    private static final long serialVersionUID = 1L;
    protected JPanel window = new JPanel();
    private JButton play, options, help, quit;
    private Rectangle rplay, roptions, rhelp, rquit;
    Configuration config = new Configuration();
    private int width = 800;
    private int height = 400;
    protected int button_width = 80;
    protected int button_height = 40;
    boolean running = false;
    Thread thread;
    JFrame frame = new JFrame();

    public Launcher(int id){
        try{
            // 현재 윈도우 상의 UI 모습을 연출할 수 있음.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            e.printStackTrace();
        }

        frame.setUndecorated(true); // swing에서 제공되는 border테두리 등 기본 UI를 없앤다. // 테두리가 사라진다고 보면 된다.
        frame.setTitle("Java3D Launcher");
        frame.setSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // getContentPane().add(window); // add the panel
        frame.add(this);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        window.setLayout(null);
        if(id == 0){
            drawButtons();
        }
        InputHandler input = new InputHandler();
        addKeyListener(input);
        addFocusListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
        startMenu();
        frame.repaint(); // bug fix: button에 마우스를 올려야 보이는 버그를 해결할 수 있음.
    }

    public void updateFrame(){
        if(InputHandler.dragged){
            Point p = getLocation();
            System.out.println("x: " +  p.x + ", y: " + p.y);
            setLocation(p.x + InputHandler.MouseDX - InputHandler.MousePX, p.y + InputHandler.MouseDY - InputHandler.MousePY);
        }
    }

    private void drawButtons(){
        play = new JButton("Play");
        rplay = new Rectangle(width/2 - (button_width/2), 90, button_width, button_height);
        play.setBounds(rplay);
        window.add(play);

        options = new JButton("options");
        roptions = new Rectangle(width/2 - (button_width/2), 140, button_width, button_height);
        options.setBounds(roptions);
        window.add(options);

        help = new JButton("help");
        rhelp = new Rectangle(width/2 - (button_width/2), 190, button_width, button_height);
        help.setBounds(rhelp);
        window.add(help);

        quit = new JButton("quit");
        rquit = new Rectangle(width/2 - (button_width/2), 240, button_width, button_height);
        quit.setBounds(rquit);
        window.add(quit);

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.loadConfiguration("res/settings/config.xml");
                frame.dispose();
                new RunGame();
            }
        });
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Options();
            }
        });
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // normal exit
            }
        });
    }

    public void startMenu(){
        running = true;
        thread = new Thread(this, "menu");
        thread.start();
    }

    public void stopMenu(){
        try {
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        requestFocus();
        while(running){
            try {
                renderMenu();
                updateFrame();
            }catch(IllegalStateException e){
                System.out.println("Handled.");
            }
        }
    }

    private void renderMenu() throws IllegalStateException{
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3); // because we work in 3 dimension
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 400);
        try {
            g.drawImage(ImageIO.read(new FileInputStream("res/textures/menu.png")), 0, 0, 800, 400, null);
            if(InputHandler.MouseX > 690 && InputHandler.MouseX < 690 + 80 && InputHandler.MouseY > 130 && InputHandler.MouseY < 130 + 30) {
                g.drawImage(ImageIO.read(new FileInputStream("res/textures/play_on.png")), 690, 130, 80, 30, null);
                g.drawImage(ImageIO.read(new FileInputStream("res/textures/arrow2.png")), 690 + 80, 134, 22, 20, null);
                if(InputHandler.MouseButton == 1){
                    config.loadConfiguration("res/settings/config.xml");
                    frame.dispose();
                    new RunGame();
                }
            }else{
                g.drawImage(ImageIO.read(new FileInputStream("res/textures/play.png")), 690, 130, 80, 30, null);
            }
            if(InputHandler.MouseX > 690 && InputHandler.MouseX < 690 + 80 && InputHandler.MouseY > 170 && InputHandler.MouseY < 170 + 30) {
                g.drawImage(ImageIO.read(new FileInputStream("res/textures/options_on.png")), 690, 170, 80, 30, null);
                g.drawImage(ImageIO.read(new FileInputStream("res/textures/arrow2.png")), 690 + 80, 174, 22, 20, null);
                if(InputHandler.MouseButton == 1){
                    new Options();
                }
            }else{
                g.drawImage(ImageIO.read(new FileInputStream("res/textures/options.png")), 690, 170, 80, 30, null);
            }
            if(InputHandler.MouseX > 690 && InputHandler.MouseX < 690 + 80 && InputHandler.MouseY > 210 && InputHandler.MouseY < 210 + 30) {
                g.drawImage(ImageIO.read(new FileInputStream("res/textures/help_on.png")), 690, 210, 80, 30, null);
                g.drawImage(ImageIO.read(new FileInputStream("res/textures/arrow2.png")), 690 + 80, 214, 22, 20, null);
                if(InputHandler.MouseButton == 1){
                    System.out.println("Help!");
                }
            }else{
                g.drawImage(ImageIO.read(new FileInputStream("res/textures/help.png")), 690, 210, 80, 30, null);
            }
            if(InputHandler.MouseX > 690 && InputHandler.MouseX < 690 + 80 && InputHandler.MouseY > 250 && InputHandler.MouseY < 250 + 30) {
                g.drawImage(ImageIO.read(new FileInputStream("res/textures/quit_on.png")), 690, 250, 80, 30, null);
                g.drawImage(ImageIO.read(new FileInputStream("res/textures/arrow2.png")), 690 + 80, 254, 22, 20, null);
                if(InputHandler.MouseButton == 1){
                    System.exit(0);
                }
            }else{
                g.drawImage(ImageIO.read(new FileInputStream("res/textures/quit.png")), 690, 250, 80, 30, null);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        g.dispose();
        bs.show();
    }
}
