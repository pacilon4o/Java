package snakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameBoard extends JPanel implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final int canvasHeight = 800;
	private final int canvasWidth = 600;
	private final int imageSize = 60;
	private final int allDots = 800 * 600 / 60 * 40;
	private final int randomBee = 5;
	public int gameSpeed = 500;

	private final int spiderCoordinatesX[] = new int[allDots];
	private final int spiderCoordinatesY[] = new int[allDots];
	private int beeCoordinatesX;
	private int beeCoordinatesY;

	private int spiderParts;
	private Timer timer;
	private Image dot;
	private Image bee;
	private Image head;
	private Image headUp;
	private Image headDown;
	private Image headLeft;
	private Image headRight;

	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private boolean isGame = true;

	public GameBoard() {

		addKeyListener(new KeyBordCommands());
		setBackground(Color.green);
		setFocusable(true);

		setPreferredSize(new Dimension(canvasHeight, canvasWidth));
		loadImages();
		initGame();
	}

	private void loadImages() {

		ImageIcon dot1 = new ImageIcon("C:\\Users\\pacio\\Desktop\\dot.png");
		dot = dot1.getImage();

		ImageIcon bee1 = new ImageIcon("C:\\Users\\pacio\\Desktop\\bee.png");
		bee = bee1.getImage();

		ImageIcon head11 = new ImageIcon("C:\\Users\\pacio\\Desktop\\headUp.png");
		head = head11.getImage();

		ImageIcon head1 = new ImageIcon("C:\\Users\\pacio\\Desktop\\headUp.png");
		headUp = head1.getImage();

		ImageIcon head2 = new ImageIcon("C:\\Users\\pacio\\Desktop\\headDown.png");
		headDown = head2.getImage();

		ImageIcon head3 = new ImageIcon("C:\\Users\\pacio\\Desktop\\headLeft.png");
		headLeft = head3.getImage();

		ImageIcon head4 = new ImageIcon("C:\\Users\\pacio\\Desktop\\headRight.png");
		headRight = head4.getImage();

	}

	private void initGame() {

		spiderParts = 3;

		/*
		 * for (int i = 0; i < spiderParts; i++) { spiderCoordinatesX[4] = 140;
		 * spiderCoordinatesY[4] = 150; }
		 */

		positionBee();

		timer = new Timer(gameSpeed, this);
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawGame(g);
	}

	private void drawGame(Graphics g) {

		if (isGame) {

			g.drawImage(bee, beeCoordinatesX, beeCoordinatesY, this);

			for (int i = 0; i < spiderParts; i++) {
				if (i == 0) {
					g.drawImage(headUp, spiderCoordinatesX[i], spiderCoordinatesY[i], this);
				} else {
					g.drawImage(dot, spiderCoordinatesX[i], spiderCoordinatesY[i], this);
				}

			}

		} else {

			gameOver(g);
		}
	}

	private void gameOver(Graphics g) {

		String message = "Game Over";
		Font font = new Font("Times New Roman", Font.BOLD, 50);
		FontMetrics metrics = getFontMetrics(font);

		g.setColor(Color.red);
		g.setFont(font);
		g.drawString(message, (canvasHeight - metrics.stringWidth(message)) / 2, canvasWidth / 2);
	}

	private void eatMouse() {

		if ((spiderCoordinatesX[0] == beeCoordinatesX) && (spiderCoordinatesY[0] == beeCoordinatesY)) {

			spiderParts++;

			positionBee();
		}

	}

	private void move() {

		for (int i = spiderParts; i > 0; i--) {
			spiderCoordinatesX[i] = spiderCoordinatesX[(i - 1)];
			spiderCoordinatesY[i] = spiderCoordinatesY[(i - 1)];
		}

		if (left) {
			spiderCoordinatesX[0] -= imageSize;
			headUp = headLeft;
		}

		if (right) {
			spiderCoordinatesX[0] += imageSize;
			headUp = headRight;
		}

		if (up) {
			spiderCoordinatesY[0] -= imageSize;
			headUp = head;
		}

		if (down) {
			spiderCoordinatesY[0] += imageSize;
			headUp = headDown;
		}
	}

	private void snakeHitSelf() {

		for (int i = spiderParts; i > 0; i--) {

			if ((i > 4) && (spiderCoordinatesX[0] == spiderCoordinatesX[i])
					&& (spiderCoordinatesY[0] == spiderCoordinatesY[i])) {
				isGame = false;
			}
		}

		if (spiderCoordinatesY[0] >= canvasWidth) {
			isGame = false;
		}

		if (spiderCoordinatesY[0] < 0) {
			isGame = false;
		}

		if (spiderCoordinatesX[0] >= canvasHeight) {
			isGame = false;
		}

		if (spiderCoordinatesX[0] < 0) {
			isGame = false;
		}

		if (!isGame) {
			timer.stop();
		}
	}

	private void positionBee() {

		int randomNumber = (int) (Math.random() * randomBee);
		beeCoordinatesX = ((randomNumber * imageSize));

		randomNumber = (int) (Math.random() * randomBee);
		beeCoordinatesY = ((randomNumber * imageSize));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (isGame) {

			eatMouse();
			snakeHitSelf();
			move();
		}

		repaint();
	}

	private class KeyBordCommands extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if ((key == KeyEvent.VK_LEFT) && (!right)) {
				left = true;
				up = false;
				down = false;
				return;
			}

			if ((key == KeyEvent.VK_RIGHT) && (!left)) {
				right = true;
				up = false;
				down = false;
				return;
			}

			if ((key == KeyEvent.VK_UP) && (!down)) {
				up = true;
				right = false;
				left = false;
				return;
			}

			if ((key == KeyEvent.VK_DOWN) && (!up)) {
				down = true;
				right = false;
				left = false;
				return;
			}
		}
	}
}