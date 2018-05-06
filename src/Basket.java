import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Basket extends Rect {
	
	Image basket_img = Toolkit.getDefaultToolkit().getImage("../assets/basket.png");
	
	public Basket(int x, int y) {
		super(x, y, 100, 90);
	}
	
	public void draw(Graphics g) {
		g.drawImage(basket_img, (int)this.x, (int) this.y, null);
	}
}
