package org.java3d.graphics;

public class Render3D extends Render{

    public Render3D(int width, int height) {
        super(width, height);
    }

    // 이 코드는 좀 이해가 안가네요.
    /*
    Comment below:
    From what I can tell, he basically derived the rendering mechanism from the following perspective equations,
    sx = x / z tan(fov / 2)
    sy = y / z tan(fov / 2)
    Where sx, sy are normalized screen coords and x, y, z are 3d space coords
    You can exclude the tan to get an fov of 90 degrees

    Then since already know sx, sy and y(the floor/ceiling distance/height) you loop through the y coordinate of the screen and for each row solve for z:
    z = 1 / sy * y
    Then you have a z distance for every horizontal row of pixels
    Next, for every horizontal row you loop through all the pixels based on the distance of the row from the camera,
    x = sx * z

    Now you have top down 2d coords x and z which you can directly translate to texture coordinates
    Am i on the right track? I'm very tired
     */
    public void floor(){
        for(int y = 0; y < height; y++){
            double yDepth = y - height / 2.4 ;
            double z = 100.0 / yDepth;
            for(int x = 0; x < width; x++){
                double depth = x - width / 2;
                depth *= z;
                int xx = (int) (depth) & 5; // <- ?
                pixels[x + y * width] = xx * 128;
            }
        }
    }
}
