import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Texture {
    public static Texture cat = new Texture("img/textures/cat.png",64);
    public static Texture wood = new Texture("img/textures/brown_wall.png", 64);
    public static Texture brick = new Texture("img/textures/brick1.png", 64);
    public static Texture bluestone = new Texture("img/textures/blue_wall.png", 64);
    public static Texture stone = new Texture("img/textures/brick2.png", 64);
    public static Texture frog = new Texture("img/textures/frog.png", 64);
    public static Texture dog = new Texture("img/textures/dog.png", 128);
    public static Texture purplebrick = new Texture("img/textures/brick3.png", 64);
    public static Texture bluebrick = new Texture("img/textures/brick4.png", 64);

	public int[] pixels;
	private String loc;
    public final int SIZE;

    public Texture(String location, int size) {
        loc = location;
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        try {
            BufferedImage image = ImageIO.read(new File(loc));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}