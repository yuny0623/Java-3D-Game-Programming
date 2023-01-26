package org.java3d.gui;

import org.java3d.Configuration;
import org.java3d.Display;
import org.java3d.RunGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Launcher extends JFrame {
    private static final long serialVersionUID = 1L;
    protected JPanel window = new JPanel();
    private JButton play, options, help, quit;
    private Rectangle rplay, roptions, rhelp, rquit;
    Configuration config = new Configuration();
    private int width = 800;
    private int height = 400;
    protected int button_width = 80;
    protected int button_height = 40;

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
        add(display);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        window.setLayout(null);
        if(id == 0){
            drawButtons();
        }
        display.start();
        repaint(); // bug fix: button에 마우스를 올려야 보이는 버그를 해결할 수 있음.
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
}
