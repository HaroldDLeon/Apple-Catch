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
	
	Random rand	= new Random();
	
	Random Test = new Random();
	
	int Random = rand.nextInt(100);
	int TestR = Test.nextInt(100);
	
	Sprite AppleSprite;
	
	int points = 20;
	
	boolean isRotten = false;
	boolean isGolden =false;
	boolean isLife =false;
	
	public Apple()
	{
		super(-100,-100,40,45);
		this.x = -100;
		this.y = 0;
		this.w = 0;
		this.h = 0;
	}
	public Apple(int Index)
	{
		super(-100,-100,40,45);
		if(Random > 89)		{
			this.isGolden=true;
			this.points = 100; // Golden Apple
			image = Toolkit.getDefaultToolkit().getImage(GoldApple);
		}
		
		else if(Random> 79 && Random<90) {
			this.isRotten = true;
			this.points = -100; // Rotten apple
			image = Toolkit.getDefaultToolkit().getImage(RottenApple);
		}
		
		else if(Random>69 && Random<80 ){
			this.points = 40; // Normal Apple
			image = Toolkit.getDefaultToolkit().getImage(Apple);
		}
		else{
			this.points =40;
			image = Toolkit.getDefaultToolkit().getImage(Apple);
			
			//setsprite for normal apple
		}
		
		Random = (Random+Index+7)*7%100;
		
		this.x = Random*5 +20;
		this.y = TestR*4 -200;
		
	}
	
	public void draw(Graphics g)	{
		g.setColor(Color.red);
//		this.drawFull(g);
		g.drawImage(image,this.x,this.y,null);
	}
	
}
