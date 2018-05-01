import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import java.awt.Image;


public class Apple {

	String base = "../assets/";
	String Apple= base +"Apple0.png";
	String GoldApple= base +"Apple0.png";
	String RottenApple= base +"Apple0.png";
	String BonusApple= base +"Apple0.png";
	
	Image image;
	
	Random rand	= new Random();
	
	Random Test = new Random();
	
	int Random = rand.nextInt(100);
	int TestR = Test.nextInt(100);
	
	Rect AppleRect = new Rect(-100,-100,40,40);
	
	Sprite AppleSprite;
	
	int points = 20;
	
	boolean isRotten = false;
	boolean isGolden =false;
	boolean isLife =false;
	
	public Apple()
	{
		AppleRect = new Rect(-100,-100,20,20);
		this.AppleRect.x = -100;
		this.AppleRect.y = 0;
		this.AppleRect.w = 0;
		this.AppleRect.h = 0;
	}
	
	
	public Apple(int Index)
	{
		if(Random > 89)
		{
			this.isGolden=true;
			this.points = 100;
			
			//SetSprite (or just an animation?) for golden apple
		}
		else if(Random> 79 && Random<90)
		{
			this.isRotten = true;
			this.points = -100;
			
			//Setsprite (or just animation?) for golden apple
		}
		else if(Random>69 && Random<80 )
		{
			this.points = 40;
			
			//setsprite for normal apple
		}
		else
		{
			this.points =40;
			
			image=Toolkit.getDefaultToolkit().getImage(Apple);
			
			//setsprite for normal apple
		}
		
		Random = (Random+Index+7)*7%100;
		
		this.AppleRect.x = Random*5 +20;
		
		this.AppleRect.y = TestR*4 -200;
		
		
		
		
		
		
		
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.red);
		AppleRect.drawFull(g);
		
		g.drawImage(image,this.AppleRect.x,this.AppleRect.y,40,40,null);
	}
	
	
	
}
