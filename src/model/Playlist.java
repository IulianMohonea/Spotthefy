package model;

import java.util.*;

public class Playlist {
    private String name;
    private List<Song> song;

    public Playlist(String name, List<Song> song) {
        this.name = name;
        this.song = song;
    }
    public String getName() {
        return name;
    }
    public List<Song> getSongs() {
        return song;
    }
}
