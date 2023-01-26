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
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLOutput;

public class Launcher extends JFrame implements Runnable{
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

    public Launcher(int id, Display display){
        try{
            // 현재 윈도우 상의 UI 모습을 연출할 수 있음.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            e.printStackTrace();
        }

        setUndecorated(true); // swing에서 제공되는 border테두리 등 기본 UI를 없앤다. // 테두리가 사라진다고 보면 된다.
        setTitle("Java3D Launcher");
        setSize(new Dimension(width, height));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // getContentPane().add(window); // add the panel
        // add(display);
        // add(this);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        window.setLayout(null);
        if(id == 0){
            drawButtons();
        }
        InputHandler input = new InputHandler();
        addMouseListener(input);
        addMouseMotionListener(input);

        startMenu();
        display.start();
        repaint(); // bug fix: button에 마우스를 올려야 보이는 버그를 해결할 수 있음.
    }

    public void updateFrame(){
        if(InputHandler.dragged){
            Point p = getLocation();
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
                dispose();
                new RunGame();
            }
        });
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
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
            renderMenu();
            updateFrame();
        }
    }

    private void renderMenu(){
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
        }catch (IOException e){
            e.printStackTrace();
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", 0, 20));
        g.drawString("Play", 700, 90);
        g.dispose();
        bs.show();
    }
}
