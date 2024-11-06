package main;

import objects.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.text.DecimalFormat;

public class UI {

    main.GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_100B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinish = false;
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(main.GamePanel gp) {

        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_100B = new Font("Arial", Font.BOLD, 100);

        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;

    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        if(gameFinish == true) {

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "You found a treasure chest!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            text = "You time is : " + dFormat.format(playTime);
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 4);
            g2.drawString(text, x, y);

            g2.setFont(arial_100B);
            g2.setColor(Color.GREEN);
            text = "You Win!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);
        }
        else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 74, 65);

            //Playtime
            playTime += (double)1/60;
            g2.drawString("Time : " + dFormat.format(playTime), gp.tileSize * 11, 65);

            //Message
            if(messageOn == true) {

                g2.setFont(g2.getFont().deriveFont(25F));
                g2.drawString(message, 330, 260);

                messageCounter++;

                if(messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            //Dp playState Stuff
        }
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

    }
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 120F));
        String text = "PAUSE";
        int x = getXforCenterText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }
    public int getXforCenterText(String text) {

        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}
