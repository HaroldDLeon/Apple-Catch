import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import java.awt.Image;


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
	
	Random rand	= new Random();
	
	Random Test = new Random();
	
	Random Super = new Random();
	
	int Random = rand.nextInt(100);
	int TestR = Test.nextInt(100);
	int SuperRandom = Super.nextInt(100);
	
	
	//Sprite AppleSprite;  (Unused)
	
	int points = 20;
	
	boolean isRotten = false;	
	boolean isGolden =false;
	boolean isLife =false;
	
	public Apple()
	{
		super(-100,0,0,0);
	
	}
	
	
	public Apple(int Index)
	{
		super(-100,-100,40,40);
		
		if(SuperRandom > 89)
		{
			this.isGolden=true;
			this.points = 100;
			
			image=Toolkit.getDefaultToolkit().getImage(GoldApple);
		}
		else if(SuperRandom> 79 && SuperRandom<90)
		{
			this.isRotten = true;
			this.points = -100;
			
			image=Toolkit.getDefaultToolkit().getImage(RottenApple);
		}
		else if(SuperRandom>49 && SuperRandom<80 )
		{
			this.points = -100;
			
			image=Toolkit.getDefaultToolkit().getImage(Apple);
		}
		else
		{
			this.points =60;
			
			image=Toolkit.getDefaultToolkit().getImage(GreenApple);
			
			
		}
		
		//these define where apples can spawn
		this.x = Random*5 +20;
		
		this.y = TestR ;
				
		
		
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.red);
		
		//AppleRect.drawFull(g);   Shows hitbox
		
		g.drawImage(image,this.x,this.y,40,40,null);
	}
	
	
	
}
