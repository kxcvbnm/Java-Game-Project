package main;

import javax.swing.*;
import java.awt.*;
import java.lang.*;

import entity.Entity;
import entity.Player;
import objects.SuperObject;
import src.tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTING
    final int originalTileSize = 16;  //16x16 tile (default size of player character)
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth  = tileSize * maxScreenCol; //768 pixels
    public final int screenHeight = tileSize * maxScreenRow; //575 pixels

    //World Setting
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;

    //System
    TileManager tileM = new TileManager(this);
    main.KeyHandler keyH = new main.KeyHandler(this);
    main.Sound music = new main.Sound();
    main.Sound se = new main.Sound();
    Thread gameThread;
    public main.CollisionChecker cChecker = new main.CollisionChecker(this);
    public main.AssetSetter aSetter = new main.AssetSetter(this);
    public main.UI ui = new main.UI(this);

    //Entity and Objects
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    //Game State
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;


    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        //stopMusic();
        gameState = playState;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
//    public void run() {
//
//        double drawInterval = 1000000000/FPS;  // 0.01666 seconds
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//        while (gameThread != null) {
//
//            // 1. Update: update information such as character position
//            update();
//
//            // 2. Draw: draw the screen with the updated information
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime/1000000;
//
//                if(remainingTime < 0) {
//                    remainingTime = 0;
//                }
//
//                Thread.sleep((long) remainingTime);
//
//                nextDrawTime = nextDrawTime + drawInterval;
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                    update();
                    repaint();
                    delta--;
                    drawCount++;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

        public void update() {

        if(gameState == playState) {
            //Player
            player.update();

            //NPC
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }
        }
        if(gameState == pauseState) {
            //nothing
        }
        }
        public void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D)g;

            //Debug
            long drawStart = 0;
            if(keyH.checkDrawTime == true) {
                drawStart = System.nanoTime();
            }

            //Tile
            tileM.draw(g2);

            //Object
            for(int i = 0; i < obj.length; i++) {

                if(obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            //NPC
            for(int i = 0; i < npc.length; i++) {

                if(npc[i] != null) {
                    npc[i].draw(g2, this);
                }
            }

            //Player
            player.draw(g2);

            //UI
            ui.draw(g2);

            //Debug
            if(keyH.checkDrawTime == true) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setColor(Color.white);
                g2.drawString("Draw Time: " + passed, 10, 400);
                System.out.println("Draw Time: " + passed);
            }

            g2.dispose();
        }

    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.playLoop();
    }
    public void stopMusic() {

        music.stop();
    }
    public void playSE(int i) {

        se.setFile(i);
        se.play();
    }
}
