
package org.services;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayerService {
    public static void playSong(String title) {
        String filePath = "resources/Songs/" + title + ".wav";
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("❌ File not found: " + title + ".wav");
            return;
        }

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            System.out.println("🎵 Now Playing: " + title + ".wav");

            while (clip.isRunning()) {
                Thread.sleep(100);
            }

            clip.close();
        } catch (Exception e) {
            System.out.println("❌ Error playing file: " + title);
            e.printStackTrace();
        }
    }
}

