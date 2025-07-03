package org;

import org.model.Song;
import org.services.AudioPlayerService;
import org.services.SongService;

import java.util.Scanner;
import java.util.List;

public class Main
{
    public static void main( String[] args ){

        Scanner scanner = new Scanner(System.in);
        SongService songService = new SongService();

        boolean running = true;
        while (running) {
            System.out.println("\n🎵 SONG MENU 🎵");
            System.out.println("1. Add Song");
            System.out.println("2. Search Song by Title");
            System.out.println("3. Display All Songs");
            System.out.println("4. Remove Song by Title");
            System.out.println("5. Play Song by Title");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("🎵 Add Song (Enter 0 at any point to cancel)");

                    System.out.print("Enter Song Title: ");
                    String title = scanner.nextLine();
                    if (title.equals("0")) break;

                    System.out.print("Enter Artist: ");
                    String artist = scanner.nextLine();
                    if (artist.equals("0")) break;

                    System.out.print("Enter Genre: ");
                    String genre = scanner.nextLine();
                    if (genre.equals("0")) break;

                    System.out.print("Enter Album: ");
                    String album = scanner.nextLine();
                    if (album.equals("0")) break;

                    System.out.print("Enter File Path (.wav): ");
                    String filePath = scanner.nextLine();

                    Song newSong = new Song(title, artist, genre, album, filePath);
                    songService.addSong(newSong);
                    break;

                case "2":
                    System.out.print("Enter title to search: ");
                    String searchTitle = scanner.nextLine();
                    List<Song> searchResults = songService.searchByTitle(searchTitle);
                    if (searchResults.isEmpty()) {
                        System.out.println("❌ No songs found with title: " + searchTitle);
                    } else {
                        searchResults.forEach(System.out::println);
                    }
                    break;

                case "3":
                    List<Song> allSongs = songService.getAllSongs();
                    if (allSongs.isEmpty()) {
                        System.out.println("No songs available.");
                    } else {
                        System.out.println("🎶 Song List:");
                        allSongs.forEach(System.out::println);
                    }
                    break;

                case "4":
                    System.out.print("Enter title to remove: ");
                    String removeTitle = scanner.nextLine();
                    songService.removeByTitle(removeTitle);
                    break;

                case "5":
                    System.out.print("Enter title of the song to play: ");
                    String playTitle = scanner.nextLine();

                    Song songToPlay = songService.searchByTitle(playTitle).stream()
                            .findFirst()
                            .orElse(null);

                    if (songToPlay != null) {
                        System.out.println("🎵 Now playing: " + songToPlay.getTitle());
                        AudioPlayerService.playSong(songToPlay.getFilePath());
                    } else {
                        System.out.println("❌ Song not found: " + playTitle);
                    }

                    break;

                case "6":
                    System.out.println("🎧 Exiting Jukebox. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("❗ Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}

