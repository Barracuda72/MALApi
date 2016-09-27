import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by barracuda on 22.09.16.
 */
public class ImagePool {
    private static final String imageDirectory = "./img/";
    private static Map<String,Image> cache = new HashMap<>();

    static {
        File theDir = new File(imageDirectory);

        if (!theDir.exists()) {
            theDir.mkdir();
        }
    }

    public static Image loadImage(String uri) {
        return new BufferedImage(200, 300, BufferedImage.TYPE_INT_ARGB);
    }

    public static Image _loadImage(String uri) {
        String hash = getHash(uri);

        if (cache.containsKey(hash)) {
            System.out.println("Loading precached "+uri);
            return cache.get(hash);
        }

        String path = imageDirectory+hash;
        File image = new File(path);
        BufferedImage img = null;

        try {
            if (!image.exists()) {
                System.out.println("Downloading "+uri);
                URL url = new URL(uri);

                img = ImageIO.read(url);
                ImageIO.write(img, "PNG", image);
            } else {
                System.out.println("Loading saved "+uri);
                img = ImageIO.read(image);
            }
            if (img != null)
                cache.put(hash, img);
            else
                System.out.print("Something wrong with "+uri);
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            return new BufferedImage(200, 300, BufferedImage.TYPE_INT_ARGB);
        }
    }

    private static String getHash(String uri) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(uri.getBytes());
            return Base64.getEncoder().encodeToString(messageDigest.digest()).replace('/', '_');
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return Integer.toString(uri.hashCode());
        }
    }
}
