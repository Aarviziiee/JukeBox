package org.services;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayerService {
    public static void playSong(String title) {
        String filePath = "resources/Songs/" + title + ".wav";
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println(" File not found: " + file.getAbsolutePath());
            return;
        }

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(file)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            System.out.println("🎵 Now Playing: " + file.getName());

            // Add a wait message
            while (!clip.isRunning())
                Thread.sleep(10);
            while (clip.isRunning())
                Thread.sleep(100);

            clip.close();
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported audio file format.");
        } catch (LineUnavailableException e) {
            System.out.println(" Audio line unavailable (used by another app?).");
        } catch (IOException e) {
            System.out.println("IO error playing the audio.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
