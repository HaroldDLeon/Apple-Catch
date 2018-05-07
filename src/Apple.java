import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

public class Apple extends Rect {

	String base 		= "../assets/";
	String Apple		= base +"red_apple.png";
	String GreenApple	= base +"green_apple.png";
	String GoldApple	= base +"gold_apple.png";
	String RottenApple	= base +"rotten_apple.png";
	String BonusApple	= base +"speed_apple.png";
	String HeartApple 	= base + "heart_apple.png";
	String TimeApple 	= base + "time_apple.png";

	Image image;
	
	Random rand	 = new Random();
	Random Test  = new Random();
	Random Super = new Random();
	
	int random_x = rand.nextInt(100);
	int random_y = Test.nextInt(100);
	int SuperRandom = Super.nextInt(100);
	
	static final int golden_points  = 100;
	static final int green_points 	= 25;
	static final int red_points		= 50;
	static final int rotten_points 	= -100;
	
	int points;
	
	boolean isRotten 	= false;	
	boolean isGolden 	= false;
	boolean isLife 		= false;
	
	public Apple()	{
		super(-100,0,0,0);
	}
	
	public Apple(String type) {
		super(-100, -100, 40, 40);
		if(type == "red") {
			this.points = red_points;
			image=Toolkit.getDefaultToolkit().getImage(Apple);
		}
		else if (type == "green") {
			this.points = green_points;
			image=Toolkit.getDefaultToolkit().getImage(GreenApple);
		}
		else if (type == "golden") {
			this.isGolden = true;
			this.points = golden_points;
			image=Toolkit.getDefaultToolkit().getImage(GoldApple);
		}
		else if (type == "rotten") {
			this.isRotten = true;
			this.points = rotten_points;
			image=Toolkit.getDefaultToolkit().getImage(RottenApple);
		}
		rand.nextInt(200);
		this.x = rand.nextInt(530);
		this.y = rand.nextInt(200)+ 25;
	}
	
	public Apple(int Index)
	{
		super(-100,-100,40,40);
		
		if(SuperRandom > 95){
			this.isGolden=true;
			this.points = golden_points;
			image=Toolkit.getDefaultToolkit().getImage(GoldApple);
		}
		else if(SuperRandom> 20 && SuperRandom<95)	{
			this.isRotten = true;
			this.points = rotten_points;
			image=Toolkit.getDefaultToolkit().getImage(RottenApple);
		}
		else if(SuperRandom>10 && SuperRandom<20 )	{
			this.points = red_points;
			image=Toolkit.getDefaultToolkit().getImage(Apple);
		}
		else{
			this.points = green_points;
			image=Toolkit.getDefaultToolkit().getImage(GreenApple);
		}
		
		//Define the apple's position
		this.x = random_x*5 +20;
		this.y = random_y ;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.red);
		//AppleRect.drawFull(g);  //Shows hit-box
		g.drawImage(image,(int) this.x, (int) this.y,40,45,null);
	}	
}
