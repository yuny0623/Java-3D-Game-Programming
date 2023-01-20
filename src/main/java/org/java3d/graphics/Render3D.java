package org.java3d.graphics;

import org.java3d.Game;
import org.java3d.input.Controller;

public class Render3D extends Render{
    public double[] zBuffer;  // depth buffer for distance gradient
    private double renderDistance = 5000;

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

        double forward = game.controls.z;
        double right = game.controls.x;
        double up = game.controls.y; // 점프 구현
        double walking = Math.sin(game.time / 6.0) * 0.5; // 걷는 효과를 준다. 화면이 살짝 흔들리는 효과
        if(Controller.crouchWalk){
            walking = Math.sin(game.time / 6.0) * 0.25; // 앉아있는 상태로 걷는 동안에는 걷는 효과를 살짝 감소시킨다. 즉 화면흔들림을 줄인다.
        }
        if(Controller.runWalk){
            walking = Math.sin(game.time / 6.0) * 0.8; // 뛰는 동안에는 걷기 효과를 좀 더 증가시킨다. 화면이 더 흔들리게 만든다.
        }

        double rotation = game.controls.rotation;
        double cosine = Math.cos(rotation);
        double sine = Math.sin(rotation);

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
