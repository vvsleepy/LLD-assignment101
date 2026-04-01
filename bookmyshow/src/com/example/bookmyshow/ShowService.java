package com.example.bookmyshow;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ShowService {

    List<Show> shows = new CopyOnWriteArrayList<>();

    public void addShow(Show show) {
        shows.add(show); // thread-safe
    }

    public List<Show> getShowsByCity(String city) {
        List<Show> result = new ArrayList<>();

        for (Show s : shows) {
            if (s.theatre.city.equals(city)) {
                result.add(s);
            }
        }
        return result;
    }
}