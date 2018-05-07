import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Ball extends Rect {
	
	String location = "../assets/tennis_ball.png";
	Image image;
	
	public Ball(double x, double y) {
		super(x, y, 40, 45);
		image =Toolkit.getDefaultToolkit().getImage(location);
	}
	public void draw(Graphics g){
		g.drawImage(image,(int) this.x, (int) this.y, 40, 45, null);;
	}

}
