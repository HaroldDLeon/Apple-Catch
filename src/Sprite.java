import java.awt.Color;
import java.awt.Graphics;

public class Sprite extends Rect
{
   Animation[] animation;

   boolean selected = false;

   boolean moving = false;

   static final int LEFT   = 0;
   static final int RIGHT  = 1;
   static final int UP     = 2;
   static final int DOWN   = 3;

   int pose = LEFT;


   public Sprite(int x, int y, String file, String[] action, int count, int duration)
   {
      super(x, y, 20, 50);


      animation = new Animation[action.length];

      for(int i = 0; i < action.length; i++)

         animation[i] = new Animation(file + action[i]+"_", count, duration);
   }

   public void moveUpBy(int dy)
   {
      y -= dy;

      moving = true;

      pose = UP;
   }
   public void moveDownBy(int dy)
   {
      y += dy;

      moving = true;

      pose = DOWN;
   }
   public void moveLeftBy(int dx)
   {
      x -= dx;

      moving = true;

      pose = LEFT;
   }
   public void moveRightBy(int dx)
   {
      x += dx;

      moving = true;

      pose = RIGHT;
   }


   public void draw(Graphics g)
   {
      if(moving)
         g.drawImage(animation[pose].nextImage(), (int)x, (int)y, null);
      else
         g.drawImage(animation[pose].stillImage(), (int)x, (int)y, null);

        /// moving = false;

      g.setColor(Color.red);
      if (selected) g.drawLine((int)x+8, (int)(y+h), (int)(x+w-5), (int)(y+h));
      g.setColor(Color.black);

   }
}