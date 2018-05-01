import java.awt.Graphics;



public class Rect
{
	int h;
	int w;
	
	int x; 
	int y;
	
	boolean held = false;
	
	public Rect(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
	}
	
	public void draw(Graphics g)
	{
		g.drawRect(x, y, w, h);

	}
	
	public void drawFull(Graphics g)
	{
		g.fillRect(x, y, w, h);
	}
	
	
	public void moveBy(int dx, int dy)
	{
		x+=dx;
		y+=dy;
	}
	
	public boolean inRect(int x,int y)
	{
		if(x>= this.x && y>=this.y && x<=this.x+w && y<=this.y+h)
		{
			return true;
		}
		
		else
		{
			return false;
		}
	}
	
	public boolean isColiding(Rect r)
	{
		return(x   < r.x + r.w) &&
			  (x+w > r.x      )&&
			  (y   < r.y+r.h  )&&
			  (y+h > r.y      );
	}
	
	public void grab()
	{
		held=true;
	}
	public void drop()
	{
		held=false;
	}
	
	public void resizeBy(int dw, int dh)
	{
		h+=dh;
		w+=dw;
	}
	
}

