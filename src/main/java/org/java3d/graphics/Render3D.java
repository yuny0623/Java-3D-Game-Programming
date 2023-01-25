package org.java3d.graphics;

import org.java3d.Game;
import org.java3d.input.Controller;

import java.util.Random;

public class Render3D extends Render{
    public double[] zBuffer;  // depth buffer for distance gradient
    private double renderDistance = 5000;
    private double forward, right, up, cosine, sine;

    public Render3D(int width, int height) {
        super(width, height);
        zBuffer = new double[width * height];
    }

    /*
        핵심 code
    */
    public void floor(Game game){
        double floorPosition = 8;
        double ceilingPosition = 8;
        // double ceilingPosition = Math.sin(game.time / 10) + 10; // 이렇게 하면 천장이 움직이는 연출을 할 수 있다. 해당 연출은 바닥에도 적용할 수 있다.
        // double ceilingPosition = 800; // 이 값을 800 정도로 하면 천장이 사라지는데 그러면 sky 연출을 할 수 있다.

        forward = game.controls.z;
        right = game.controls.x;
        up = game.controls.y; // 점프 구현
        double walking = Math.sin(game.time / 6.0) * 0.5; // 걷는 효과를 준다. 화면이 살짝 흔들리는 효과
        if(Controller.crouchWalk){
            walking = Math.sin(game.time / 6.0) * 0.25; // 앉아있는 상태로 걷는 동안에는 걷는 효과를 살짝 감소시킨다. 즉 화면흔들림을 줄인다.
        }
        if(Controller.runWalk){
            walking = Math.sin(game.time / 6.0) * 0.8; // 뛰는 동안에는 걷기 효과를 좀 더 증가시킨다. 화면이 더 흔들리게 만든다.
        }

        // double rotation = 0;
        double rotation = Math.sin(game.time / 40.8) * 0.5;
        // double rotation = game.controls.rotation;
        // double rotation = game.controls.rotation;

        cosine = Math.cos(rotation);
        sine = Math.sin(rotation);

        for(int y = 0; y < height; y++){
            double ceiling = (y + - height / 2.0) / height;

            double z = (floorPosition + up) / ceiling; // 점프한 높이만큼 바닥에서 더해주기
            if(Controller.walk){
                z = (floorPosition + up + walking) / ceiling;
            }
            if (ceiling < 0){
                z = (ceilingPosition - up) / -ceiling; // 점프한 만큼 천장에서 빼주기
                if (Controller.walk){
                    z = (ceilingPosition - up - walking) / -ceiling;
                }
            }

            for(int x = 0; x < width; x++){
                double depth = (x - width / 2.0) / height;
                depth *= z;
                double xx = (depth) * cosine + z * sine;
                double yy = z * cosine - depth * sine;
                int xPix= (int) (xx+ right);
                int yPix = (int) (yy + forward);
                zBuffer[x + y * width] = z;
                //pixels[x + y * width] = ((xPix & 15) * 16) | ((yPix & 15) * 16) << 8;    // 기본값 사용하기
                pixels[x + y * width] = Texture.floor.pixels[(xPix & 7) + (yPix & 7) * 8]; // 텍스쳐 적용하기
                if (z > 500){
                    pixels[x + y * width] = 0;
                }
            }
        }
    }

    public void renderWall(double xLeft, double xRight, double zDistance, double yHeight){
        double xcLeft = ((xLeft) - right) * 2;
        double zcLeft = ((zDistance) - forward) * 2;

        double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
        double yCornerTL = ((-yHeight) - up) * 2;
        double yCornerBL = ((+0.5 - yHeight) - up) * 2;
        double rotLeftSideZ = zcLeft  * cosine + xcLeft * sine;

        double xcRight = ((xRight) - right) * 2;
        double zcRight = ((zDistance) - forward) * 2;

        double rotRightSideX = xcRight * cosine - zcRight * sine;
        double yCornerTR = ((-yHeight) - up) * 2;
        double yCornerBR = ((+0.5 - yHeight) - up) * 2;
        double rotRightSideZ = zcRight * cosine + xcRight * sine;

        double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2);
        double xPixelRight = (rotRightSideX / rotRightSideZ * height + width / 2);

        if(xPixelLeft >= xPixelRight){
            return;
        }
        int xPixelLeftInt = (int) xPixelLeft;
        int xPixelRightInt = (int) xPixelRight;
        if (xPixelLeftInt < 0) {
            xPixelLeftInt = 0;
        }
        if(xPixelRight > width){
            xPixelRightInt = width;
        }

        double yPixelLeftTop  = (int) (yCornerTL / rotLeftSideZ * height + height / 2);
        double yPixelLeftBottom = (int) (yCornerBL)  / rotLeftSideZ * height + height / 2;
        double yPixelRightTop = (int) yCornerTR / rotRightSideZ * height + height / 2;
        double yPixelRightBottom = (int) yCornerBL / rotRightSideZ * height + height / 2;

        for(int x = xPixelLeftInt; x < xPixelRightInt; x ++){
            double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);

            double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
            double yPixelBottom = yPixelLeftBottom  + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

            int yPixelTopInt = (int) yPixelTop;
            int yPixelBottomInt = (int) yPixelBottom;

            if(yPixelTopInt < 0){
                yPixelTopInt = 0;
            }
            if(yPixelTopInt > height){
                yPixelTopInt = height;
            }

            for(int y = yPixelTopInt; y < yPixelBottomInt; y++){
                try {
                    pixels[x + y * width] = 0xfffff;
                }catch(ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                    continue;
                }
                zBuffer[x + y * width] = 0;
            }
        }
    }


    public void renderDistanceLimiter(){
        for(int i = 0; i < width * height; i++){
            int colour = pixels[i];
            int brightness = (int) (renderDistance / (zBuffer[i]));
            if (brightness < 0){
                brightness = 0;
            }
            if(brightness > 255){
                brightness = 255;
            }

            int r = (colour >> 16) & 0xff;
            int g = (colour >> 8) & 0xff;
            int b = (colour) & 0xff;

            r = r * brightness / 255;
            g = g * brightness / 255;
            b = b * brightness / 255;

            pixels[i] = r << 16 | g << 8 | b;
         }
    }
}
