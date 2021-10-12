
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Objects;

/**
 * @author
 * Jing BAI 21017636
 * Yuting Zhao 20003036
 * 
 * */
public class GameControl implements ActionListener {

	// Default gameLevel.
	int gameLevel = 1;
	boolean isStart = false;
	int score;
	int coins;
	JTextArea scoreArea;
	JButton startButton;
	Color color = new Color(255, 0, 0);
	Long startTime;
	Long endTime;
	Long gameTime;
	boolean gameAgain = false;

	public GameControl(JPanel mJpanel,JFrame mframe) {
		
		JButton startButton = new JButton("Start");
		startButton.setMnemonic(KeyEvent.VK_9);
		startButton.setActionCommand("StartOrPause");
		mJpanel.add(startButton);
		this.startButton = startButton;
		this.startButton.setForeground(color);
		startButton.addActionListener(this);
		mframe.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("StartOrPause")) {

			if (this.getStart() == false) {
				if (Objects.equals(this.startButton.getText(), "Start")) {
					this.setStart(true);
					this.startButton.setText("Pause");

				}
			} else {
				if (Objects.equals(this.startButton.getText(), "Pause")) {
					this.setStart(false);
					this.startButton.setText("Start");

				}
			}

		}
		
	}

	public void setStart(boolean startStatus) {
		this.isStart = startStatus;
	}

	public boolean getStart() {
		return this.isStart;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setCoins(int score) {
		this.coins = coins;
	}

	public void increaseScore() {
		this.score += 1;
	}

	public int getScore() {
		return this.score;
	}

	public void updateText(int score, int coins, Graphics g) {

		// Count-time and score display.
		this.scoreArea = new JTextArea("Score:" + this.getScore() + " Coins: " + this.getCoins());
		this.scoreArea.setBounds(10, 30, 200, 200);
		// Set background transparency.
		scoreArea.setBackground(new Color(0, 0, 0, 0));
		scoreArea.setForeground(color);
		this.score = score;
		this.coins = coins;
		this.scoreArea.update(g);
	}

	private int getCoins() {
		return this.coins;
	}

}
