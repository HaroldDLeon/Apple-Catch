import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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
	== Keep it bottom right or top left?

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

	(DELETE THIS NOTE LATER)

	 */

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

	// Basket Options
	int basketSpeed = 5;
	int slowBasketSpeed = 1;
	int fastBasketSpeed = 10;

	// Apples Options
	int applesSpeed = 4;
	int fastApplesSpeed = 4;
	int slowApplesSpeed = 2;

	// Apple Images
	Image redApple = Toolkit.getDefaultToolkit().createImage("../assets/red_apple.png");
	Image greenApple = Toolkit.getDefaultToolkit().createImage("../assets/green_apple.png");
	Image goldApple = Toolkit.getDefaultToolkit().createImage("../assets/gold_apple.png");
	Image rottenApple = Toolkit.getDefaultToolkit().createImage("../assets/rotten_apple.png");

	// Keys
	boolean lePressed = false;
	boolean riPressed = false;
	boolean spaceBarPressed = false;
	boolean downPressed = false;
	boolean upPressed = false;

	Apple[] Apple = new Apple[50];
	Basket Basket = new Basket(game_width/2, (int)(0.85*game_height));

	Rect tree_rect 		= new Rect(20,20,500,200);
	Rect PlayButton 	= new Rect(110,350,350,75);
	Rect HomeButton 	= new Rect(50,650,50,30);
	Rect EndButton 		= new Rect(500,650,50,30);
	Rect Game1Button	= new Rect(110,0,100,75);
	Rect Game2Button	= new Rect(250,0,100,75);
	Rect Game3Button	= new Rect(410,0,100,75);

	// Audio
	// BGM gameOverSound 		= new BGM("../assets/game_over");
	// BGM background_music	= new BGM("../assets/background_music");
	Sound gameOver 			= new Sound("../assets/game_over.wav");
	Sound backgroundMusic 	= new Sound("../assets/background_music.wav");
	Sound coinSound	        = new Sound("../assets/coin.wav");

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
		backgroundMusic.loop();
		while(true)	{
			//System.out.println(""+GameState);
			if(GameState == 2) 	{
				// TEMP MEASURE TO GET MORE APPLES TO FALL AT SAME TIME
				// TEST
				if(AppleTimer<80)	{
					AppleTimer++;
				}
				if(AppleTimer == 80) {
					AppleTimer = 0;
					SpawnApples(ArrayIndex);
					ArrayIndex = ArrayIndex+1;
					if(ArrayIndex > 49)
					{
						ArrayIndex = 0;
					}
				}
				if(AppleTimer<80){
					AppleTimer++;
				}
				if(AppleTimer == 80) {
					AppleTimer = 0;
					SpawnApples(ArrayIndex);
					ArrayIndex = ArrayIndex+1;
					if(ArrayIndex > 49)	{
						ArrayIndex = 0;
					}
				}
				if(AppleTimer<80) {
					AppleTimer++;
				}
				if(AppleTimer == 80) {
					AppleTimer = 0;
					SpawnApples(ArrayIndex);
					ArrayIndex = ArrayIndex+1;
					if(ArrayIndex > 49)	{
						ArrayIndex = 0;
					}
				}

				//Checks for collision with baskets each frame, and increases your score by the value of the apple

				/*AppleSlowTimer++;
				if(AppleSlowTimer ==3)
				{
					AppleSlowTimer= 0;
				}*/
				for(int i = 0; i < 50; i++)	{
					if(	Apple[i].isColiding(Basket)) {
						Apple[i].moveBy(-10000, -1000);
						coinSound.play();
						score += Apple[i].points;
					}

					//Moves all apples down by 1
					/*if(AppleSlowTimer == 3)	{*/
					Apple[i].moveBy(0, 1*applesSpeed);
					/*}
					if(AppleSlowTimer ==4)	{
						AppleSlowTimer= 0;
					}*/
				}

				//Controls Basket Speed
				if(lePressed) {
					Basket.moveBy(-1*basketSpeed,0);
				}
				if(riPressed) {
					Basket.moveBy(1*basketSpeed,0);
				}

				// User can slow down speed by holding down spacebar
				if(spaceBarPressed) {
					applesSpeed = slowApplesSpeed;
				} else {
					applesSpeed = fastApplesSpeed;
				}
				if(downPressed) {
					basketSpeed -= 1;
				}
				if(upPressed) {
					basketSpeed += 1;
				}
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
			if(GameState == 3) 	{
				if(AppleTimer<80) {
					AppleTimer++;
				}
				if(AppleTimer == 80) {
					time--;
					AppleTimer = 0;
					SpawnApples(ArrayIndex);
					ArrayIndex = ArrayIndex+1;
					if(ArrayIndex > 49)	{
						ArrayIndex = 0;
					}
				}
				if(time == 0){
					GameState = 6;
				}
				for(int i = 0; i < 50; i++)	{
					if(	Apple[i].isColiding(Basket)){
						Apple[i].moveBy(-10000, -1000);
						score += Apple[i].points;
					}
					Apple[i].moveBy(0, 1*applesSpeed);
				}

				//Controls Basket BasketSpeed
				if(lePressed) {
					Basket.moveBy(-1*basketSpeed,0);
				}
				if(riPressed) {
					Basket.moveBy(1*basketSpeed,0);
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
			if(GameState == 4) {
				if(AppleTimer<80) {
					AppleTimer++;
				}
				if(AppleTimer == 80) {
					AppleTimer = 0;
					SpawnApples(ArrayIndex);
					ArrayIndex = ArrayIndex+1;
					if(ArrayIndex > 49)	{
						ArrayIndex = 0;
					}
				}
				if(lives == 0)	{
					GameState = 6;
				}
				for(int i = 0; i < 50; i++)	{
					if(	Apple[i].isColiding(Basket)) {
						Apple[i].moveBy(-10000, -1000);
						int tempscore = score;
						score += Apple[i].points;
						if(tempscore > score)	{
							//deducts a life when taking rotten apples
							lives--;
						}
					}
					Apple[i].moveBy(0, 1*applesSpeed);
					if((Apple[i].y>700) && (Apple[i].isRotten == false)){
						lives= lives-1;
						if(lives<-36) {lives=3;}
						Apple[i].y = -100000;
					}
				}

				//Controls Basket BasketSpeed
				if(lePressed) {
					Basket.moveBy(-1*basketSpeed,0);
				}
				if(riPressed) {
					Basket.moveBy(1*basketSpeed,0);
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

	public void paint(Graphics g){
		this.setSize(game_width, game_height);
		g.setFont(new Font("Roboto Light", Font.PLAIN, 36));
		g.drawImage(tree, 0,0,game_width,game_height, null);
		drawApplesOnTree(g);

		if(GameState == 0)	{
			//			StartButton.draw(g);
			String welcome = "Press here to start!";
			g.drawImage(title, 40, 30, null);
			g.drawString(welcome, PlayButton.x+25, PlayButton.y+40);
		}
		else if(GameState == 1)	{
			g.drawImage(instruct, 0, 0, game_width, game_height,this);
			Game1Button.drawFull(g);
			Game2Button.drawFull(g);
			Game3Button.drawFull(g);
		}
		else if(GameState == 3)	{
			String time_str = Integer.toString(time);
			g.drawString(time_str + " ", 450 , 40);
		}
		else if(GameState == 4)	{
			System.out.println(lives+"");
			for(int i = lives; i > 0; i--)	{
				g.drawImage(heart_apple, 560-(40*i), 20,this);
			}
		}

		else if(GameState == 6)	{
			this.resetApples();
			g.setColor(java.awt.Color.black);
			g.drawImage(game_over, 30, 0, null);
			g.drawString("Game Over! Your score was: " + score, 0, 70);
			g.drawImage(score_img, 0,  0,  null);
		}
		if((GameState >1) && (GameState<6))	{
			g.drawImage(score_img,0,0,null);
			for(int i = 0; i < 50; i++){
				Apple[i].draw(g);
			}
			Basket.draw(g);
			g.setColor(Color.BLACK);
			String score_str = Integer.toString(score);
			g.drawString(score_str + " ", 430 , 630);
			g.drawImage(score_img, 430,  630,  null);
			g.drawString("" + basketSpeed, 270 , 530);
		}
		if(GameState>1){
			String score_str = Integer.toString(score);
			g.drawString(score_str + " ", 110 , 40);
		}

		HomeButton.drawFull(g);
		EndButton.drawFull(g);

	}

	public void SpawnApples(int Index)	{
		Apple[Index] = new Apple(Index);
	}
	public void resetApples() {
		for(int i = 0; i < Apple.length; i++){
			Apple[i].y =-100000;
		}
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

		// Don't comment out until we finish setting all images
		System.out.println("mx: " + Integer.toString(mx) +" my: " + Integer.toString(my));

		if(GameState==0) {
			if(PlayButton.inRect(mx, my)){
				GameState=1;
			}
		}
		if(GameState == 1) {
			if(Game1Button.inRect(mx, my))	{
				GameState = 2;
			}
			if(Game2Button.inRect(mx, my))	{
				GameState = 3;
				time = 60;
				
			}
			if(Game3Button.inRect(mx, my))	{
				GameState=4;
				lives = 4;
			}
		}
		if(HomeButton.inRect(mx, my))	{
			GameState = 0;
			score = 0;
			this.resetApples();
		}
		if(EndButton.inRect(mx, my)){
			GameState = 6;
		}
	}
	public void drawApplesOnTree(Graphics g) {
		int appleWidth = 40;
		int appleHeight = 45;

		g.drawImage(greenApple,70,100,appleWidth,appleHeight,this);
		g.drawImage(redApple,180,70,appleWidth,appleHeight,this);
		g.drawImage(greenApple,300,190,appleWidth,appleHeight,this);
		g.drawImage(redApple,200,150,appleWidth,appleHeight,this);
		g.drawImage(greenApple,400,120,appleWidth,appleHeight,this);
		g.drawImage(redApple,100,220,appleWidth,appleHeight,this);
		g.drawImage(goldApple,300,80,appleWidth,appleHeight,this);
		g.drawImage(redApple,470,200,appleWidth,appleHeight,this);
		g.drawImage(redApple,425,50,appleWidth,appleHeight,this);
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void keyPressed(KeyEvent e)	{
		int code = e.getKeyCode();

		if (code == e.VK_A)		lePressed = true;
		if (code == e.VK_D)		riPressed = true;
		if (code == e.VK_LEFT) 	lePressed = true;
		if (code == e.VK_RIGHT)	riPressed = true;	
		if (code == e.VK_SPACE) spaceBarPressed = true;
		if (code == e.VK_DOWN)	downPressed = true;
		if (code == e.VK_UP) 	upPressed = true;
	}

	public void keyReleased(KeyEvent e)	{
		int code = e.getKeyCode();
		if (code == e.VK_A)		lePressed = false;
		if (code == e.VK_D)		riPressed = false;
		if (code == e.VK_LEFT) 	lePressed = false;
		if (code == e.VK_RIGHT)	riPressed = false;	
		if (code == e.VK_SPACE) spaceBarPressed = false;
		if (code == e.VK_DOWN)	downPressed = false;
		if (code == e.VK_UP) 	upPressed = false;
	}

	public void keyTyped(KeyEvent e) {

	}
}

