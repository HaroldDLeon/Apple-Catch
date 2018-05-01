import java.awt.Graphics;

import javafx.scene.paint.Color;

public class Basket
{
	Rect BasketRect = new Rect(200,600,60,20);
	
	Basket()
	{
		
	}
	
	
	public void draw(Graphics g)
	{
		g.setColor(java.awt.Color.yellow);
		BasketRect.drawFull(g);
	}
	
	public void moveBy(int move)
	{
		this.BasketRect.x+=move;
	}
	
}
