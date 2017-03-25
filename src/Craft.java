import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Craft extends Sprite {

	public int dx;
	public int dy;
	public int lives = 9;
	private ArrayList<Bullet> missiles;

	public static final int DELTA = 5;
	public static final int WINDOW_WIDTH = 900;
	public static final int WINDOW_HEIGHT = 800;

	public Craft() {

		initCraft();
	}

	private void initCraft() {

		missiles = new ArrayList<Bullet>();
		loadImage("images/small_blue_spaceship.gif");
		getImageDimensions();
		x = (WINDOW_WIDTH / 2) - image.getWidth(null) / 2;
		y = WINDOW_HEIGHT - image.getHeight(null) - 20;
	}

	public ArrayList<Bullet> getMissiles() {
		return missiles;
	}

	public void fire(boolean s_bullet) {
		// Lorsqu'on construit le bullet, on met le visible à true.
		// missiles.add(new Bullet(x , y));
		missiles.add(new Bullet(x + image.getWidth(null) / 4, y, false, s_bullet));
	}

	public void move() {
		if ((dx < 0 && x > 0) || (dx > 0 && x < (WINDOW_WIDTH - image.getWidth(null))))
			x += dx;
		if ((dy < 0 && y > WINDOW_HEIGHT / 2) || (dy > 0 && y < WINDOW_HEIGHT - image.getHeight(null)))
			y += dy;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getImage() {
		return image;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, (width - width / 4), (height - height / 2));
	}

	public void touched() {
		lives--;
		if (lives == 0)
			vis = false;
	}

}
