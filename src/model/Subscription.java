package model;

import java.util.*;
import java.time.LocalDate;

public class Subscription  {
    private LocalDate startDate;
    private LocalDate endDate;
    private UserPremium userPremium;

    public Subscription(LocalDate startDate, LocalDate endDate, UserPremium userPremium) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.userPremium = userPremium;
    }
}
