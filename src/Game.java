import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javafx.scene.layout.Background;

public class Game extends Applet implements KeyListener, Runnable, MouseListener, MouseMotionListener
{
	/*
	*********************
	TO-DO LIST
	
	- FIX BUCKET SIDES COLLISION WITH APPLES
	-- Sides of bucket should collide with apples
	-- Alternatively consider leaving it and change basket to a critter?
	--- Like a squirrel or something?
	
	- MENU BUTTON DISPLAY
	-- Make it button?
	
	- SCORE DISPLAY
	-- Add graphic to left of it? Red apple?
	
	- PUSH TO START DISPLAY
	-- Make it button? Need some design ideas here.
	-- Make it just say "START" ?
	
	////////////////////
	 
	BUGS
	
	-	
	
	///////////////////
	
	EXTRA (POTENTIAL) FEATURES
	
	- SOUND EFFECTS FOR CATCHING APPLES
	-- Different sounds for different apples - good sound and bad sound
	== Set something very basic up for all apples just to test it
	=== Little buggy/delayed
	
	- BUCKET STARTS "FILLING UP" AFTER CERTAIN SCORE
	
	- LET PLAYER PICK 'CHARACTER'
	-- Think it would be fairly simple
	
	- ADD LIVES?
	
	///////////////////
	 
	NOTES

	- PLAYER CAN CONTROL SPEED OF BASKET WITH 'UP' AND 'DOWN' ARROW KEYS
	== 'UP' to increase by 1, 'DOWN' to increase
	
	- PLAYER CAN SLOW DOWN APPLES WITH SPACEBAR
	== Just messing with mechanics, we can choose to keep it or not
	
	- MOVED SCORE TO BOTTOM RIGHT
	== Looks a little better? We can keep messing with it
		
	(DELETE THIS NOTE LATER)	
		
	*/
	
	final int game_width = 563;
	final int game_height = 700; 
	
	Image	off_screen;
	Graphics off_g;	  
	
	Thread t;
	
	int AppleTimer 	= 100;
	int GameState 	= 0;
	int ArrayIndex 	= 0;
	int AppleSlowTimer = 0;
	
	int lives;
	int time;
	int score;
	
	// Basket Options
	int basketSpeed = 5;
	int slowBasketSpeed = 1;
	int fastBasketSpeed = 10;
	
	// Apples Options
	int applesSpeed = 4;
	int fastApplesSpeed = 4;
	int slowApplesSpeed = 2;
	
	// Keys
	boolean lePressed = false;
	boolean riPressed = false;
	boolean spaceBarPressed = false;
	boolean downPressed = false;
	boolean upPressed = false;
	
	int numOfApples = 50;
	
	Apple[] apples = new Apple[numOfApples];
	Basket Basket = new Basket(game_width/2, (int)(0.85*game_height));
	Image apple1 = Toolkit.getDefaultToolkit().createImage("../assets/red_apple.png");

	// Audio
	BGM gameOverSound 		= new BGM("../assets/game_over");
	BGM background_music	= new BGM("../assets/background_music");
	Sound gameOver 			= new Sound("../assets/game_over.wav");
	Sound backgroundMusic 	= new Sound("../assets/background_music.wav");
	Sound coinSound	        = new Sound("../assets/coin.wav");
	
	Rect tree_rect 		= new Rect(20,20,500,200);
	Rect StartButton 	= new Rect(110,350,350,75);
	Rect MainButton 	= new Rect(50,650,50,30);
	Rect EndButton 		= new Rect(500,650,50,30);
	
	Image tree 		= Toolkit.getDefaultToolkit().getImage("../assets/Appletree.png");
	Image score_img = Toolkit.getDefaultToolkit().getImage("../assets/score.png");
	Image title 	= Toolkit.getDefaultToolkit().getImage("../assets/apple_catch.png");
	Image game_over = Toolkit.getDefaultToolkit().getImage("../assets/game_over.gif");
	Image apple 	= Toolkit.getDefaultToolkit().createImage("../assets/red_apple.png");
	Image start_img = Toolkit.getDefaultToolkit().createImage("../assets/start_button.png");
	
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
		
		for(int i =0; i<numOfApples; i++){
			apples[i] = new Apple();
		}
	}
		
	public void run() {
		
		//background_music.Play();
		backgroundMusic.loop();
		
		if (GameState == 2) {
			gameOverSound.Play();	
		}

		while(true)		{
			
			if(GameState==1){
				if(AppleTimer<80){
					AppleTimer++;
				}
				if(AppleTimer >= 80)	{
					AppleTimer = 0;
					SpawnApples(ArrayIndex);
					ArrayIndex = ArrayIndex+1;
					if(ArrayIndex > 49){
						ArrayIndex = 0;
					}
				}
				if(AppleTimer<80){
					AppleTimer++;
				}
				if(AppleTimer >= 80)	{
					AppleTimer = 0;
					SpawnApples(ArrayIndex);
					ArrayIndex = ArrayIndex+1;
					if(ArrayIndex > 49){
						ArrayIndex = 0;
					}
				}
				if(AppleTimer<80){
					AppleTimer++;
				}
				if(AppleTimer >= 80)	{
					AppleTimer = 0;
					SpawnApples(ArrayIndex);
					ArrayIndex = ArrayIndex+1;
					if(ArrayIndex > 49){
						ArrayIndex = 0;
					}
				}

				//Checks for collision with baskets each frame, and increases your score by the value of the apple
				
				/*AppleSlowTimer++;
				if(AppleSlowTimer ==3)
				{
					AppleSlowTimer= 0;
				}*/
				for(int i = 0; i < numOfApples; i++)
				{
					//System.out.println(" "+i);
					if(	apples[i].isColiding(Basket)){
						apples[i].moveBy(-10000, -1000);
						coinSound.play(); 
						score += apples[i].points;
					}
					
					//Moves all apples down by 1
					
					/*if(AppleSlowTimer == 3)
					{*/
						apples[i].moveBy(0, applesSpeed);
					/*}
					
					if(AppleSlowTimer ==4)
					{
						AppleSlowTimer= 0;
					}*/
				}
				
				//Controls Basket Speed
				if(lePressed) Basket.moveBy(-1*basketSpeed,0);
				if(riPressed) Basket.moveBy(1*basketSpeed,0); 
				// User can slow down speed by holding down spacebar
				if(spaceBarPressed) applesSpeed = slowApplesSpeed;
				else applesSpeed = fastApplesSpeed;
				
				if(downPressed) basketSpeed -= 1;
				if(upPressed) basketSpeed += 1;
				if(basketSpeed<1) {
					basketSpeed = 1;
				}
				if(basketSpeed>15) {
					basketSpeed = 15;
				}

				//Left Side Basket Boundary
				if(Basket.x <20){
					Basket.x = 20;
				}
				//Right side Basket Boundary
				if(Basket.x >480){
					Basket.x = 480;
				}
			}
			repaint();

			if (GameState == 2){
				backgroundMusic.stop();
				gameOver.loop();
			}
		
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
	
	public void paint(Graphics g)	{


		this.setSize(game_width, game_height);
		g.setFont(new Font("Roboto Light", Font.TRUETYPE_FONT, 34));		
		g.drawImage(tree, 0,0, null);
		
		if(GameState == 0){
//			StartButton.draw(g);
			DrawApplesOnTree(g);
			String welcome = "Press here to start!";
			g.drawImage(title, 40, 30, null);
			g.drawImage(start_img, StartButton.x, StartButton.y, null);
		}
		else if(GameState==1){
			g.drawImage(tree, 0,0, null);
			DrawApplesOnTree(g);
			for(int i = 0; i < numOfApples; i++){
				apples[i].draw(g);
			}
			Basket.draw(g);
			g.setColor(Color.BLACK);
			String score_str = Integer.toString(score);
			g.drawString(score_str + " ", 430 , 630);
			g.drawImage(score_img, 430,  630,  null);
			
			g.drawString("" + basketSpeed, 270 , 530);

		}
		else if(GameState == 2){
			DrawApplesOnTree(g);
			g.setColor(java.awt.Color.black);
			g.drawImage(game_over, 30, 0, null);

			g.drawString("Your score was: ", 150, 400);
			g.drawString("" + score, 250, 450);
			g.drawImage(score_img, 0,  0,  null);
		}
		MainButton.draw(g);
				
	}	

	public void SpawnApples(int Index)	{
		apples[Index] = new Apple(Index);
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
		System.out.println("mx: " + Integer.toString(mx) +" my: " + Integer.toString(my));
		if(GameState==0){
			if(StartButton.inRect(mx, my)){
				GameState = 1;
			}
		}
		
		if(MainButton.inRect(mx, my))	{			
			GameState = 0;
			score = 0;

			gameOver.stop();
			backgroundMusic.play();
			for (int i = 0; i < apples.length; i++) {
				apples[i].x = -100;
				apples[i].y = 0;
			}
		}
		if(EndButton.inRect(mx, my)){
			GameState = 2;

			 
		}
	}

	public void mouseReleased(MouseEvent e) {		
	}

	public void keyPressed(KeyEvent e)	{
		int code = e.getKeyCode();
	
		if (code == e.VK_A)		lePressed = true;
		if (code == e.VK_D)		riPressed = true;
		
		if (code == e.VK_LEFT)		lePressed = true;
		if (code == e.VK_RIGHT)		riPressed = true;
		
		if (code == e.VK_SPACE)		spaceBarPressed = true;
		
		if (code == e.VK_DOWN)			downPressed = true;
		if (code == e.VK_UP)			upPressed = true;

	}

	public void keyReleased(KeyEvent e)	{
		int code = e.getKeyCode();
		
		if (code == e.VK_A)		lePressed = false;
		if (code == e.VK_D)		riPressed = false;
		

		if (code == e.VK_LEFT)		lePressed = false;
		if (code == e.VK_RIGHT)		riPressed = false;
		
		if (code == e.VK_SPACE)		spaceBarPressed = false;
		
		if (code == e.VK_DOWN)			downPressed = false;
		if (code == e.VK_UP)			upPressed = false;

	}

	public void keyTyped(KeyEvent e) {
	}	
}
