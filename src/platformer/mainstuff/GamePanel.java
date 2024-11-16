package platformer.mainstuff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import platformer.entity.Player;
import platformer.tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS

    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; //scales 16x16 = 48x48

    public final int tileSize = originalTileSize * scale; //tile size AFTER scale and og size var's are used
    public final int maxScreenCol = 16; //40 tiles horizontally
    public final int maxScreenRow = 12; //22 tiles vertically
    public final int screenWidth = tileSize * maxScreenCol; //1920 px
    public final int screenHeight = tileSize * maxScreenRow; // 1056 px

    //player has default position
    int playerX = 100;
    int playerY = 100;
    //playerspeed is moved to player.java

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 60;

    KeyHandler keyHandler = new KeyHandler(); //key input handled

    TileManager tileM = new TileManager(this);
    Thread gameThread; //calls run method
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyHandler);


    public GamePanel(){
        //MAKING THE FRAME WITH SPECIFIED DIMENSIONS
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //better rendering, double buffered
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);

        gameThread.start();
    }
    /*
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; //0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            // #1 update information such as character pos
            update();
            // #2 DRAW: draw the screen with the updated info

            repaint(); //calls paintcomponent

            try {
                double remainingTime = nextDrawTime - System.nanoTime(); //time remaining until next draw time

                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
 */
    @Override
    public void run(){
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;



        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if (delta >= 1) {
                update(); //calls run
                repaint(); //calls paintcomponent
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {
        player.update();
    }
    @Override
    public void paintComponent(Graphics g) {
        //draw screen with the updated info

        //vars:

        int width = tileSize;
        int height = tileSize; //character model

        super.paintComponent(g); //parent class of graphics (JPANEL)

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        player.draw(g2);

        g2.dispose(); //better performance, releases system resources that are being used
    }
}
