import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	private Timer timer;
	private Craft craft;
	private Craft_Enemy craft_en;

	ImageIcon ii = new ImageIcon("images/59_heart.png");
	Image heart = ii.getImage();
	Image explosion = (new ImageIcon("images/spideyblast.gif")).getImage();
	private final int DELAY = 10;
	private Timer timer2;
	public static boolean ingame = true;

	public Board() {

		initBoard();
	}

	private void initBoard() {

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.WHITE);
		setDoubleBuffered(true);

		craft = new Craft();
		craft_en = new Craft_Enemy();

		timer = new Timer(DELAY, this);
		
		timer.start();
		
		timer2 = new Timer(1300, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ingame = false;
				timer2.stop();
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);

		Toolkit.getDefaultToolkit().sync();
	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		if (ingame) {
			
			if (craft.isVisible())
				g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);
			else{
				g2d.drawImage(explosion, craft.getX(), craft.getY(), this);
				timer2.start();
			}
			
			if (craft_en.isVisible())
				g2d.drawImage(craft_en.getImage(), craft_en.getX(), craft_en.getY(), this);
			else{
				g2d.drawImage(explosion, craft_en.getX(), craft_en.getY(), this);
				timer2.start();
			}

			ArrayList<Bullet> ms = craft.getMissiles();

			for (Object m1 : ms) {
				Bullet m = (Bullet) m1;
				g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}

			ArrayList<Bullet> ms_en = craft_en.getMissiles();

			for (Object m1 : ms_en) {
				Bullet m = (Bullet) m1;
				g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}

			g2d.drawImage(heart, 20, 20, this);
			g2d.drawImage(heart, craft.WINDOW_WIDTH - 120, craft.WINDOW_HEIGHT - 90, this);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Helvetica", Font.BOLD, 20));
			g.drawString("x " + craft_en.lives, 60, 48);
			g.drawString("x " + craft.lives, craft.WINDOW_WIDTH - 75, craft.WINDOW_HEIGHT - 60);
			
		} else {
			String msg = "Game Over";
	        Font small = new Font("Helvetica", Font.BOLD, 50);
	        FontMetrics fm = getFontMetrics(small);

	        g.setColor(Color.black);
	        g.setFont(small);
	        g.drawString(msg, (craft.WINDOW_WIDTH - fm.stringWidth(msg)) / 2,
	                craft.WINDOW_HEIGHT / 2);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (!ingame) {
			timer.stop();
		}

		if (craft.vis)
			craft.move();
		if (craft_en.vis)
			craft_en.move();
		updateMissiles();
		checkCollisions();
		repaint();
	}

	private void updateMissiles() {

		ArrayList<Bullet> ms = craft.getMissiles();

		for (int i = 0; i < ms.size(); i++) {

			Bullet m = (Bullet) ms.get(i);

			if (m.isVisible()) {
				m.move();
			} else {
				ms.remove(i);
			}
		}

		ms = craft_en.getMissiles();

		for (int i = 0; i < ms.size(); i++) {

			Bullet m = (Bullet) ms.get(i);

			if (m.isVisible()) {
				m.move();
			} else {
				ms.remove(i);
			}
		}
	}

	public void checkCollisions() {

		ArrayList<Bullet> ms = craft.getMissiles();
		Rectangle r2 = craft_en.getBounds();

		for (Bullet m : ms) {

			Rectangle r1 = m.getBounds();
			if (r1.intersects(r2)) {
				craft_en.touched();
				m.setVisible(false);
			}

		}

		ms = craft_en.getMissiles();
		r2 = craft.getBounds();

		for (Bullet m : ms) {

			Rectangle r1 = m.getBounds();
			if (r1.intersects(r2)) {
				craft.touched();
				m.setVisible(false);
			}

		}
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_ENTER) {
				craft.fire(false);
			}

			if (key == KeyEvent.VK_DOLLAR) {
				craft.fire(true);
			}

			if (key == KeyEvent.VK_LEFT) {
				craft.dx = -craft.DELTA;
			}

			if (key == KeyEvent.VK_RIGHT) {
				craft.dx = craft.DELTA;
			}

			if (key == KeyEvent.VK_UP) {
				craft.dy = -craft.DELTA;
			}

			if (key == KeyEvent.VK_DOWN) {
				craft.dy = craft.DELTA;
			}

			if (key == KeyEvent.VK_SPACE) {
				craft_en.fire(false);
			}

			if (key == KeyEvent.VK_Q) {
				craft_en.dx = -craft_en.DELTA;
			}

			if (key == KeyEvent.VK_D) {
				craft_en.dx = craft_en.DELTA;
			}

			if (key == KeyEvent.VK_Z) {
				craft_en.dy = -craft_en.DELTA;
			}

			if (key == KeyEvent.VK_S) {
				craft_en.dy = craft_en.DELTA;
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT) {
				craft.dx = 0;
			}

			if (key == KeyEvent.VK_RIGHT) {
				craft.dx = 0;
			}

			if (key == KeyEvent.VK_UP) {
				craft.dy = 0;
			}

			if (key == KeyEvent.VK_DOWN) {
				craft.dy = 0;
			}

			if (key == KeyEvent.VK_Q) {
				craft_en.dx = 0;
			}

			if (key == KeyEvent.VK_D) {
				craft_en.dx = 0;
			}

			if (key == KeyEvent.VK_Z) {
				craft_en.dy = 0;
			}

			if (key == KeyEvent.VK_S) {
				craft_en.dy = 0;
			}

		}
	}
}