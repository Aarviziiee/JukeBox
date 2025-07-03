package org.model;

public class Song
{
    private String title;
    private String artist;
    private String genre;
    private String album;
    private String filePath;

    public Song(String title, String artist, String genre, String album, String filePath) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.album = album;
        this.filePath = filePath;
    }

    public String getTitle() {
        return title; }
    public String getArtist() {
        return artist; }
    public String getGenre() {
        return genre; }
    public String getAlbum()
    { return album; }
    public String getFilePath() {
        return filePath; }

    @Override
    public String toString() {
        return "[Title: " + title + ", Artist: " + artist + ", Genre: " + genre + ", Album: " + album + "]";
    }
}
