import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Craft_Enemy extends Sprite {

	public int dx;
	public int dy;
	public int lives = 9;
	private ArrayList<Bullet> missiles;

	public static final int DELTA = 5;

	public Craft_Enemy() {
		
		missiles = new ArrayList<Bullet>();
		loadImage("images/ship_enemy.png");
		getImageDimensions();

		x = (Craft.WINDOW_WIDTH / 2) - width / 2;
		y = height + 20;
	}
	
	
	public ArrayList<Bullet> getMissiles() {
        return missiles;
    }
	
	public void fire(boolean s_bullet) {
//      missiles.add(new Bullet(x + image.getWidth(null)/2, y));		// Lorsqu'on construit le bullet, on met le visible à true.
//      missiles.add(new Bullet(x , y));
  	missiles.add(new Bullet(x + width/4  , y + height, true, s_bullet));
  }

	public void move() {
		if ((dx < 0 && x > 0) || (dx > 0 && x < (Craft.WINDOW_WIDTH - width)))
			x += dx;
		if ((dy < 0 && y > 0 || (dy > 0 && y < Craft.WINDOW_HEIGHT / 3)))
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

	public Rectangle getBounds() {
		return new Rectangle(x, y, (width - width / 4), (height - height / 2));
	}


	public void touched() {
		lives--;
		if (lives == 0)
			vis = false;
		
	}

}
