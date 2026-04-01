package com.example.bookmyshow;

import java.util.*;

public class ShowController {

    ShowService showService = new ShowService();
    TheatreService theatreService = new TheatreService();

    public List<Theatre> showTheatres(String city) {
        return theatreService.getTheatres(city);
    }

    public List<Movie> showMovies(String city) {
        List<Show> shows = showService.getShowsByCity(city);
        Set<Movie> movies = new HashSet<>();

        for (Show s : shows) {
            movies.add(s.movie);
        }

        return new ArrayList<>(movies);
    }
}