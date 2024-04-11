package model;

import java.util.*;

public class Liked_songs {
    private List<Song> song = new ArrayList<>();

    public Liked_songs(List <Song> song) {
        this.song = new ArrayList<>(song);

    }

    public List<Song> getSongs(){
        return song;
    }
}
