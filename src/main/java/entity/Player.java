package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import objects.OBJ_Boots;

import javax.imageio.ImageIO;
import javax.management.ObjectName;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

public class Player extends entity.Entity {


    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 6;
        solidArea.y = 14;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 28;
        solidArea.height = 28;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() {

        up1 = setup("player/player_up_1");
        up2 = setup("player/player_up_2");
        down1 = setup("player/player_down_1");
        down2 = setup("player/player_down_2");
        left1 = setup("player/player_left_1");
        left2 = setup("player/player_left_2");
        left3 = setup("player/player_left_3");
        right1 = setup("player/player_right_1");
        right2 = setup("player/player_right_2");
        right3 = setup("player/player_right_3");
    }

    public void update() {

        if(keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true) {

            if(keyH.upPressed == true) {
                direction = "up";
            }
            else if(keyH.downPressed == true) {
                direction = "down";
            }
            else if(keyH.leftPressed == true) {
                direction = "left";
            }
            else if(keyH.rightPressed == true) {
                direction = "right";
            }

            //Check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //Check object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //If collision is false, player can move through
            if(collisionOn == false) {

                switch(direction) {
                    case "up":
                        worldY = worldY - speed;
                        break;
                    case "down":
                        worldY = worldY + speed;
                        break;
                    case "left":
                        worldX = worldX - speed;
                        break;
                    case "right":
                        worldX = worldX + speed;
                        break;
                }
            }

            spriteCounter++;
            if(spriteCounter > 15) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }
    public void pickUpObject(int i) {

        if(i != 999) {

            String objectName = gp.obj[i].name;

            switch(objectName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You found a key!");
                    System.out.println("You got a key!");
                    break;
                case "Door":
                    gp.playSE(2);
                    if(hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("The door unlocked!");
                        System.out.println("Key left: " + hasKey);
                    }
                    else { gp.ui.showMessage("You need a key!"); }
                    break;
                case "Chest":
                    gp.playSE(4);
                    if(hasKey > 0) {
                        hasKey--;
                        gp.ui.gameFinish = true;
                        gp.stopMusic();
                        gp.playSE(5);
                        System.out.println("Key left: " + hasKey);
                        gp.obj[i] = new OBJ_Boots(gp);
                    }
                    break;
                case "Boots":
                    gp.playSE(3);
                    speed += 2;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Speed up!");
                    left1 = setup("player/player_run_left_1");
                    left2 = setup("player/player_run_left_2");
                    left3 = setup("player/player_run_left_3");
                    right1 = setup("player/player_run_right_1");
                    right2 = setup("player/player_run_right_2");
                    right3 = setup("player/player_run_right_3");
                    break;
            }
        }
    }

    public void interactNPC(int i) {
        if(i != 999) {
            System.out.println("OUCH! You are hitting me");
        }
    }

    public void draw(Graphics2D g2) {

        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction) {

            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        /*
        int x = screenX;
        int y = screenY;

        if(screenX > worldX) {
            x = worldX;
        }
        if(screenY > worldY) {
            y = worldY;
        }
        int rightOffset = gp.screenWidth - screenX;
        if(rightOffset > gp.worldWidth - worldX) {
            x = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bottomOffset = gp.screenHeight - screenY;
        if(bottomOffset > gp.worldHeight - worldY) {
            y = gp.screenHeight - (gp.worldHeight - worldY);
        }
        */

        //g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
       g2.drawImage(image, screenX, screenY, null);
    }
}
