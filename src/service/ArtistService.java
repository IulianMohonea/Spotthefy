package service;
import model.*;
import java.util.*;
import java.time.LocalDate;


public class ArtistService {

    private Set<Artist> artists = new TreeSet<>(Comparator.comparing(Artist::getName));;
    private List<Song> songs = new ArrayList<>();

    public ArtistService() {

    }
    public Artist findArtist(String name){
        for (Artist artist : artists) {
            if (artist.getName().equals(name)) {
                return artist;
            }
        }
        return null;
    }
    public Song findSong(String song_name, String artist_name){
        Artist artist = findArtist(artist_name);
        for (Song song : artist.getSongs()) {
            if (song.getTitle().equals(song_name)) {
                return song;
            }
        }
        return null;

    }
    public String addArtists(String name){
        Artist artist = new Artist(name);
        artists.add(artist);
        return "The artist has been added successfully";
    }

    public void showArtists(){
        if (artists.isEmpty()) {
            System.out.println("No artists found");
        }
        else{
            for (Artist artist : artists) {
                System.out.println(artist.getName());
            }
        }

    }

    public String addSongs(String artist_name, String song_name, String genre_name){
        if(findArtist(artist_name) != null){
            Artist artist = findArtist(artist_name);
            Song song = new Song(song_name, artist, genre_name);
            artist.getSongs().add(song);
            songs.add(song);
            return "The song has been added successfully";
        }

        return "The artist does not exist";
    }

    public String addAlbums(String artist_name, String album_name, int year){
        if (findArtist(artist_name) == null){
            return "The artist doesn't exist";
        }
        Artist artist = findArtist(artist_name);
        Album album = new Album(album_name, artist, year, null);
        artist.getAlbums().add(album);
        return "The album has been added successfully";
    }

    public List<Album> albumsOfArtists(String name){
        List<Album> albums = new ArrayList<>();
        if (findArtist(name) == null){
            return albums;
        }
        Artist artist = findArtist(name);
        for (Album album : artist.getAlbums()) {
            albums.add(album);
        }
        return albums;
    }

    public List<Song> songsOfArtists(String name){
        List<Song> songs = new ArrayList<>();
        if (findArtist(name) == null){
            return songs;
        }
        Artist artist = findArtist(name);
        for (Song song : artist.getSongs()) {
            songs.add(song);
        }
        return songs;
    }
    public Playlist genrePlaylist(String name){
        List<Song> playlist_songs = new ArrayList<>();
        for (Song song : songs){
            if(song.getGenre().equals(name)){
                playlist_songs.add(song);
            }
        }
        Playlist playlist = new Playlist(name, playlist_songs);
        return playlist;
    }



}
