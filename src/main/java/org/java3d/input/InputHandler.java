package org.java3d.input;

import java.awt.event.*;

public class InputHandler implements KeyListener, FocusListener, MouseListener, MouseMotionListener {

    public boolean[] key = new boolean[68836];
    public static int MouseX;
    public static int MouseY;
    public static int MouseDX; // d = drag
    public static int MouseDY;

    public static int MousePX; // p = pressed -> mouse pressed coordinates
    public static int MousePY;
    public static int MouseButton;
    public static boolean dragged = false;

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) { // 게임 창 밖으로 focus 가 나가지면 동작을 멈춘다.
        for(int i = 0; i < key.length; i++){
            key[i] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode > 0 && keyCode < key.length){
            key[keyCode] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { // 키가 떼지면 동작을 멈춘다.
        int keyCode = e.getKeyCode();
        if(keyCode > 0 && keyCode < key.length){
            key[keyCode] = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        MouseButton = e.getButton();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        MousePX = e.getX();
        MousePY = e.getY();
        dragged = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragged = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        MouseDX = e.getX();
        MouseDY = e.getY();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        MouseX = e.getX(); // getX()는 게임창에서의 상대좌표이고 getXOnScreen()은 전체 모니터 화면에서의 상대좌표이다.
        MouseY = e.getY();
    }
}
