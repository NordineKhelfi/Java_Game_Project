public class Bullet extends Sprite {

	// private final int BOARD_HEIGHT = Craft.WINDOW_HEIGHT;
	private final int MISSILE_SPEED = 2;
	private boolean enemy = false;
	private boolean super_bullet = false;

	public Bullet(int x, int y, boolean enemy, boolean super_bullet) {
		super(x, y);
		this.enemy = enemy;
		this.super_bullet = super_bullet;

		initMissile();
	}

	private void initMissile() {
		if (super_bullet)
			loadImage("images/iceball.gif");
		else
			loadImage("images/bullet.png");
		getImageDimensions();
	}

	public void move() {

		if (enemy) {
			y += MISSILE_SPEED;

			if (y > Craft.WINDOW_HEIGHT) {
				vis = false;
			}
		} else {
			y -= MISSILE_SPEED;

			if (y < 0) {
				vis = false;
			}
		}

	}

	public boolean isEnemy() {
		return enemy;
	}

	public void setEnemy(boolean enemy) {
		this.enemy = enemy;
	}
}