package org.java3d.Entity.mob;

import org.java3d.input.InputHandler;

public class Player extends Mob{
    public double y, rotation, xa, za, rotationa;
    public static boolean turnLeft = false;
    public static boolean turnRight = false;
    public static boolean walk = false;
    public static boolean crouchWalk = false;
    public static boolean runWalk =  false;
    private InputHandler input;

    public Player(InputHandler input){
        this.input = input;
    }

    public void tick(){
        // double rotationSpeed = 0.002 * Display.MouseSpeed; // 좌우 회전 속도 제어 -> 마우스 속도에 가속도를 준다. 마우스가 빨리 움직일 수록 실제 화면 회전도 빠르게 움직이게 한다.
        double rotationSpeed = 0.03; // 방향키 회전 제어
        double walkSpeed = 0.5;     // 평균 걷기 속도
        double jumpHeight = 0.5;    // 점프 높이 제어
        double crouchHeight = 0.3; // 앉았을때의 높이 제어
        double xMove = 0;
        double zMove = 0;
        int xa = 0;
        int za = 0;
        if(input.forward){
            za ++;
            walk = true;
        }
        if(input.back){
            za --;
            walk = true;
        }
        if(input.left){
            xa --;
            walk = true;
        }
        if(input.right){
            xa ++;
            walk = true;
        }

        // rotation for key control
        if(input.rleft){
            rotation -= rotationSpeed;
        }
        if(input.rright){
            rotation += rotationSpeed;
        }

        rotation += rotationa;
        rotationa *= 0.5;

        if(xa != 0 || za != 0){
            move(xa, za, rotation);
        }

        /*
        // rotation for mouse control
        if(turnLeft){
            if(InputHandler.MouseButton == 3){

            }else {
                rotationa -= rotationSpeed;

            }
        }
        if(turnRight){
            if(InputHandler.MouseButton == 3){

            }else {
                rotationa += rotationSpeed;
            }
        }*/

        if(input.jump){
            y += jumpHeight;
            input.run = false; // 점프 중일때는 달릴 수 없다.
        }
        if(input.crouch){ // 앉기
            y -= crouchHeight;
            input.run = false; // 앉아있는 동안에는 달릴 수 없다.
            crouchWalk = true;
            walkSpeed = 0.2; // 앉아서 걸을때는 속도를 줄인다.
        }
        if(input.run){
            walkSpeed = 1;
            walk = true;
            runWalk = true;
        }
        if(!input.forward && !input.back && !input.left && !input.right){
            walk = false;
        }
        if(!input.crouch){
            crouchWalk = false;
        }
        if(!input.run){
            runWalk = false;
        }

        //      x += xa;
        y *= 0.9;
//        z += za;
//        za *= 0.1;
//        xa *= 0.1;
    }

    public void tick(boolean forward, boolean back, boolean left, boolean right, boolean rleft, boolean rright, boolean jump, boolean crouch, boolean run){

    }
}
