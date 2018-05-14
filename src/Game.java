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
import java.util.concurrent.TimeUnit;

public class Game extends Applet implements KeyListener, Runnable, MouseListener, MouseMotionListener {
	/*
	 *********************
	TO-DO LIST
	
	Add more sounds
	////////////////////
		BUGS
	
	///////////////////
	EXTRA (POTENTIAL) FEATURES
	- SOUND EFFECTS FOR CATCHING APPLES
	-- Different sounds for different apples - good sound and bad sound
	== Set something very basic up for all apples just to test it
	
	- BUCKET STARTS "FILLING UP" AFTER CERTAIN SCORE   
	
	-Might add a thing that shows all the apples you got during the score screen (luke)
	
	///////////////////
		NOTES
	- PLAYER CAN CONTROL SPEED OF BASKET WITH 'UP' AND 'DOWN' ARROW KEYS
	== 'UP' to increase by 1, 'DOWN' to increase
	- PLAYER CAN SLOW DOWN APPLES WITH SPACEBAR
	== Just messing with mechanics, we can choose to keep it or not
	(DELETE THIS NOTE LATER)
	
	
	-- Summary of current game states:
		- State 0: Main game start screen 
		- State 1: Mode selection
		- State 2: Infinite times and lifes.
		- State 3: Against the clock
		- State 4: Rotten fest (survival)
		- State 5: Shoot apples down (WIP)
	 */

	final int game_width = 563;
	final int game_height = 700;
	
	// Game modes.
	final int start_screen 		= 0;
	final int mode_selection 	= 1;
	final int infinite_mode 	= 2;
	final int timed_mode		= 3;
	final int rotten_fest		= 4;
	final int sky_shooter		= 5;
	final int end_screen 		= 6;
	
	Image	off_screen;
	Graphics off_g;

	Thread t;
	
	int mouseX;
	int mouseY;

	
	int timeTimer = 0;
	int endTimer = 0;
	int musicTestTimer=0;
	int AppleTimer 	= 30;
	int AppleSlowTimer = 0;	
	boolean GameOverChecker =false;
	
	int GameState 	= start_screen;
	int ArrayIndex 	= 0;
	
	int lives;
	int time = 0;
	int score;
	int scorechecker = 1;

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
	Image lifeApple =  Toolkit.getDefaultToolkit().createImage("../assets/rotten_apple.png");
	Image timeApple =  Toolkit.getDefaultToolkit().createImage("../assets/rotten_apple.png");
	
	// Keys
	boolean lePressed = false;
	boolean riPressed = false;
	boolean spaceBarPressed = false;
	boolean downPressed = false;
	boolean upPressed = false;
	
	
	
	Apple[] Apple = new Apple[50];
	Apple[] red_apples = new Apple[15];
	Basket Basket = new Basket(game_width/2, (int)(0.85*game_height));
	
	Rect tree_rect 		= new Rect(20,   20,  500, 	200);
	Rect PlayButton 	= new Rect(110, 430,  353,  110);
	Rect HomeButton 	= new Rect(0,  	620,  134, 	105);
	Rect EndButton 		= new Rect(450, 620,  137,  113);
	Rect InfiniteButton	= new Rect(115, 140,  300,  94);
	Rect TimerButton	= new Rect(115, 240,  300,  94);
	Rect RottenButton	= new Rect(115, 340,  300,  94);
	Rect SkyButton 		= new Rect(80,	440,  300,  94);

	//Button images:
	Image PlayImage 	= Toolkit.getDefaultToolkit().getImage("../assets/start_game.png");
	Image MenuImage 	= Toolkit.getDefaultToolkit().getImage("../assets/menu.png");
	Image EndImage 		= Toolkit.getDefaultToolkit().getImage("../assets/quit.png");
	Image InfiniteImage = Toolkit.getDefaultToolkit().getImage("../assets/infinite.png");
	Image TimerImage 	= Toolkit.getDefaultToolkit().getImage("../assets/against_the_clock.png");
	Image RottenImage 	= Toolkit.getDefaultToolkit().getImage("../assets/rotten_fest.png");
	Image SkyImage		= Toolkit.getDefaultToolkit().getImage("../assets/shooting_mode.png");
	
	// Audio
	Sound gameOver 			= new Sound("../assets/game_over.wav");
	Sound backgroundMusic 	= new Sound("../assets/background_music.wav");
	Sound coinSound	        = new Sound("../assets/coin.wav");
	Sound ShootingSound 	= new Sound("../assets/Sniper.wav");

	Image Instructions 	= Toolkit.getDefaultToolkit().getImage("../assets/instructions.png");
	Image TreeBG		= Toolkit.getDefaultToolkit().getImage("../assets/tree_bg.png");
	Image AltBG			= Toolkit.getDefaultToolkit().getImage("../assets/alt_bg.png");
	Image ScoreImage 	= Toolkit.getDefaultToolkit().getImage("../assets/score.png");
	Image Logo 			= Toolkit.getDefaultToolkit().getImage("../assets/apple_catch.png");
	Image GameOver 		= Toolkit.getDefaultToolkit().getImage("../assets/game_over.gif");
	Image HeartApple 	=Toolkit.getDefaultToolkit().getImage("../assets/heart_apple.png");
	Image Scope			= Toolkit.getDefaultToolkit().getImage("../assets/Scope.png");
	Image displayBar =  Toolkit.getDefaultToolkit().createImage("../assets/displaybar.png");
	
	public void init()	{

		//Initializing threads and removing flicker, not much reason to touch anything here
		off_screen = createImage(1000,700);
		off_g	   = off_screen.getGraphics();

		this.requestFocus();
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		t = new Thread(this);
		t.start();

		for(int i =0; i<50; i++){
			Apple[i] = new Apple();
		}
		for (int i = 0; i < red_apples.length; i++) {
			red_apples[i] = new Apple("red");
		}
	}

	public void run() {
//		
		while(true)	{
			if(musicTestTimer == 0)
			{
				backgroundMusic.play();
			}
			
//			//System.out.println(""+GameState);
			musicTestTimer++;
			if(musicTestTimer == 4000)
			{
				
				musicTestTimer = 0;
			}
			
			if(GameState==6 && GameOverChecker == false) 
				{
					gameOver.play();
					GameOverChecker = true;
				}
			
			if(GameState == infinite_mode) 	{
				this.pushApples(3);
				//Checks for collision with baskets each frame, and increases your score by the value of the apple

				/*AppleSlowTimer++;
				if(AppleSlowTimer ==3)	{
					AppleSlowTimer= 0;
				}*/
				for(int i = 0; i < 50; i++)	{
					if(	Apple[i].isColiding(Basket)) {
						Apple[i].moveBy(-10000, -1000);
						if(Apple[i].isRotten==false)
							{
								coinSound.stop();
								coinSound.play();
							}
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
				
				// User can slow down speed by holding down space bar
				if(spaceBarPressed) applesSpeed = slowApplesSpeed; 
				else 				applesSpeed = fastApplesSpeed;

				if(downPressed) 	basketSpeed -= 1;
				if(upPressed) 		basketSpeed += 1;
				if(basketSpeed<1) 	basketSpeed = 1;
				if(basketSpeed>15) 	basketSpeed = 15;

			}
			
			
			
			if(GameState == timed_mode) {
				
				timeTimer++;
				if(timeTimer == 66)
				{
					time--;
					timeTimer=0;
					
				}
				this.pushApples(2);
				if(time == 0){
					GameState = end_screen;
				}
				for(int i = 0; i < 50; i++)	{
					if(	Apple[i].isColiding(Basket)){
						if(Apple[i].isRotten==false)
							{
								coinSound.stop();
								coinSound.play();
							}
						Apple[i].moveBy(-10000, -1000);
						score += Apple[i].points;
					}
					Apple[i].moveBy(0, 1*applesSpeed);
				}
			}
			
			
			
			
			
			if(GameState == rotten_fest) {
				this.pushApples(3);
				if(lives == 0)	GameState = end_screen;
				
				for(int i = 0; i < 50; i++)	{
					if(	Apple[i].isColiding(Basket)) {
						if(Apple[i].isRotten==false)
							{	
								coinSound.stop();
								coinSound.play();
							}
						Apple[i].moveBy(-10000, -100000);
						score += Apple[i].points;
						if (Apple[i].isRotten)lives--;
						
					}
					Apple[i].moveBy(0, 1*applesSpeed);
					if((Apple[i].y>700) && (Apple[i].isRotten == false)){
						lives -= 1;
						
						
						//System.out.println("lost life:drop" + i +" x: "+ Apple[i].x);
						Apple[i].y = -100000;
					}
				}
			}
			
			
			
			
			
			if(GameState == sky_shooter) 
			{		
				timeTimer++;
				if(timeTimer == 66)
				{
					time--;
					timeTimer=0;
					
				}
				if(time==0)
				{
					GameState=6;
				}
				
				this.pushApples(2);
				for(int i = 0; i < 50; i++)	{
				Apple[i].moveBy(0, 1*applesSpeed);
				}
			}
			
			
			
			
			//Basket speed
			if(lePressed) Basket.moveBy(-1*basketSpeed,0);
			if(riPressed) Basket.moveBy(1*basketSpeed,0);
			// Basket limiter.
			if(Basket.x <20) 	Basket.x = 20;
			if(Basket.x >480)	Basket.x = 480;
		
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
		
		if(mode_selection < GameState && GameState < end_screen) {
			g.drawImage(TreeBG, 0,0,game_width,game_height, null);
		}
		else {
			g.drawImage(AltBG, 0,0,game_width,game_height, null);
		}
		if(GameState == start_screen)	{

			g.drawImage(Logo, 40, 70, null);
			g.drawImage(PlayImage, (int)PlayButton.x, (int)PlayButton.y, null);
		}
		else if(GameState == mode_selection)	{
			
			g.drawImage(Instructions, 0, 0, game_width, game_height,this);
			g.drawImage(InfiniteImage, 	(int)InfiniteButton.x, 	(int) InfiniteButton.y, null);
			g.drawImage(TimerImage, 	(int)TimerButton.x, 	(int) TimerButton.y, 	null);
			g.drawImage(RottenImage, 	(int)RottenButton.x, 	(int) RottenButton.y, 	null);
			g.drawImage(SkyImage, 	    (int)SkyButton.x,     	(int) SkyButton.y, 	null);
		}
		
		else if(GameState == infinite_mode)	{
			g.drawImage(displayBar, 0, 0, null);
		}
		
		else if(GameState == timed_mode)	{
			g.drawImage(displayBar, 0, 0, null);
			String time_str = Integer.toString(time);
			g.drawString(time_str + " ", 450 , 35);
			g.setColor(java.awt.Color.white);
		}
		else if(GameState == rotten_fest)	{
//			//System.out.println(lives+"");
			g.drawImage(displayBar, 0, 0, null);
			for(int i = lives; i > 0; i--)	{
				g.drawImage(HeartApple , 560-(40*i),  0, 40, 40, this);
			}
			//g.setColor(java.awt.Color.white);
		}
		/*else if(GameState == sky_shooter) {
			for (int i = 0; i < red_apples.length; i++) {
				red_apples[i].draw(g);
			}
			ball1.draw(g);
		} */
		else if(GameState == sky_shooter) {
			g.drawImage(displayBar, 0, 0, null);
			String time_str = Integer.toString(time);
			g.drawString(time_str + " ", 450 , 35);
			//g.setColor(java.awt.Color.white);
			//g.drawImage(Scope,mouseX-50,mouseY-50,100,100,this);
			
		}
		
		else if(GameState == end_screen)	{
			this.resetApples();
			g.setColor(java.awt.Color.yellow);
			g.drawImage(GameOver, 30, 0, null);
			g.drawString("Game Over! Your score was: " + score, 5, 400);
			g.drawImage(ScoreImage, 0,  0, 80, 40,  this);
			
			
			int locationX = 0;
			int locationY = 0;
			
			int tempscore = score;
			
			
			while(scorechecker<tempscore )
			{
				scorechecker=2;
				if(tempscore>200)
				{
					g.drawImage(goldApple,5+locationX,450+locationY,this);
					tempscore= tempscore-100;
					locationX+=40;
				}
				if(tempscore<=200)
				{
					g.drawImage(redApple,5+locationX,450+locationY,this);
					tempscore = tempscore-25;
					locationX+=40;
				}
				if(tempscore<50)gameOver.play();
				
				
				if(locationX > 520)
				{
					locationX = 0;
					locationY = locationY+40;
				}
			}
			
			
			
		}
		if((GameState > mode_selection) && (GameState < end_screen))	{
			
			g.drawImage(ScoreImage,0,0, 80, 40, null);
			drawApplesOnTree(g);
			for(int i = 0; i < 50; i++){
				Apple[i].draw(g);
			}
			if(GameState != 5)Basket.draw(g);
			else g.drawImage(Scope,mouseX-50,mouseY-50,100,100,this);
			g.setColor(java.awt.Color.white);
			String score_str = Integer.toString(score);
			
			
			//g.drawString("" + basketSpeed, 270 , 530);
		}
		if(GameState > mode_selection){
			
			String score_str = Integer.toString(score);
			g.drawString(score_str + " ", 90 , 35);
		}
		if(GameState >1 && GameState<6)
		{
			g.drawImage(EndImage, 	(int)EndButton.x,  (int) EndButton.y, null);
		}
		if(GameState>1)
		{
			g.drawImage(MenuImage, 	(int)HomeButton.x, (int) HomeButton.y, null);
		}
		

	}

	public void SpawnApples(int Index)	{
		Apple[Index] = new Apple(Index);
	}
	
	public void pushApples(int number_of_apples) {
		for(int i = 0; i < number_of_apples; i++) {
			if(AppleTimer < 80){
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
		}
	}
	public void resetApples() 
	{
		for(int i = 0; i < Apple.length; i++){
			Apple[i].y =-100000;
		}
		GameOverChecker = false;
		score = 0;
	}
	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		//System.out.println(e.getX());
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
		//System.out.println("mx: " + Integer.toString(mx) +" my: " + Integer.toString(my));
		if(GameState == start_screen) {
			if(PlayButton.inRect(mx, my)){
				GameState = mode_selection;
				resetApples();
			}
		}
		else if(GameState == mode_selection) {
			if(InfiniteButton.inRect(mx, my)){
				GameState = infinite_mode;
				resetApples();
			}
			if(TimerButton.inRect(mx, my))	{
				GameState = timed_mode;
				time = 60;
				resetApples();
			}
			if(RottenButton.inRect(mx, my))	{
				GameState = rotten_fest;
				lives = 3;
				resetApples();
			}
			if(SkyButton.inRect(mx, my)) {
				GameState = sky_shooter;
				resetApples();
				time=10;
			}
		}

		else if(HomeButton.inRect(mx, my))	{
			GameState = start_screen;
			score = 0;
			resetApples();
			this.resetApples();
		}
		else if(EndButton.inRect(mx, my)){
			GameState = end_screen;
		}
		
		if(GameState == 5)
		{
			ShootingSound.stop();
			ShootingSound.play(); 
			
			for(int i = 0; i < 50; i++)	
			{
				if(Apple[i].inRect(mx, my))
				{
					Apple[i].y = -10000;
					score+=Apple[i].points;
					time+=2;
				}
			}
			
		}
		
	}

	
	
	
	public void drawApplesOnTree(Graphics g) {
		int appleWidth = 40;
		int appleHeight = 45;

		g.drawImage(greenApple,70,100,appleWidth/2,appleHeight/2,this);
		g.drawImage(redApple,180,70,appleWidth/2,appleHeight/2,this);
		g.drawImage(greenApple,300,190,appleWidth/2,appleHeight/2,this);
		g.drawImage(redApple,200,150,appleWidth/2,appleHeight/2,this);
		g.drawImage(greenApple,400,120,appleWidth/2,appleHeight/2,this);
		g.drawImage(redApple,100,220,appleWidth/2,appleHeight/2,this);
		g.drawImage(goldApple,300,80,appleWidth/2,appleHeight/2,this);
		g.drawImage(redApple,470,200,appleWidth/2,appleHeight/2,this);
		g.drawImage(redApple,425,50,appleWidth/2,appleHeight/2,this);
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
