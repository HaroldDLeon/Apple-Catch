import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javafx.scene.paint.Color;

public class Game extends Applet implements KeyListener, Runnable, MouseListener, MouseMotionListener
{
	
	Image	off_screen;
	Graphics off_g;	  
	
	Thread t;
	
	int AppleTimer = 0;
	
	//Speed multiplyer for the basket
	int speed =1;
	
	//Booleans controling Basket direction
	boolean lePressed = false;
	boolean riPressed = false;
	
	int score;

	Basket Basket = new Basket();
	
	Rect Tree = new Rect(20,20,500,200);
	
	Apple[] Apple = new Apple[50];
	
	int ArrayIndex=0;
	
	int AppleSlowTimer =0;
	
	
	public void init() 
	{
		//Initializing threads and removing flicker, not much reason to touch anything here
				
		off_screen = createImage(1000,700);
		off_g	   = off_screen.getGraphics();
		
		this.requestFocus();
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseListener(this);
		
		t = new Thread(this);
		
		t.start();
		
		for(int i =0; i<50; i++)
		{
			Apple[i] = new Apple();
		}
		
	}
		
	
	public void run() 
	{
		while(true)
		{
			
			if(AppleTimer<80)
			{
				AppleTimer++;
			}
			
			if(AppleTimer==80)
			{
				
				AppleTimer=0;
				
				SpawnApples(ArrayIndex);
				
				ArrayIndex = ArrayIndex+1;
				if(ArrayIndex>49)
				{
					ArrayIndex = 0;
				}
			}
			
			
			//Checks for collision with baskets each frame, and increases your score by the value of the apple
			
			/*AppleSlowTimer++;
			if(AppleSlowTimer ==3)
			{
				AppleSlowTimer= 0;
			}*/
			for(int i =0; i<50; i++)
			{
				System.out.println(" "+i);
				if(	Apple[i].AppleRect.isColiding(Basket.BasketRect))
				{
					Apple[i].AppleRect.moveBy(-10000, -1000);
					
					score+=Apple[i].points;
				}
				
				//Moves all apples down by 1
				
				
				/*if(AppleSlowTimer == 3)
				{*/
					Apple[i].AppleRect.moveBy(0, 1);
				/*}
				
				if(AppleSlowTimer ==4)
				{
					AppleSlowTimer= 0;
				}*/
			}
			
			//Controls Basket Speed
			if(lePressed) Basket.moveBy(-3*speed);
			if(riPressed) Basket.moveBy(3*speed); 
			
			
			//Left Side Basket Boundry
			if(Basket.BasketRect.x <20)
			{
				Basket.BasketRect.x = 20;
			}
			
			//Right side Basket Boundry
			if(Basket.BasketRect.x >480)
			{
				Basket.BasketRect.x = 480;
			}
			
			repaint();
			
			try
			{
				t.sleep(15);	
			}
			catch(Exception x) {}
			
		}
		
	}
	
	public void update(Graphics g)
	{
		off_g.clearRect(0, 0, 1000, 1000);
		
		paint(off_g);
		
		g.drawImage(off_screen,0,0,null);
	}
	
	public void paint(Graphics g)
	{
		this.setSize(600, 800);
		
		//Draws Score (in top left corner currently
		g.drawString(score + " ", 10, 10);
		
		Basket.draw(g);
		
	
		Tree.drawFull(g);
		
		for(int i =0; i<50; i++)
		{
			Apple[i].draw(g);
		}
	
	}

	public void SpawnApples(int Index)
	{
		Apple[Index] = new Apple(Index);
	}

	
	public void mouseDragged(MouseEvent e) {
		
		
	}

	
	public void mouseMoved(MouseEvent e) {
		
		
	}

	
	public void mouseClicked(MouseEvent e) {
		
		
	}

	
	public void mouseEntered(MouseEvent e) {
		
		
	}

	
	public void mouseExited(MouseEvent e) {
		
		
	}

	
	public void mousePressed(MouseEvent e) {
		
		
	}

	
	public void mouseReleased(MouseEvent e) {
		
		
	}


	
	public void keyPressed(KeyEvent e) 
	{
		int code = e.getKeyCode();
	
		if (code == e.VK_A)		lePressed = true;
		
		if (code == e.VK_D)		riPressed = true;
		
	}

	
	public void keyReleased(KeyEvent e) 
	{
		int code = e.getKeyCode();
		
		if (code == e.VK_A)		lePressed = false;
		
		if (code == e.VK_D)		riPressed = false;
		
	}

	
	public void keyTyped(KeyEvent e) {
		
		
	}
	
	
	
}
