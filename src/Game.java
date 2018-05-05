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
	int time;
	int speed = 1;
	int score;
	
	boolean lePressed = false;
	boolean riPressed = false;
	
	Apple[] Apple = new Apple[50];
	Basket Basket = new Basket(game_width/2, (int)(0.85*game_height));
	BGM game_sound = new BGM("../assets/game_over");
	
	Rect tree_rect 		= new Rect(20,20,500,200);
	Rect StartButton 	= new Rect(110,350,350,75);
	Rect MainButton 	= new Rect(50,650,50,30);
	Rect EndButton 		= new Rect(500,650,50,30);
	
	Image tree 		= Toolkit.getDefaultToolkit().getImage("../assets/Appletree.png");
	Image score_img = Toolkit.getDefaultToolkit().getImage("../assets/score.png");
	Image title 	= Toolkit.getDefaultToolkit().getImage("../assets/apple_catch.png");
	Image game_over = Toolkit.getDefaultToolkit().getImage("../assets/game_over.gif");

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
		if (GameState == 2){
			game_sound.Play();	
		}
//		game_sound.Play();
		while(true)		{
			if(GameState>0){
				if(AppleTimer<80){
					AppleTimer++;
				}
				if(AppleTimer == 80)	{
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
				for(int i = 0; i < 50; i++)
				{
					//System.out.println(" "+i);
					if(	Apple[i].isColiding(Basket)){
						Apple[i].moveBy(-10000, -1000);
						
						score += Apple[i].points;
					}
					
					//Moves all apples down by 1
					
					/*if(AppleSlowTimer == 3)
					{*/
						Apple[i].moveBy(0, 1);
					/*}
					
					if(AppleSlowTimer ==4)
					{
						AppleSlowTimer= 0;
					}*/
				}
				
				//Controls Basket Speed
				if(lePressed) Basket.moveBy(-3*speed,0);
				if(riPressed) Basket.moveBy(3*speed,0); 
				
				
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
	
	public void paint(Graphics g)	{
		this.setSize(game_width, game_height);
		g.setFont(new Font("Roboto Light", Font.PLAIN, 36));		
		g.drawImage(tree, 0,0, null);
		
		if(GameState == 0){
//			StartButton.draw(g);
			String welcome = "Press here to start!";
			g.drawImage(title, 40, 30, null);
			g.drawString(welcome, StartButton.x+25, StartButton.y+40);
		}
		else if(GameState==1){
			g.drawImage(tree, 0,0, null);
			for(int i = 0; i < 50; i++){
				Apple[i].draw(g);
			}
			Basket.draw(g);
		}
		else if(GameState == 2){
			g.setColor(java.awt.Color.black);
			g.drawImage(game_over, 0, 0, null);
			g.drawString("Game Over! Your score was: " + score, 10, 10);
			g.drawImage(score_img, 0,  0,  null);
		}
		String score_str = Integer.toString(score);
		g.drawString(score_str + " ", 110 , 40);
		MainButton.draw(g);
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
		if(GameState==0){
			if(StartButton.inRect(mx, my)){
				GameState = 1;
			}
		}
		
		if(MainButton.inRect(mx, my))	{
			GameState = 0;
			score = 0;
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
	}

	public void keyReleased(KeyEvent e)	{
		int code = e.getKeyCode();
		
		if (code == e.VK_A)		lePressed = false;
		if (code == e.VK_D)		riPressed = false;
	}

	public void keyTyped(KeyEvent e) {
	}	
}
