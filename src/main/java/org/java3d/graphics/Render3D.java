package org.java3d.graphics;

import org.java3d.Game;
import org.java3d.input.Controller;
import org.java3d.level.Block;
import org.java3d.level.Level;

import java.util.Random;

public class Render3D extends Render{
    public double[] zBuffer;  // depth buffer for distance gradient
    public double[] zBufferWall;
    private double renderDistance = 5000;
    private double forward, right, up, cosine, sine, walking;

    public Render3D(int width, int height) {
        super(width, height);
        zBuffer = new double[width * height];
        zBufferWall = new double[width];
    }

    /*
        핵심 code
    */
    public void floor(Game game){
        for(int x = 0; x < width; x ++){
            zBufferWall[x] = 0;
        }

        double floorPosition = 8;
        double ceilingPosition = 8;
        // double ceilingPosition = Math.sin(game.time / 10) + 10; // 이렇게 하면 천장이 움직이는 연출을 할 수 있다. 해당 연출은 바닥에도 적용할 수 있다.
        // double ceilingPosition = 800; // 이 값을 800 정도로 하면 천장이 사라지는데 그러면 sky 연출을 할 수 있다.

        forward = game.controls.z;
        right = game.controls.x;
        up = game.controls.y; // 점프 구현
        walking = 0;  // 초기화
        // double rotation = 0;
        // double rotation = Math.sin(game.time / 40.8) * 0.5;
        double rotation = game.controls.rotation;

        cosine = Math.cos(rotation);
        sine = Math.sin(rotation);

        for(int y = 0; y < height; y++){
            double ceiling = (y + - height / 2.0) / height;

            double z = (floorPosition + up) / ceiling; // 점프한 높이만큼 바닥에서 더해주기
            if(Controller.walk){
                walking = Math.sin(game.time / 6.0) * 0.5; // 걷는 효과를 준다. 화면이 살짝 흔들리는 효과
                z = (floorPosition + up + walking) / ceiling;
            }
            if(Controller.crouchWalk && Controller.walk){
                walking = Math.sin(game.time / 6.0) * 0.25; // 앉아있는 상태로 걷는 동안에는 걷는 효과를 살짝 감소시킨다. 즉 화면흔들림을 줄인다.
                z = (floorPosition + up + walking) / ceiling;
            }
            if(Controller.runWalk && Controller.walk){
                walking = Math.sin(game.time / 6.0) * 0.8; // 뛰는 동안에는 걷기 효과를 좀 더 증가시킨다. 화면이 더 흔들리게 만든다.
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
                pixels[x + y * width] = Texture.floor.pixels[(xPix & 7) + (yPix & 7) * 16]; // 텍스쳐 적용하기
                if (z > 500){
                    pixels[x + y * width] = 0;
                }
            }
        }

        Level level = game.level;
        int size = 50;
        // 1단 벽
        for(int xBlock = -size; xBlock <= size; xBlock++){
            for(int zBlock = -size; zBlock <= size; zBlock++){
                Block block = level.create(xBlock, zBlock);
                Block east = level.create(xBlock + 1, zBlock);
                Block south = level.create(xBlock, zBlock + 1);

                if(block.solid){
                    if(!east.solid){
                        renderWall(xBlock + 1, xBlock + 1, zBlock, zBlock + 1, 0);
                    }
                    if(!south.solid){
                        renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1, 0);
                    }
                }else{
                    if(east.solid){
                        renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock, 0);
                    }
                    if(south.solid){
                        renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1, 0);
                    }
                }
            }
        }

        // 2단 벽
        for(int xBlock = -size; xBlock <= size; xBlock++){
            for(int zBlock = -size; zBlock <= size; zBlock++){
                Block block = level.create(xBlock, zBlock);
                Block east = level.create(xBlock + 1, zBlock);
                Block south = level.create(xBlock, zBlock + 1);

                if(block.solid){
                    if(!east.solid){
                        renderWall(xBlock + 1, xBlock + 1, zBlock, zBlock + 1, 0.5);
                    }
                    if(!south.solid){
                        renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1, 0.5);
                    }
                }else{
                    if(east.solid){
                        renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock, 0.5);
                    }
                    if(south.solid){
                        renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1, 0.5);
                    }
                }
            }
        }

        for(int xBlock = -size; xBlock <= size; xBlock++) {
            for (int zBlock = -size; zBlock <= size; zBlock++) {
                Block block = level.create(xBlock, zBlock);
                for(int s = 0; s < block.sprites.size(); s++){
                    Sprite sprite = block.sprites.get(s);
                    renderSprite(xBlock+ sprite.x, sprite.y,zBlock + sprite.z);
                }
            }
        }
    }

    public void renderSprite(double x, double y, double z){
        double upCorrect = -0.125;
        double rightCorrect = 0.0625;
        double forwardCorrect = 0.0625;
        double walkCorrect = 0.0625;

        double xc = ((x / 2) - (right * rightCorrect)) * 2;
        double yc = ((y / 2) - (up * upCorrect))+ (walking * walkCorrect) * 2; // Sprites에도 걷기 연출 적용
        double zc = ((z / 2) - (forward * forwardCorrect)) * 2;

        double rotX = xc * cosine - zc * sine;
        double rotY = yc;
        double rotZ = zc * cosine + xc * sine;

        double xCenter = 400.0;
        double yCenter = 300.0;

        double xPixel = rotX / rotZ * height + xCenter;
        double yPixel = rotY / rotZ * height + yCenter;

        double xPixelL = xPixel - 16 / rotZ;
        double xPixelR = xPixel + 16 / rotZ;

        double yPixelL = yPixel - 16 / rotZ;
        double yPixelR = yPixel + 16 / rotZ;

        int xpl = (int) xPixelL;
        int xpr = (int) xPixelR;
        int ypl = (int) yPixelL;
        int ypr = (int) yPixelR;

        if(xpl < 0 )
            xpl = 0;
        if(xpr > width )
            xpr = width;
        if(ypl < 0 )
            ypl = 0;
        if(ypr > height )
            ypr = height;

        rotZ *= 8; // to make sprites more darker

        for(int yp = ypl; yp < ypr; yp++){
            for(int xp = xpl; xp < xpr; xp++){
                if(zBuffer[xp + yp * width] > rotZ){
                    pixels[xp + yp * width] = 0xFF0000;
                    zBuffer[xp + yp * width] = rotZ;
                }
            }
        }
    }

    public void renderWall(double xLeft, double xRight, double zDistanceLeft, double zDistanceRight, double yHeight){
        double upCorrect = 0.0625;
        double rightCorrect = 0.0625;
        double forwardCorrect = 0.0625;
        double walkCorrect = -0.0625;

        double xcLeft = ((xLeft / 2) - (right * rightCorrect)) * 2;
        double zcLeft = ((zDistanceLeft / 2) - (forward * forwardCorrect)) * 2;

        double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
        double yCornerTL = ((-yHeight) - (-up * upCorrect+ (walking * walkCorrect))) * 2;
        double yCornerBL = ((+0.5 - yHeight) - (-up * upCorrect+ (walking* walkCorrect))) * 2;
        double rotLeftSideZ = zcLeft  * cosine + xcLeft * sine;

        double xcRight = ((xRight / 2) - (right * rightCorrect)) * 2;
        double zcRight = ((zDistanceRight / 2) - (forward * forwardCorrect)) * 2;

        double rotRightSideX = xcRight * cosine - zcRight * sine;
        double yCornerTR = ((-yHeight) - (-up * upCorrect+ (walking* walkCorrect))) * 2;
        double yCornerBR = ((+0.5 - yHeight) - (-up * upCorrect+ (walking* walkCorrect))) * 2;
        double rotRightSideZ = zcRight * cosine + xcRight * sine;

        double tex30 = 0;
        double tex40 = 8;
        double clip = 0.5;

        if(rotLeftSideZ < clip && rotRightSideZ < clip){
            return;
        }

        if(rotLeftSideZ < clip){
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
            rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex30 = tex30  + (tex40 - tex30) * clip0;
        }
        if(rotRightSideZ < clip){
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
            rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex40 = tex30  + (tex40 - tex30) * clip0;
        }

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
        if(xPixelRightInt > width){
            xPixelRightInt = width;
        }

        double yPixelLeftTop  = yCornerTL / rotLeftSideZ * height + height / 2.0;
        double yPixelLeftBottom = yCornerBL  / rotLeftSideZ * height + height / 2.0;
        double yPixelRightTop = yCornerTR / rotRightSideZ * height + height / 2.0;
        double yPixelRightBottom = yCornerBL / rotRightSideZ * height + height / 2.0;

        double tex1 = 1 / rotLeftSideZ;
        double tex2 = 1 / rotRightSideZ;
        double tex3 = tex30 / rotLeftSideZ;
        double tex4 = tex40 / rotRightSideZ - tex3;

        for(int x = xPixelLeftInt; x < xPixelRightInt; x ++){
            double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
            double zWall =  (tex1 + (tex2 - tex1) * pixelRotation);

            if(zBufferWall[x] > zWall){
                continue;
            }
            zBufferWall[x] = zWall;

            int xTexture = (int) ((tex3 + tex4 * pixelRotation) / zWall);

            double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
            double yPixelBottom = yPixelLeftBottom  + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

            int yPixelTopInt = (int) yPixelTop;
            int yPixelBottomInt = (int) yPixelBottom;

            if(yPixelTopInt < 0){
                yPixelTopInt = 0;
            }
            if(yPixelBottomInt > height){
                yPixelBottomInt = height;
            }

            for(int y = yPixelTopInt; y < yPixelBottomInt; y++){
                double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
                int yTexture = (int) (8 * pixelRotationY);
                pixels[x + y * width] = Texture.floor.pixels[((xTexture & 7) + 8) + (yTexture & 7) * 16]; // 텍스쳐 적용하기
                // pixels[x + y * width] = xTexture * 100 + yTexture * 100 * 256;

                zBuffer[x + y * width] = 1/ (tex1 + (tex2 - tex1) * pixelRotation) * 8;
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
