package org.java3d.graphics;


public class Render {
    public final int width;
    public final int height;
    public final int[] pixels; // 픽셀 array

    public Render(int width, int height){
        this.width = width;
        this.height = height;
        pixels = new int[width * height]; // 800 x 600 = 480,000 size of array
    }

    public void draw(Render render, int xOffset, int yOffset){
        for(int y = 0; y < render.height; y++) {
            int yPix = y + yOffset;
            if(yPix < 0 || yPix >= height) {
                continue;
            }

            for (int x = 0; x < render.width; x++) {
                int xPix = x + xOffset;
                if(xPix < 0 || xPix >= width){
                    continue;
                }
                int alpha = render.pixels[x + y * render.width];

                // 성능개선 팁: 렌더할 게 없다면 렌더하지 마라.
                if(alpha > 0) {
                    pixels[xPix + yPix * width] = alpha;
                }
            }
        }
    }
}
