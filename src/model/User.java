package model;

import java.util.*;

public class User {
    private String name;
    private Liked_songs liked_songs;

    public User(String name, Liked_songs liked_songs) {
        this.name = name;
        this.liked_songs = liked_songs;
    }

    public String getName() {
        return name;
    }

    public void addSongToLikedSongs(Song song) {

        liked_songs.getSongs().add(song);
    }
    public Liked_songs forPremium (){
        return liked_songs;
    }
    public List<Song> getLikedSongs() {
        return liked_songs.getSongs();
    }
}
