package org.services;


import org.model.Song;

import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;

import java.io.File;
import java.io.IOException;


public class SongService {
    private List<Song> songs = new ArrayList<>();

    public SongService() {
        songs = new ArrayList<>();
    }

    // 1. Add a song (avoid duplicates)
    public void addSong(Song song) {
        boolean exists = songs.stream()
                .anyMatch(s -> s.getTitle().equalsIgnoreCase(song.getTitle()));
        if (!exists) {
            songs.add(song);
            System.out.println("✅ Song added: " + song.getTitle());
            System.out.println("Current size of song list: " + songs.size());

        } else {
            System.out.println("❌ Duplicate song not added.");
        }

    }

    public List<Song> searchByTitle(String title) {
        List<Song> results = new ArrayList<>();
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                results.add(song);
            }
        }
        return results;
    }


    public void playSongByTitle(String title) {
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                System.out.println("🎵 Now playing: " + song.getTitle());
                playAudio(song.getFilePath()); // this should call AudioSystem to play .wav
                return;
            }
        }
        System.out.println("❌ Song not found with title: " + title);
    }


    // 2. Remove song by title
    public void removeByTitle(String title) {
        boolean removed = songs.removeIf(s -> s.getTitle().equalsIgnoreCase(title));
        System.out.println(removed ? "🗑️ Removed: " + title : "⚠️ Not found: " + title);
    }

    // 3. Display all songs (numbered)
    public void displayAllSongs() {
        if (songs.isEmpty()) {
            System.out.println("📂 No songs available.");
        } else {
            System.out.println("🎶 All Songs:");
            songs.stream()
                    .sorted(Comparator.comparing(Song::getTitle))
                    .forEach(System.out::println);
        }
    }

    // 4. Search by artist
    public void searchByArtist(String artist) {
        List<Song> result = songs.stream()
                .filter(s -> s.getArtist().equalsIgnoreCase(artist))
                .sorted(Comparator.comparing(Song::getTitle))
                .toList();
        printSearchResults(result, "Artist: " + artist);
    }

    // 5. Search by genre
    public void searchByGenre(String genre) {
        List<Song> result = songs.stream()
                .filter(s -> s.getGenre().equalsIgnoreCase(genre))
                .sorted(Comparator.comparing(Song::getTitle))
                .toList();
        printSearchResults(result, "Genre: " + genre);
    }

    // 6. Search by album
    public void searchByAlbum(String album) {
        List<Song> result = songs.stream()
                .filter(s -> s.getAlbum().equalsIgnoreCase(album))
                .sorted(Comparator.comparing(Song::getTitle))
                .toList();
        printSearchResults(result, "Album: " + album);
    }

    // 7. Play all songs (alphabetically)
    public void playAllSongs() {
        songs.stream()
                .sorted(Comparator.comparing(Song::getTitle))
                .forEach(song -> playAudio(song.getFilePath()));
    }


    // Utility: Print results
    private void printSearchResults(List<Song> list, String criteria) {
        if (list.isEmpty()) {
            System.out.println("🔍 No songs found for " + criteria);
        } else {
            System.out.println("🔍 Songs found for " + criteria + ":");
            list.forEach(System.out::println);
        }
    }

    // Utility: Simulate song playback
    private void playAudio(String filePath) {
        try {
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                System.out.println("❌ File not found: " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Wait till the song completes playing
            while (clip.isRunning()) {
                Thread.sleep(100);
            }

            clip.close();
            audioStream.close();
            System.out.println("✅ Finished playing: " + filePath);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            System.out.println("❌ Error playing file: " + e.getMessage());
        }
    }

    // Optional: Get all songs (used for testing)
    public List<Song> getAllSongs() {
        System.out.println("📦 Fetching all songs. Count: " + songs.size());
        return songs;
    }
}

