package org.java3d.gui;

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
    private int width = 240;
    private int height = 320;
    protected int button_width = 80;
    protected int button_height = 40;

    public Launcher(int id){
        try{
            // 현재 윈도우 상의 UI 모습을 연출할 수 있음.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            e.printStackTrace();
        }
        setTitle("Java3D Launcher");
        setSize(new Dimension(width, height));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(window); // add the panel
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        window.setLayout(null);
        if(id == 0){
            drawButtons();
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
