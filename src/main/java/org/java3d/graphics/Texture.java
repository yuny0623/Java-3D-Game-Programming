package org.java3d.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class Texture {
    public static Render floor = loadBitmap("res/textures/floor.png");

    public static Render loadBitmap(String fileName){
        try{
            // Texture.class.getResource는 이슈가 있음. 대신 FileInputStream을 쓰세요.
            // BufferedImage image = ImageIO.read(Texture.class.getResource(fileName));
            BufferedImage image = ImageIO.read(new FileInputStream(fileName));
            int width = image.getWidth();
            int height = image.getHeight();
            Render result = new Render(width, height);
            image.getRGB(0, 0, width, height, result.pixels,0, width);
            return result;
        }catch (Exception e){
            // 게임에 필요한 이미지, 파일을 날렸다면 게임을 구동하지 못하게 만든다.
            System.out.println("CRASH!");
            throw new RuntimeException(e);
        }
    }
}
