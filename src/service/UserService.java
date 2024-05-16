package service;
import model.*;
import java.util.*;
import java.time.LocalDate;


public class UserService {

    private List<User> users;
    private List<UserPremium> usersPremium;

    public UserService() {

    }
    
    public String addUser (String name){
        LikedSongs likedSongs = new LikedSongs(new ArrayList<>());
        User user = new User (name, likedSongs);
        if (users == null) {
            users = new ArrayList<>();
        }

        users.add(user);
        return "The user has been added succesfully!";
    }
    public int numberOfPremiums(){
        if (usersPremium == null) {
            return 0;
        }
        return usersPremium.size();

    }
    public void addSongToLikedSongs(User user, Song song) {
            user.addSongToLikedSongs(song);
        }

    public List<Song> getLikedSongs (User user) {
            return user.getLikedSongs();
    }

    public void gettLikedSongs(String name) {
            User user = getUser(name);
            if (user == null) {
                System.out.println("User not found");
            }
            else{
                List <Song> songs = user.getLikedSongs();
                if (songs.isEmpty()) {
                    System.out.println("No songs found");
                }
                else{
                    System.out.println("Songs liked by user:" + user.getName());
                    for (Song s : songs) {
                        System.out.println(s.getTitle());
                    }
                }

            }

    }
    public String getName(User user){
        return user.getName();
    }

    public User getUser (String name){
        if (users == null) {
            return null;
        }
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public void makePremium (User user){
            LocalDate startdate = LocalDate.now();
            LocalDate enddate = startdate.plusMonths(1);
            Subscription subscription = new Subscription(startdate, enddate, null);
            UserPremium userPremium = new UserPremium(user.getName(), user.forPremium(), subscription);
            if (usersPremium == null) {
                usersPremium = new ArrayList<>();
            }
            

            usersPremium.add(userPremium);
    }

}
