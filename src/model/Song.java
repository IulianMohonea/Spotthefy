package model;

public class Song {
    private String title;
    private Artist artist;
    private String genre;

    public Song(String title, Artist artist, String genre) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
    }
    public String getTitle() {
        return title;
    }
    public String getGenre() {
        return genre;
    }

}
