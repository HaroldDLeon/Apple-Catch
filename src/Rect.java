import java.awt.Graphics;



public class Rect{
	double h;
	double w;
	
	double  x; 
	double y;
	double vx;
	double vy;
	double ax;
	double ay;
	static final double gravity = 0.0;
	
	boolean held = false;
	
	public Rect(double x, double y, double width, double height){
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
	}
	
	public void draw(Graphics g){
		g.drawRect((int) x, (int) y, (int) w,(int) h);
	}
	
	public void drawFull(Graphics g){
		g.fillRect((int) x, (int) y, (int) w, (int)h);
	}
	
	public void moveBy(int dx, int dy){
		x+=dx;
		y+=dy;
	}
	
	public boolean inRect(int x,int y){
		return (x >= this.x && y>=this.y && x<=this.x+w && y<=this.y+h);
	}
	
	public boolean isColiding(Rect r){
		return(x   < r.x + r.w) &&
			  (x+w > r.x      )&&
			  (y   < r.y+r.h  )&&
			  (y+h > r.y      );
	}
	
	public void grab(){
		held=true;
	}
	public void drop(){
		held=false;
	}
	public void resizeBy(int dw, int dh){
		h+=dh;
		w+=dw;
	}
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void setVelocity(int velocity_x, int velocity_y){
		this.vx = velocity_x;
		this.vy = velocity_y;
	}
	public void setAcceleration(double acceleration_x, double acceleration_y){
		this.ax = acceleration_x;
		this.ay = acceleration_y;
	}
	
}

