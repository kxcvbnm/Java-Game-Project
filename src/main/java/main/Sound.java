package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {

        soundURL[0] = getClass().getClassLoader().getResource("sound/mainTheme.wav");
        soundURL[1] = getClass().getClassLoader().getResource("sound/pickup.wav");
        soundURL[2] = getClass().getClassLoader().getResource("sound/door.wav");
        soundURL[3] = getClass().getClassLoader().getResource("sound/powerup.wav");
        soundURL[4] = getClass().getClassLoader().getResource("sound/chestopen.wav");
        soundURL[5] = getClass().getClassLoader().getResource("sound/ending.wav");

    }

    public void setFile(int i) {
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void play() {
        clip.start();
    }
    public void playLoop() {
        if (clip != null && clip.isOpen()) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
    }
    public void stop() {
        clip.stop();
    }
}
