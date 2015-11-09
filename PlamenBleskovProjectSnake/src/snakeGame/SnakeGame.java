package snakeGame;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class SnakeGame extends JFrame {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;


	public SnakeGame() {

        add(new GameBoard());

        setResizable(false);
        pack();

        setTitle("SnakeGame");

    }


    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame ex = new SnakeGame();
                ex.setVisible(true);
            }
        });
    }
}
