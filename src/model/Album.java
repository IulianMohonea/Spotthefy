package model;
import java.util.*;

public class Album {
    private String title;
    private Artist artist;
    private int year;
    private List<Song> song;

    public Album(String title, Artist artist, int year, List<Song> song) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.song = song;
    }
    public String getTitle() {
        return title;
    }
}
