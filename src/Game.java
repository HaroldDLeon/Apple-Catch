import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


*********************
 	TO-DO LIST
 	
-	- **DONE** LOOP BACKGROUND MUSIC
-	== Uploaded temp potential background music
-	== Used small sound class from stackoverflow user - Sound.java class
-	
 	- FIX BUCKET SIDES COLLISION WITH APPLES
 	-- Sides of bucket should collide with apples
 	
@@ -34,13 +30,22 @@
 	
 	- PUSH TO START DISPLAY
 	-- Make it button? Need some design ideas here.
+	-- Make it just say "START" ?
 	
 	////////////////////
 	 
 	BUGS
 	
-	- SOME RED APPLES DECREASE POINTS / ARE ROTTEN 
+	-	
+	
+	///////////////////
+	
+	EXTRA FEATURES
+	
+	- SOUND EFFECTS FOR CATCHING APPLES
+	-- Different sounds for different apples - good sound and bad sound
 	
+		
 	*/

public class Game extends Applet implements KeyListener, Runnable, MouseListener, MouseMotionListener
{
	final int game_width = 563;
	final int game_height = 700; 
	
	Image	off_screen;
	Graphics off_g;	  
	
	Thread t;
	
	int AppleTimer 	= 30;
	int GameState 	= 0;
	int ArrayIndex 	= 0;
	int AppleSlowTimer = 0;
	
	int lives;
	int time = 0;
	int score;
	
	int BasketSpeed = 3;
	int AppleSpeed  = 1;
	
	Image apple1 = Toolkit.getDefaultToolkit().createImage("../assets/red_apple.png");

	
	

	
	boolean lePressed = false;
	boolean riPressed = false;
	
	Apple[] Apple = new Apple[50];
	Basket Basket = new Basket(game_width/2, (int)(0.85*game_height));
	//BGM game_sound = new BGM("../assets/game_over");
	
	Rect tree_rect 		= new Rect(20,20,500,200);
	Rect PlayButton 	= new Rect(110,350,350,75);
	Rect HomeButton 	= new Rect(50,650,50,30);
	Rect EndButton 		= new Rect(500,650,50,30);
	Rect Game1Button	= new Rect(110,0,100,75);
	Rect Game2Button	= new Rect(250,0,100,75);
	Rect Game3Button	= new Rect(410,0,100,75);
	
	// Audio
	BGM gameOverSound 		= new BGM("../assets/game_over");
	BGM background_music	= new BGM("../assets/background_music");
	Sound gameOver 			= new Sound("../assets/game_over.wav");
	Sound backgroundMusic 	= new Sound("../assets/background_music.wav");


	Image instruct  = Toolkit.getDefaultToolkit().getImage("../assets/instructions.png");
	Image tree		= Toolkit.getDefaultToolkit().getImage("../assets/Appletree.png");
	Image score_img = Toolkit.getDefaultToolkit().getImage("../assets/score.png");
	Image title 	= Toolkit.getDefaultToolkit().getImage("../assets/apple_catch.png");
	Image game_over = Toolkit.getDefaultToolkit().getImage("../assets/game_over.gif");
	Image heart_apple=Toolkit.getDefaultToolkit().getImage("../assets/heart_apple.png");
	
	
	public void init()	{
		
		//Initializing threads and removing flicker, not much reason to touch anything here		
		off_screen = createImage(1000,700);
		off_g	   = off_screen.getGraphics();
		
		this.requestFocus();
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseListener(this);
		
		t = new Thread(this);
		t.start();
		
		for(int i =0; i<50; i++){
			Apple[i] = new Apple();
		}
	}
		
	public void run() {
		//background_music.Play();
		backgroundMusic.loop();
				
				if (GameState == 2) {
					gameOverSound.Play();	
				}

		
		if (GameState == 6)
		{
			//game_sound.Play();	
		}
//		game_sound.Play();
		while(true)	
		{
			//System.out.println(""+GameState);
			if(GameState == 2) 
			{	
				if(AppleTimer<80)
				{
					AppleTimer++;
				}
				if(AppleTimer == 80)	
				{
					AppleTimer = 0;
					SpawnApples(ArrayIndex);
					ArrayIndex = ArrayIndex+1;
					if(ArrayIndex > 49)
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
				
				
					
				for(int i = 0; i < 50; i++)
				{
					//System.out.println(" "+i);
					if(	
							Apple[i].
							isColiding
							(Basket)
							)
					{
						Apple[i].moveBy(-10000, -1000);
						
						score += Apple[i].points;
					}
					
					//Moves all apples down by 1
					
					/*if(AppleSlowTimer == 3)
					{*/
						Apple[i].moveBy(0, 1*AppleSpeed);
					/*}
					
					if(AppleSlowTimer ==4)
					{
						AppleSlowTimer= 0;
					}*/
				}
				
				
				//Controls Basket BasketSpeed
				if(lePressed) Basket.moveBy(-1*BasketSpeed,0);
				if(riPressed) Basket.moveBy(1*BasketSpeed,0); 
				
				
				//Left Side Basket Boundary
				if(Basket.x <20)
				{
					Basket.x = 20;
				}
				//Right side Basket Boundary
				if(Basket.x >480)
				{
					Basket.x = 480;
				}
			}
			
			if(GameState == 3) 
			{	
				
				
				if(AppleTimer<80)
				{
					AppleTimer++;
				}
				if(AppleTimer == 80)	
				{
					time--;
					AppleTimer = 0;
					SpawnApples(ArrayIndex);
					ArrayIndex = ArrayIndex+1;
					if(ArrayIndex > 49)
					{
						ArrayIndex = 0;
					}
				}
			
				if(time == 0)
				{
					GameState = 6;
				}
					
				for(int i = 0; i < 50; i++)
				{
			
					if(	Apple[i].isColiding(Basket))
					{
						Apple[i].moveBy(-10000, -1000);
						
						score += Apple[i].points;
					}
					
					
						Apple[i].moveBy(0, 1*AppleSpeed);
					
				}
				
				
				//Controls Basket BasketSpeed
				if(lePressed) Basket.moveBy(-3*BasketSpeed,0);
				if(riPressed) Basket.moveBy(3*BasketSpeed,0); 
				
				
				//Left Side Basket Boundary
				if(Basket.x <20)
				{
					Basket.x = 20;
				}
				//Right side Basket Boundary
				if(Basket.x >480)
				{
					Basket.x = 480;
				}
			}
			
			if(GameState == 4) 
			{	
				
				
				if(AppleTimer<80)
				{
					AppleTimer++;
				}
				if(AppleTimer == 80)	
				{
				
					AppleTimer = 0;
					SpawnApples(ArrayIndex);
					ArrayIndex = ArrayIndex+1;
					if(ArrayIndex > 49)
					{
						ArrayIndex = 0;
					}
				}
				
				
			
				if(lives == 0)
				{
					GameState = 6;
				}
				
				 
				
				for(int i = 0; i < 50; i++)
				{
			
					if(	Apple[i].isColiding(Basket))
					{
						Apple[i].moveBy(-10000, -1000);
						
						
						int tempscore = score;
						
						score += Apple[i].points;
						
						if(tempscore>score)
						{
							//deducts a life when taking rotten apples
							lives--;
						}
					}
					
					
						Apple[i].moveBy(0, 1*AppleSpeed);
						if(Apple[i].y>700 && Apple[i].isRotten == false)
						{
							
							
							lives= lives-1;
							if(lives<-36) {lives=3;}
							Apple[i].y = -100000;
						}
					
				}
				
				
				//Controls Basket BasketSpeed
				if(lePressed) Basket.moveBy(-3*BasketSpeed,0);
				if(riPressed) Basket.moveBy(3*BasketSpeed,0); 
				
				
				//Left Side Basket Boundary
				if(Basket.x <20)
				{
					Basket.x = 20;
				}
				//Right side Basket Boundary
				if(Basket.x >480)
				{
					Basket.x = 480;
				}
			}
		
	repaint();
		
			try {
				t.sleep(15);	
				}
			catch(Exception x) {}
		}
	}	
	
	public void update(Graphics g)	{
		off_g.clearRect(0, 0, game_width, game_height);
		paint(off_g);
		g.drawImage(off_screen,0,0,null);
	}
	
	public void paint(Graphics g)	
	{
		this.setSize(game_width, game_height);
		g.setFont(new Font("Roboto Light", Font.PLAIN, 36));		
		g.drawImage(tree, 0,0,game_width,game_height, null);
		
		if(GameState == 0)
		{
//			StartButton.draw(g);
			String welcome = "Press here to start!";
			g.drawImage(title, 40, 30, null);
			g.drawString(welcome, PlayButton.x+25, PlayButton.y+40);
		}
		else if(GameState == 1)
		{
			
			g.drawImage(instruct, 0, 0, game_width, game_height,this);
			Game1Button.drawFull(g);
			Game2Button.drawFull(g);
			Game3Button.drawFull(g);
		}
		else if(GameState == 3)
		{
			String time_str = Integer.toString(time);
			g.drawString(time_str + " ", 450 , 40);
		}
		else if(GameState == 4)
		{
			System.out.println(lives+"");
			for(int i = lives; i>0;i--)
			{
				g.drawImage(heart_apple, 560-40*i, 20,this);
			}
		}
		
		else if(GameState == 6)
		{
			//resets all apples to be offscreen during endstates
			for(int i = 0; i<50;i++)
			{
				Apple[i].y =-100000;
				
			}
			g.setColor(java.awt.Color.black);
			g.drawImage(game_over, 0, 0, null);
			g.drawString("Game Over! Your score was: " + score, 0, 70);
			g.drawImage(score_img, 0,  0,  null);
		}
		if(GameState >1 && GameState<6)
		{
			g.drawImage(score_img,0,0,null);
			for(int i = 0; i < 50; i++){
				Apple[i].draw(g);
			}
			Basket.draw(g);
		}
				
		if(GameState>1)
		{
		String score_str = Integer.toString(score);
		g.drawString(score_str + " ", 110 , 40);
		}
		
		
		HomeButton.drawFull(g);
		
		EndButton.drawFull(g);
		
	}	

	public void SpawnApples(int Index)	{
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
		
		int mx = e.getX();
		int my = e.getY();
//		System.out.println("mx: " + Integer.toString(mx) +" my: " + Integer.toString(my));
		
		if(GameState==0 )
		{
			if(PlayButton.inRect(mx, my))
			{
				GameState=1;
			}
			
			
		}
		
		if(GameState==1 )
		{
			if(Game1Button.inRect(mx, my))
			{
				GameState=2;
			}
			
			if(Game2Button.inRect(mx, my))
			{
				GameState=3;
				
				time = 60;
			}
			
			if(Game3Button.inRect(mx, my))
			{
				GameState=4;
				
				lives = 4;
			}
			
			
		}
		
		if(HomeButton.inRect(mx, my))	{
			GameState = 0;
			score = 0;
		}
		if(EndButton.inRect(mx, my)){
			GameState = 6;
		}
	}

	public void DrawApplesOnTree(Graphics g) {
		g.drawImage(apple1,70,100,40,40,this);
		g.drawImage(apple1,180,70,40,40,this);
		g.drawImage(apple1,300,190,40,40,this);
		g.drawImage(apple1,200,150,40,40,this);
		g.drawImage(apple1,400,120,40,40,this);
		g.drawImage(apple1,100,220,40,40,this);
		g.drawImage(apple1,300,80,40,40,this);
		g.drawImage(apple1,470,200,40,40,this);
		g.drawImage(apple1,425,50,40,40,this);
	}
	
	
	
	public void mouseReleased(MouseEvent e) {		
	}

	public void keyPressed(KeyEvent e)	{
		int code = e.getKeyCode();
	
		if (code == e.VK_A)		lePressed = true;
		if (code == e.VK_D)		riPressed = true;
		
		if (code == e.VK_LEFT)		lePressed = true;
		if (code == e.VK_RIGHT)		riPressed = true;
	}

	public void keyReleased(KeyEvent e)	{
		int code = e.getKeyCode();
		
		if (code == e.VK_A)		lePressed = false;
		if (code == e.VK_D)		riPressed = false;
		
		if (code == e.VK_LEFT)		lePressed = false;
		if (code == e.VK_RIGHT)		riPressed = false;
	}

	public void keyTyped(KeyEvent e) {
		
	}
}	

