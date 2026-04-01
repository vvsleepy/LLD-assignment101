package com.example.bookmyshow;

import java.util.*;

public class TheatreService {

    List<Theatre> theatres = new ArrayList<>();

    public List<Theatre> getTheatres(String city) {
        List<Theatre> result = new ArrayList<>();

        for (Theatre t : theatres) {
            if (t.city.equals(city)) {
                result.add(t);
            }
        }
        return result;
    }
}