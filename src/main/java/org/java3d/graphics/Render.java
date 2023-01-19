package org.java3d.graphics;

public class Render {
    public final int width; //
    public final int height;
    public final int[] pixels; //

    public Render(int width, int height){
        this.width = width;
        this.height = height;
        pixels = new int[width * height]; // 800 x 600 = 480,000 size of array
    }
}
