package org.java3d.input;

public class Controller {
    public double x, y, z, rotation, xa, za, rotationa;
    public static boolean turnLeft = false;
    public static boolean turnRight = false;
    public static boolean walk = false;
    public static boolean crouchWalk = false;
    public static boolean runWalk =  false;


    public void tick(boolean forward, boolean back, boolean left, boolean right, boolean jump, boolean crouch, boolean run){
        double rotationSpeed = 0.025; // 좌우 회전 속도 제어
        double walkSpeed = 0.5;     // 평균 걷기 속도
        double jumpHeight = 0.5;    // 점프 높이 제어
        double crouchHeight = 0.33; // 앉았을때의 높이 제어
        double xMove = 0;
        double zMove = 0;

        if(forward){
            zMove++;
            walk = true;
        }
        if(back){
            zMove--;

            walk = true;
        }
        if(left){
            xMove--;

            walk = true;
        }
        if(right){
            xMove++;

            walk = true;
        }
        if(turnLeft){
            rotationa -= rotationSpeed;
        }
        if(turnRight){
            rotationa += rotationSpeed;
        }
        if(jump){
            y += jumpHeight;
            run = false; // 점프 중일때는 달릴 수 없다.
        }
        if(crouch){ // 앉기
            y -= crouchHeight;
            run = false; // 앉아있는 동안에는 달릴 수 없다.
            crouchWalk = true;
        }
        if(run){
            walkSpeed = 1;
            walk = true;
            runWalk = true;
        }
        if(!forward && !back && !left && !right){
            walk = false;
        }
        if(!crouch){
            crouchWalk = false;
        }
        if(!run){
            runWalk = false;
        }

        xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
        za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;

        x += xa;
        y *= 0.9;
        z += za;
        za *= 0.1;
        xa *= 0.1;
        rotation += rotationa;
        rotationa *= 0.05;
    }
}
