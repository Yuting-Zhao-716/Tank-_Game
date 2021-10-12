

import java.awt.*;

/**
 * @author
 * Jing BAI 21017636
 * Yuting Zhao 20003036
 * 
 * */

public class Bullet extends Item {

    public Bullet(int x, int y) {
        super(x, y, DIRECTION.LEFT);
    }

    public Bullet(int x, int y, DIRECTION d) {
        super(x, y, d);
    }

    public Bullet(int x, int y, int s, DIRECTION d) {
        super(x, y, s, d);
    }

    public void draw(Graphics g) {
        Image tank = loadImage("resources/bullet.png");
        g.drawImage(tank, this.getX(), this.getY(), null);
    }
}