import service.*;
import model.*;


import java.io.FileNotFoundException;
import java.util.*;
/* Interogari + Actiuni:
1. Afisare melodii apreciate de catre un user;
2. Afisare numele unui user;
3. Adaugare a unei melodii la melodiile apreciate
4.

*/
public class Main {
    public static final String USER_NAME_PROMPT = "What's the user's name?";
    public static final String ARTIST_NAME_PROMPT = "What's the artist's name?";
    public static final String SONG_NAME_PROMPT = "What's the song's name?";


    public static void main(String[] args) throws FileNotFoundException{
        UserService userService = new UserService();
        ArtistService artistService = new ArtistService();
        while (true) {
            displayMenu(userService, artistService);
        }
    }
    private static void displayMenu(UserService userService, ArtistService artistService) {
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
                13. Exit
               
                """);

        Scanner scanner = new Scanner(System.in);
        int option = Integer.parseInt(scanner.nextLine());

        switch (option) {
            case 1 -> addUsers(userService);
            case 2 -> addArtists(artistService);
            case 3 -> addSongs(artistService);
            case 4 -> showLikedSongs(userService);
            case 5 -> addToLikedSongs(userService, artistService);
            case 6 -> makeUserPremium(userService);
            case 7 -> addAlbums(artistService);
            case 8 -> showArtistsAlbums(artistService);
            case 9 -> showNumberOfPremiums(userService);
            case 10 -> showSongsByArtist(artistService);
            case 11 -> makePlaylistByGenre(artistService);
            case 12 -> showArtists(artistService);
            case 13 -> System.exit(0);
            default -> System.out.println("Invalid option");
        }
    }
    private static void showArtists(ArtistService artistService) {
        artistService.showArtists();
    }

    private static void makePlaylistByGenre(ArtistService artistService) {
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

    private static void showArtistsAlbums(ArtistService artistService) {
        String name = readLookupName(ARTIST_NAME_PROMPT);
        List<Album> albums = artistService.albumsOfArtists(name);
        System.out.println("Albums of " + name + " are: ");
        for (Album album : albums) {
            System.out.println(album.getTitle());
        }

    }

    private static void showSongsByArtist(ArtistService artistService) {
        String name = readLookupName(ARTIST_NAME_PROMPT);
        List<Song> songs = artistService.songsOfArtists(name);
        System.out.println("Songs of " + name + " are: ");
        for (Song song : songs) {
            System.out.println(song.getTitle());
        }
    }

    private static void showNumberOfPremiums(UserService userService) {
        System.out.println("The number of premium users is: ");
        System.out.println(userService.numberOfPremiums());
        // In viitor o sa vina in subscription si un price in functie de ce tip este...
        System.out.println("Which comes out to a total of x dollars per month! ");
    }

    private static void addAlbums(ArtistService artistService) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What's the album's name?");
        String album_name = scanner.nextLine();
        String name = readLookupName(ARTIST_NAME_PROMPT);
        System.out.println("In what year was it made?");
        int year = scanner.nextInt();
        String message = artistService.addAlbums(name, album_name, year);
        System.out.println(message);
    }

    private static void addArtists(ArtistService artistService) {
        String name = readLookupName(ARTIST_NAME_PROMPT);
        String message = artistService.addArtists(name);
        System.out.println(message);
    }

    private static void addSongs(ArtistService artistService) {
        String artist_name = readLookupName(ARTIST_NAME_PROMPT);
        String song_name = readLookupName(SONG_NAME_PROMPT);
        Scanner scanner = new Scanner(System.in);
        System.out.println("What's the genre's name?");
        String genre_name = scanner.nextLine();
        String message = artistService.addSongs(artist_name, song_name, genre_name);
        System.out.println(message);
    }

    private static void addUsers(UserService userService) {
        String name = readLookupName(USER_NAME_PROMPT);
        String message = userService.addUser(name);
        System.out.println(message);
    }

    private static void showLikedSongs(UserService userService){
        String name = readLookupName(USER_NAME_PROMPT);
        userService.gettLikedSongs(name);
    }

    private static void addToLikedSongs(UserService userService, ArtistService artistService){
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
                }
                else {
                    System.out.println("Song does not exist");
                }
            }
        }

    }

    private static void makeUserPremium(UserService userService){
        String name = readLookupName(USER_NAME_PROMPT);
        User user = userService.getUser(name);
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

}