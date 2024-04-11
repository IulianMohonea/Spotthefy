package model;
import java.util.*;
public class User_Premium extends User {
    private Subscription subscription;

    public User_Premium(String name, Liked_songs liked_songs, Subscription subscription) {
        super(name, liked_songs);
        this.subscription = subscription;
    }
}
