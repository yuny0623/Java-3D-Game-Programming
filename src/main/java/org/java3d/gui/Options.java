package org.java3d.gui;

import org.java3d.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Options extends Launcher{
    private static final long serialVersionUID = 1L;
    private Choice resolution = new Choice();
    private int width = 550;
    private int height = 450;
    private JButton OK;
    private Rectangle rOK, rresolution;

    public Options(){
        super(1);
        setTitle("Options - Java3D Launcher");
        setSize(new Dimension(width, height));
        setLocationRelativeTo(null);

        drawButtons();
    }

    private void drawButtons(){
        OK = new JButton("OK");
        rOK = new Rectangle(width - 100, height - 70, button_width, button_height  - 10);
        OK.setBounds(rOK);
        window.add(OK);

        rresolution = new Rectangle(50, 80, 80, 25);
        resolution.setBounds(rresolution);
        resolution.add("640, 480");
        resolution.add("800, 600");
        resolution.add("1024, 768");
        resolution.select(1);
        window.add(resolution);

        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.selection = resolution.getSelectedIndex(); // 선택된 index를 get
                dispose();
                new Launcher(0);
            }
        });
    }
}
