package org.java3d;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RunGame {
    public RunGame(){
        // create new cursor for game play
        BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); // 투명한 마우스 커서를 만든다. TYPE_INT_RGB를 사용하면 검은색 커서가 나옴)
        Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");

        Display game = new Display();
        JFrame frame = new JFrame();
        frame.add(game);
        // frame.pack(); // border 에러 때문에 pack()은 setResizable 이후에 와야함.
        // frame.getContentPane().setCursor(blank); // custom했던 마우스 커서 적용

        frame.setTitle(Display.TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        game.start();
    }
}
