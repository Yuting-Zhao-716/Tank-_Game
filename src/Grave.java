
/**
 * @author
 * Jing BAI 21017636
 * Yuting Zhao 20003036
 * 
 * */

import java.awt.*;

public class Grave extends Item{

    private Image grave_img = null;

    public Grave(int x, int y) {
        super(x, y, DIRECTION.LEFT);
        this.grave_img = loadImage("resources/mariocoin.png");
    }

    public void draw(Graphics g) {
        g.drawImage(this.grave_img, this.getX(), this.getY(), null);
    }

    public void render(Graphics g) {
        this.draw(g);
    }
}