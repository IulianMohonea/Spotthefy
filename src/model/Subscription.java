package model;

import java.util.*;
import java.time.LocalDate;

public class Subscription  {
    private LocalDate startDate;
    private LocalDate endDate;
    private User_Premium user_premium;

    public Subscription(LocalDate startDate, LocalDate endDate, User_Premium userPremium) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user_premium = userPremium;
    }
}
