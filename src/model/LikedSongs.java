package model;

import java.util.*;

public class LikedSongs {
    private List<Song> song = new ArrayList<>();

    public LikedSongs(List <Song> song) {
        this.song = new ArrayList<>(song);

    }

    public List<Song> getSongs(){
        return song;
    }
}
