package objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Gold extends objects.SuperObject {

    public OBJ_Gold() {

        name = "Gold";
        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/gold.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }

    }
}
