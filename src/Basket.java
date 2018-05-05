import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Image;

public class Basket extends Rect {
	
	Image basket_img = Toolkit.getDefaultToolkit().getImage("../assets/basket.png");
	
	public Basket(int x, int y) {
		super(x, y, 100, 90);
	}
	
	public void draw(Graphics g) {
		g.drawImage(basket_img, this.x, this.y, null);
	}
}
