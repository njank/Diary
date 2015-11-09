package utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IOUtils {
    public static byte[] readFile(File f) {
        try{
            return Files.readAllBytes(Paths.get(f.getAbsolutePath()));
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static void writeFile(File f, byte[] content) {
        try {
            FileOutputStream stream = new FileOutputStream(f);
            stream.write(content);
            stream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static ImageIcon readImage(String filename){
        try {
            BufferedImage image = ImageIO.read(IOUtils.class.getResourceAsStream("/img/"+filename));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
