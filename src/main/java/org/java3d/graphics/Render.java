package org.java3d.graphics;

import org.java3d.Display;

public class Render {
    public final int width; //
    public final int height;
    public final int[] pixels; //

    private Display display;

    public Render(int width, int height){
        this.width = width;
        this.height = height;
        pixels = new int[width * height]; // 800 x 600 = 480,000 size of array
    }

    public void draw(Render render, int xOffset, int yOffset){
        for(int y = 0; y < render.height; y++) {
            int yPix = y + yOffset;
            if(yPix < 0 || yPix >= display.HEIGHT) {
                continue;
            }
            for (int x = 0; x < render.width; x++) {
                int xPix = x + xOffset;
                if(xPix < 0 || xPix >= display.WIDTH){
                    continue;
                }
                pixels[xPix + yPix * width] = render.pixels[x + y * render.width];
                // System.out.println("x: " + x + " y: " + y);
            }
        }
    }
}
