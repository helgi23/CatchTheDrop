import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame {

   private static GameWindow gameWindow;
   private static long lastFrameTime;
   private static Image drop;
   private static Image grass;
   private static Image game_over;
   private static float dropLeft = 200f;
   private static float dropTop = -100f;
   private static int dropSpeed = 200;
   private static int score = 0;

   public static void main(String[] args) throws IOException{
      grass = ImageIO.read(GameWindow.class.getResourceAsStream("127812.jpg"));
      drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"));
      game_over = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
      gameWindow = new GameWindow();
      gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      gameWindow.setLocation(200, 100);
      gameWindow.setSize(850, 531);
      lastFrameTime = System.nanoTime();
      gameWindow.setResizable(false);
      GameField gameField = new GameField();
      gameField.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            float dropRight = dropLeft + drop.getWidth(null);
            float dropBottom = dropTop + drop.getHeight(null);
            boolean isDrop = x >= dropLeft && x <= dropRight && y >= dropTop && y <= dropBottom;
            if(isDrop){
                dropTop = -100;
                dropLeft = (int) (Math.random() *  (gameField.getWidth() - drop.getWidth(null)));
                dropSpeed = dropSpeed + 10;
                score++;
                gameWindow.setTitle("Score: " + score);
            }
          }
      });
      gameWindow.add(gameField);
      gameWindow.setVisible(true);
   }

    private static void onRepaint(Graphics g){
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
        lastFrameTime = currentTime;


        dropTop = dropTop + dropSpeed * deltaTime;
        g.drawImage(grass, 0, 0, null);
        g.drawImage(drop, (int)dropLeft, (int)dropTop, null);
        if(dropTop > gameWindow.getHeight())
            g.drawImage(game_over, 260, 120, null);

    }

    private static class GameField extends JPanel{

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}
