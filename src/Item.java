

import javax.imageio.ImageIO;

/**
 * @author
 * Jing BAI 21017636
 * Yuting Zhao 20003036
 * 
 * */
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Item {
	private int x, y;
	private DIRECTION d;
	private int step = 4;

	public Item(int a, int b) {
		this.x = a;
		this.y = b;
		this.d = DIRECTION.LEFT;
	}

	public Item(int a, int b, DIRECTION d) {
		this.x = a;
		this.y = b;
		this.d = d;
	}

	public Item(int a, int b, int speed, DIRECTION d) {
		this.x = a;
		this.y = b;
		this.d = d;
		this.step = speed;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getStep() {
		return this.step;
	}


	public DIRECTION getDirection() {
		return d;
	}


	public void setX(int a) {
		x = a;
	}

	public void setY(int b) {
		y = b;
	}

	public void setDirection(DIRECTION d) {
		this.d = d;
	}

	public boolean posEquals(Item newItem) {
		return newItem.getX() - 20 < x && x< newItem.getX() + 20 &&
				newItem.getY() - 20 < y && y<= newItem.getY() + 20;
	}

	public boolean outOfBoarder() {
		switch (this.d) {
		case UP:
			if (this.y <= 0) { return true; }
			break;
		case DOWN:
			if (this.y >= (600-20)) { return true; }
			break;
		case LEFT:
			if (this.x <= 0) { return true; }
			break;
		case RIGHT:
			if (this.x >= (600-20)) { return true; }
			break;
		default: 
			return false;
		}
		
		return false;
		 
	}

	public void turnBack() {
		switch (this.d) {
			case UP:
				this.d = DIRECTION.DOWN;
				break;
			case DOWN:
				this.d = DIRECTION.UP;;
				break;
			case LEFT:
				this.d = DIRECTION.RIGHT;;
				break;
			case RIGHT:
				this.d = DIRECTION.LEFT;;
				break;
			default:
				break;
		}
	}

	public void move(boolean auto) {
		if (this.outOfBoarder()) {
			// enemy will turn back when hitting the boarder
			if (auto) {
				this.turnBack();
			} else {
				return;
			}
		}

		if (auto) {
			this.moveForwardWithRandomDirection();
		} else {
			this.moveForward();
		}
	}

	public void moveForwardWithRandomDirection() {
		// randomly turn another direction for enemies
		// 1/500 chance for each direction
		int random = (int) (Math.random() * 500);
		switch (random) {
			case 1:
				this.d = DIRECTION.DOWN;
				break;
			case 2:
				this.d = DIRECTION.UP;;
				break;
			case 3:
				this.d = DIRECTION.RIGHT;;
				break;
			case 4:
				this.d = DIRECTION.LEFT;;
				break;
			default:
				break;
		}

		this.moveForward();
	}

	public void moveForward () {
		switch (this.d) {
			case UP:
				this.y -= this.step;
				break;
			case DOWN:
				this.y += this.step;
				break;
			case LEFT:
				this.x -= this.step;
				break;
			case RIGHT:
				this.x += this.step;
				break;
			default:
				break;
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
