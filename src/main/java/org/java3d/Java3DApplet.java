package org.java3d;

import java.applet.Applet;
import java.awt.*;

public class Java3DApplet extends Applet {
    private static final long serialVersionUID = 1L;

    private Display display = new Display();

    public void init(){
        setLayout(new BorderLayout());
        add(display); // embedding in browser
    }

    public void start(){
        display.start();
    }

    public void stop(){
        display.stop();
    }
}
