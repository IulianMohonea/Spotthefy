package model;

import java.util.*;

public class User {
    private String name;
    private LikedSongs likedSongs;

    public User(String name, LikedSongs likedSongs) {
        this.name = name;
        this.likedSongs = likedSongs;
    }

    public String getName() {
        return name;
    }

    public void addSongToLikedSongs(Song song) {

        likedSongs.getSongs().add(song);
    }
    public LikedSongs forPremium (){
        return likedSongs;
    }
    public List<Song> getLikedSongs() {
        return likedSongs.getSongs();
    }
}
