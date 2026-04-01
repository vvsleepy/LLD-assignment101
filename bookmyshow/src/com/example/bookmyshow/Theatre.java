package com.example.bookmyshow;

import java.util.*;

public class Theatre {
    String theatreId;
    String name;
    String city;
    List<Show> shows = new ArrayList<>();

    public Theatre(String theatreId, String name, String city) {
        this.theatreId = theatreId;
        this.name = name;
        this.city = city;
    }
}