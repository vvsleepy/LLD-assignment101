package com.example.bookmyshow;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Show {
    String showId;
    Movie movie;
    Theatre theatre;
    Map<String, Seat> seats = new ConcurrentHashMap<>();

    public Show(String showId, Movie movie, Theatre theatre, List<String> seatIds) {
        this.showId = showId;
        this.movie = movie;
        this.theatre = theatre;

        for (String id : seatIds) {
            seats.put(id, new Seat(id));
        }
    }
}