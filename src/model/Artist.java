package model;

import java.util.*;

public class Artist {
    private String name;
    private List<Song> songs;
    private List<Album> albums;

    public Artist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
        this.albums = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public List<Song> getSongs (){
        return songs;
    }
    public List<Album> getAlbums (){
        return albums;
    }
    public void addSong(Song s){
        songs.add(s);
    }
}
