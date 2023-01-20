package org.java3d.graphics;

import org.java3d.Game;

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
        double forward = game.controls.z;
        double right = game.controls.x;

        double rotation = game.controls.rotation;
        double cosine = Math.cos(rotation);
        double sine = Math.sin(rotation);

        for(int y = 0; y < height; y++){
            double ceiling = (y + - height / 2.0) / height;

            double z = floorPosition / ceiling;
            if (ceiling < 0){
                z = ceilingPosition / -ceiling;
            }

            for(int x = 0; x < width; x++){
                double depth = (x - width / 2.0) / height;
                depth *= z;
                double xx = (depth) * cosine + z * sine;
                double yy = z * cosine - depth * sine;
                int xPix= (int) (xx+ right);
                int yPix = (int) (yy + forward);
                zBuffer[x + y * width] = z;
                pixels[x + y * width] = ((xPix & 15) * 16) | ((yPix & 15) * 16) << 8;

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
