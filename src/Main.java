import service.*;
import model.*;
import repository.*;


import java.io.FileNotFoundException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Main {
    public static final String USER_NAME_PROMPT = "What's the user's name?";
    public static final String ARTIST_NAME_PROMPT = "What's the artist's name?";
    public static final String SONG_NAME_PROMPT = "What's the song's name?";


    

    public static void main(String[] args) throws FileNotFoundException{

        JDBCUserService jdbcUserService = new JDBCUserService();
        AuditService auditService = new AuditService();
        UserService userService = new UserService();
        ArtistService artistService = new ArtistService();
        while (true) {
            displayMenu(userService, artistService, jdbcUserService, auditService);
        }
    }
    private static void displayMenu(UserService userService, ArtistService artistService, JDBCUserService jdbcUserService, AuditService auditService) {
        System.out.println("""
                
                Welcome to the Spotthefy!
                
                Select an option from the menu below:
                1. Add users  
                2. Add artists
                3. Add songs
                4. Show the liked songs of an user
                5. Add a song to a user's liked songs
                6. Give a user a premium subscription
                7. Add albums
                8. Show the albums of a certain artist
                9. Show the total number of premium subscriptions
                10. Show the songs of a certain artist
                11. Make a playlist based on the genre of the songs
                12. Show list of artists
                13. Delete user
                14. See if a user has a premium subscription
                15. Exit 
               
                """);
        // Actiunile 1,2,3
        Scanner scanner = new Scanner(System.in);
        int option = Integer.parseInt(scanner.nextLine());

        switch (option) {
            case 1 -> addUsers(userService, jdbcUserService, auditService);
            case 2 -> addArtists(artistService, jdbcUserService);
            case 3 -> addSongs(artistService, jdbcUserService);
            case 4 -> showLikedSongs(userService, jdbcUserService);
            case 5 -> addToLikedSongs(userService, artistService, jdbcUserService);
            case 6 -> makeUserPremium(userService, jdbcUserService);
            case 7 -> addAlbums(artistService, jdbcUserService);
            case 8 -> showArtistsAlbums(artistService, jdbcUserService);
            case 9 -> showNumberOfPremiums(userService, jdbcUserService);
            case 10 -> showSongsByArtist(artistService, jdbcUserService);
            case 11 -> makePlaylistByGenre(artistService, jdbcUserService);
            case 12 -> showArtists(artistService, jdbcUserService);
            case 13 -> deleteUser(jdbcUserService);
            case 14 -> seePremium(jdbcUserService);
            case 15 -> System.exit(0);
            default -> System.out.println("Invalid option");
        }
    }

    public static void logUpdateAction(String actionName) {

        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestampString = dateFormat.format(currentDate);

        AuditService auditService = AuditService.getInstance();


        auditService.logAction(actionName + ", " + timestampString);
        auditService.close();
    }

    private static void showArtists(ArtistService artistService, JDBCUserService jdbcUserService) {
        artistService.showArtists();
        jdbcUserService.showArtists();
    }

    private static void makePlaylistByGenre(ArtistService artistService, JDBCUserService jdbcUserService) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What's the genre's name?");
        String genre_name = scanner.nextLine();
        Playlist playlist = artistService.genrePlaylist(genre_name);
        if(playlist.getSongs().isEmpty())
            System.out.println("No songs found");
        else{
            System.out.println("Songs of " + genre_name + " genre are: ");
            for (Song song : playlist.getSongs()){
                System.out.println(song.getTitle());
            }
        }

    }

    private static void showArtistsAlbums(ArtistService artistService, JDBCUserService jdbcUserService) {
        String name = readLookupName(ARTIST_NAME_PROMPT);
        List<Album> albums = artistService.albumsOfArtists(name);
        System.out.println("Albums of " + name + " are: ");
        for (Album album : albums) {
            System.out.println(album.getTitle());
        }
    }

    private static void showSongsByArtist(ArtistService artistService, JDBCUserService jdbcUserService) {
        String name = readLookupName(ARTIST_NAME_PROMPT);
        List<Song> songs = artistService.songsOfArtists(name);
        System.out.println("Songs of " + name + " are: ");
        for (Song song : songs) {
            System.out.println(song.getTitle());
        }
    }

    private static void showNumberOfPremiums(UserService userService, JDBCUserService jdbcUserService) {
        jdbcUserService.showNumberOfPremiums();
        System.out.println("The number of premium users is: ");
        System.out.println(userService.numberOfPremiums());
        System.out.println("Which comes out to a total of x dollars per month! ");
    }

    private static void addAlbums(ArtistService artistService, JDBCUserService jdbcUserService) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What's the album's name?");
        String album_name = scanner.nextLine();
        String name = readLookupName(ARTIST_NAME_PROMPT);
        System.out.println("In what year was it made?");
        int year = scanner.nextInt();
        String message = artistService.addAlbums(name, album_name, year);
        System.out.println(message);
        jdbcUserService.addAlbums(name, album_name, year);
    }

    private static void addArtists(ArtistService artistService, JDBCUserService jdbcUserService) {
        String name = readLookupName(ARTIST_NAME_PROMPT);
        String message = artistService.addArtists(name);
        System.out.println(message);
        jdbcUserService.addArtist(name);
    }

    private static void addSongs(ArtistService artistService, JDBCUserService jdbcUserService) {
        String artist_name = readLookupName(ARTIST_NAME_PROMPT);
        String song_name = readLookupName(SONG_NAME_PROMPT);
        Scanner scanner = new Scanner(System.in);
        System.out.println("What's the genre's name?");
        String genre_name = scanner.nextLine();
        String message = artistService.addSongs(artist_name, song_name, genre_name);
        System.out.println(message);

        jdbcUserService.addSong(artist_name, song_name, genre_name);
    }

    private static void addUsers(UserService userService, JDBCUserService jdbcUserService, AuditService auditService) {
        String name = readLookupName(USER_NAME_PROMPT);
        String message = userService.addUser(name);
        System.out.println(message);
        logUpdateAction("Add user");
        jdbcUserService.addUser(name);
    }

    private static void showLikedSongs(UserService userService,JDBCUserService jdbcUserService){
        String name = readLookupName(USER_NAME_PROMPT);
        jdbcUserService.showLikedSongs(name);
        userService.gettLikedSongs(name);
    }

    private static void addToLikedSongs(UserService userService, ArtistService artistService, JDBCUserService jdbcUserService){
        String name = readLookupName(USER_NAME_PROMPT);
        User user = userService.getUser(name);
        if (user == null){
            System.out.println("User not found");
        }
        else{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Song name:");
            String song_name = scanner.nextLine();
            System.out.println("Artist name:");
            String artist = scanner.nextLine();
            if(artistService.findArtist(artist) == null){
                System.out.println("Artist not found");
            }
            else{
                Song song = artistService.findSong(song_name, artist);
                if(artistService.findSong(song_name, artist) != null){
                    userService.addSongToLikedSongs(user, song);
                    jdbcUserService.addToLikedSongs(name, song_name);
                }
                else {
                    System.out.println("Song does not exist");
                }
            }
        }

    }

    private static void makeUserPremium(UserService userService, JDBCUserService jdbcUserService){
        String name = readLookupName(USER_NAME_PROMPT);
        User user = userService.getUser(name);
        jdbcUserService.updateUserStatus(name);
        if(user == null){
            System.out.println("User not found");
        }
        else{
            userService.makePremium(user);
        }

    }

    private static String readLookupName(String message) {
        Scanner screenInput = new Scanner(System.in);
        System.out.println(message);
        String lookUpName = screenInput.nextLine();
        System.out.println();
        return lookUpName;
    }

    private static void deleteUser(JDBCUserService jdbcUserService) {
        String name = readLookupName(USER_NAME_PROMPT);
        jdbcUserService.deleteUser(name);
    }

    private static void seePremium(JDBCUserService jdbcUserService) {
        String name = readLookupName(USER_NAME_PROMPT);
        System.out.println(jdbcUserService.seeStatus(name));
    }
}