import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable{
	
	private static final long serialVersionUID = 1L;
	public int mapWidth = 32;
	public int mapHeight = 18;
	private Thread thread;
	private boolean running;
	private BufferedImage image;
	public int[] pixels;
	public ArrayList<Texture> textures;
	public Camera camera;
	public Screen screen;
	public static int[][] map = 
		{
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,8,0,0,0,0,0,8,8,0,0,0,8,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,8,0,0,0,0,0,8,8,0,0,0,8,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,8,0,0,0,0,0,8,8,0,0,0,8,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,8,0,0,0,0,0,0,0,0,0,0,8,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,8,0,0,0,0,0,8,8,0,0,0,8,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,8,0,0,0,0,0,8,8,0,0,0,8,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,8,8,8,8,8,8,8,8,8,0,8,8,8,0,8,8,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,9,9,9,9,9,9,9,9,9,9,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		};
	public Game() {
		thread = new Thread(this);
		image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		textures = new ArrayList<Texture>();
		textures.add(Texture.wood);
		textures.add(Texture.brick);
		textures.add(Texture.bluestone);
		textures.add(Texture.stone);
		textures.add(Texture.cat);
		textures.add(Texture.frog);
		textures.add(Texture.dog);
		textures.add(Texture.purplebrick);
		textures.add(Texture.bluebrick);
		camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
		screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480);
		addKeyListener(camera);
		setSize(640, 480);
		setResizable(false);
		setTitle("3D Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
		start();
	}
	private synchronized void start() {
		running = true;
		thread.start();
	}
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		bs.show();
	}
	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;//60 times per second
		double delta = 0;
		requestFocus();
		while(running) {
			long now = System.nanoTime();
			delta = delta + ((now-lastTime) / ns);
			lastTime = now;
			while (delta >= 1)//Make sure update is only happening 60 times a second
			{
				//handles all of the logic restricted time
				screen.update(camera, pixels);
				camera.update(map);
				delta--;
			}
			render();//displays to the screen unrestricted time
		}
	}
	public static void main(String [] args) {
		String music_filepath = "music/videoplayback.wav";
		Sound gameMusic = new Sound();
		gameMusic.playMusic(music_filepath);
		new Game();
	}
}