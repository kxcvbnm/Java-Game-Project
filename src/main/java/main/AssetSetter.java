package main;

import entity.NPC_Baby;
import objects.*;

public class AssetSetter {

    main.GamePanel gp;

    public AssetSetter(main.GamePanel gp) {

        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new objects.OBJ_Key(gp);
        gp.obj[0].worldX = 38 * gp.tileSize;
        gp.obj[0].worldY = 10 * gp.tileSize;

        gp.obj[1] = new objects.OBJ_Key(gp);
        gp.obj[1].worldX = 24 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        gp.obj[2] = new objects.OBJ_Door(gp);
        gp.obj[2].worldX = 10 * gp.tileSize;
        gp.obj[2].worldY = 11 * gp.tileSize;

        gp.obj[3] = new objects.OBJ_Chest(gp);
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 7 * gp.tileSize;

    /*    gp.obj[4] = new OBJ_Chest();
        gp.obj[4].worldX = 18 * gp.tileSize;
        gp.obj[4].worldY = 37 * gp.tileSize;
    */
        gp.obj[5] = new objects.OBJ_Boots(gp);
        gp.obj[5].worldX = 35 * gp.tileSize;
        gp.obj[5].worldY = 27 * gp.tileSize;
    }

    public void setNPC() {

        gp.npc[0] = new NPC_Baby(gp);
        gp.npc[0].worldX = gp.tileSize * 22;
        gp.npc[0].worldY = gp.tileSize * 42;
    }
}
