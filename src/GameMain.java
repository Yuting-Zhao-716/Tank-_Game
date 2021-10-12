

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * @author
 * Jing BAI 21017636
 * Yuting Zhao 20003036
 * 
 * */

public class GameMain extends GameEngine {

	public static void main(String args[]) {
		createGame(new GameMain());
	}

	Tank tank;
	public static int ENEMY_NUM = 5;
	
	private static int Flag;


	LinkedList<Tank> tankEnemies = new LinkedList<>();
	LinkedList<Grave> graves = new LinkedList<>();

	int times = 0;
	static int level = 3;
	boolean isStart = false;
	Image backgroundImage;
	GameControl gameControl;

	int score;int coins;
	boolean gameAgain;
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		JComponent source = (JComponent)e.getSource();
		JLabel label1 = new JLabel();
		JTextField speedinput= new JTextField(20);
		JLabel label2= new JLabel();
		JButton confirm = new JButton("Set");
		confirm.setSize(100,50);
		if (source == speedItem) {
			JDialog dialog = new JDialog(mFrame, "Reset speed");
			dialog.setModal(true);
			dialog.setSize(300, 200);
			dialog.setLocation(200, 350);
			dialog.setLayout(new FlowLayout(FlowLayout.CENTER,20,5));
			label1.setText("Speed of the Tank:    ");
			label2.setText("Speed: 1 for fast,2 for middle,3 for slow:");
			dialog.add(label1);
			dialog.add(label2);
			dialog.add(speedinput);
			confirm.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						int s=Integer.parseInt(speedinput.getText())+1;
						GameMain.level=s;
					}
					catch (Exception exception){}
					dialog.dispose();
				}
			});
			dialog.add(confirm);
			dialog.setVisible(true);
		}
		if(source==tankItem){

			JDialog dialog = new JDialog(mFrame, "Reset number of tank");
			dialog.setModal(true);
			dialog.setSize(300, 200);
			dialog.setLocation(200, 350);
			dialog.setLayout(new FlowLayout(FlowLayout.CENTER,20,5));
			label1.setText("Number of the Enemy:    ");
			dialog.add(label1);
			dialog.add(speedinput);
			confirm.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						int s=Integer.parseInt(speedinput.getText());
						if(ENEMY_NUM > s){
							for(int i = 0;i < ENEMY_NUM-s; i++){
								tankEnemies.pop();
							}
							ENEMY_NUM = s;
						}
						if(ENEMY_NUM < s){
							for(int i = 0;i < s- ENEMY_NUM; i++){
								tankEnemies.add(new Tank(true));
							}
							ENEMY_NUM = s;
						}
					}
					catch (Exception exception){}
					dialog.dispose();
				}
			});
			dialog.add(confirm);
			dialog.setVisible(true);
		}
		else if (source == aboutItem) {
			JOptionPane.showMessageDialog(mFrame,"Developer Group:\nJing BAI & Yuting ZHAO","About", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// Game initiated.
	public void init() {
		this.tank = new Tank(500, 500);
		this.tank.setControl(true);
		
		this.backgroundImage = this.loadImage("resources/background_Transparency.png");
		this.tankEnemies.clear();
		this.graves.clear();
		for (int i = 0; i < ENEMY_NUM; i++) {
			this.tankEnemies.add(new Tank(true));
		}
		this.coins=0;
		this.score = 0;
		this.gameAgain = false;

		// Pop out a welcome dialog to welcome the user and show instructions.
		ImageIcon icon = new ImageIcon("welcome.jpg");
		JLabel iconLabel = new JLabel(icon);
		JLabel textLabel1 = new JLabel("Welcome! Here are some instructions:");
		JLabel textLabel2 = new JLabel("1. Using arrow keys to control your tank and \"F\" to shoot your enemies.Dead enemies will turn into coins\n");
		JLabel textLabel3 = new JLabel("2. Don't forget to collect coins.Collecting coins will earn extra 5*Coins Score.Hurry up! It might be disappeared");
		JLabel textLabel4 = new JLabel("3. The difficulties could be changed in menu");
		JLabel textLabel5= new JLabel("4. Enjoy your game!");
		textLabel1.setFont(new Font("Serif", Font.PLAIN, 20));
		textLabel2.setFont(new Font("Serif", Font.PLAIN, 14));
		textLabel3.setFont(new Font("Serif", Font.PLAIN, 14));
		textLabel4.setFont(new Font("Serif", Font.PLAIN, 14));
		textLabel5.setFont(new Font("Serif", Font.PLAIN, 14));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(iconLabel);
		panel.add(textLabel1);
		panel.add(textLabel2);
		panel.add(textLabel3);
		panel.add(textLabel4);
		panel.add(textLabel5);
		

		JOptionPane.showMessageDialog(mFrame,panel,"Hi Mario TankMan!",JOptionPane.PLAIN_MESSAGE);

	}

	@Override
	public void update(double dt) {
		
		if (gameControl != null) {
//			level = gameControl.getGameLevel();
			isStart = gameControl.getStart();
			gameControl.setScore(this.score);
			gameControl.setCoins(this.coins);
		}

		// Check if the coins have been eaten by tank.
		for(int i=0;i<this.graves.size();i++){
			if(this.tank.posEquals(this.graves.get(i))){
				System.out.println("HIT");
				this.graves.remove(i);
				this.coins++;
				break;
			}

		}

		times++;
		if (times % level == 0 && isStart) {
			// if the user is dead
			if (this.tank.getIsDead()) {
				this.playAudio(this.loadAudio("resources/dead.wav"));
				//this.playMusic("resources/dead.wav");
				int result = JOptionPane.showConfirmDialog(null, "You are Dead! Final score: "+(score+coins*5)+"\n"+" Play again?", "Oh NO!!!!!",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (result == JOptionPane.CANCEL_OPTION) {
					System.exit(0);
				} else {
					this.gameAgain = true;
//					gameControl.gameAgain(true);
					init();
				}
			}

			// the user is still alive
			else {
				this.tank.updateAndCheckDead(this.tankEnemies);
				LinkedList<Tank> died = new LinkedList<>();
				for (Tank e: this.tankEnemies) {
					LinkedList<Tank> list = new LinkedList<>();
					list.add(this.tank);
					if (e.updateAndCheckDead(list) ){
						died.add(e);
					}
				}
				if (!died.isEmpty()) {
					this.score += 1;
					Tank die = died.getFirst();
					// mark died coordinate and only keep 5 graves
					if ( this.graves.size() == 5 ) {
						this.graves.remove();
					}
					this.graves.add(new Grave(die.getX(), die.getY()));


					// generate new enemy
					this.tankEnemies.remove(die);
					Tank newEnemy = new Tank(true);
					this.tankEnemies.add(newEnemy);
				}
			}

			//reset times to avoid overflow
			times = 1;
		}

	}

	@Override
	public void paintComponent() {
		if(this.Flag == 0) {
			super.setupWindow(600, 630);
			this.Flag = 1;
		}
		if (gameControl == null) {
			gameControl = new GameControl(this.mPanel,this.mFrame);
			this.startAudioLoop(this.loadAudio("resources/background.wav"));
			
		}
		drawImage(backgroundImage, 0, 0, 600, 600);
		this.paint(mGraphics);

	}

	public void paint(Graphics g) {
		this.tank.render(g);
		for (Tank e: this.tankEnemies) {
			e.render(g);
		}
		for (Grave gr: this.graves) {
			gr.render(g);
		}
		this.gameControl.updateText(this.score,this.coins, g);
		this.mFrame.setTitle("Mario Tank Battle");
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.tank.setDirection(DIRECTION.LEFT);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.tank.setDirection(DIRECTION.RIGHT);
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			this.tank.setDirection(DIRECTION.UP);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.tank.setDirection(DIRECTION.DOWN);
		}
		if (e.getKeyCode() == KeyEvent.VK_F) {
			this.tank.fire();
		}
	}


	
	public Image loadImage(String filename) {
		try {
			URL url = this.getClass().getResource(filename);
			Image image = ImageIO.read(url);
			return image;
		} catch (Exception e) {
			System.out.println("Can't load image " + filename);
			System.exit(1);
		}
		return null;
	}
}
