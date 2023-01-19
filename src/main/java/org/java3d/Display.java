package org.java3d;

import javax.swing.*;
import java.awt.*;

public class Display extends Canvas {
    private static final Long serialVersionUID = 1L;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static final String TITLE = "java3d 0.01";

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
    }
}
