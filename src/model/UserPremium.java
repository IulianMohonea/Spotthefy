package model;
import java.util.*;
public class UserPremium extends User {
    private Subscription subscription;

    public UserPremium(String name, LikedSongs likedSongs, Subscription subscription) {
        super(name, likedSongs);
        this.subscription = subscription;
    }
}
