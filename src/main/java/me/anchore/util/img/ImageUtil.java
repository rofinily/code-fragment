package me.anchore.util.img;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

/**
 * @author Su
 * @date 2017/12/17
 */
public class ImageUtil {
    public static void main(String[] args) throws IOException {
        Image img = ImageIO.read(new File("d:/cat.jpg"));
//        img.getHeight(null)
        System.out.println();
    }
}
