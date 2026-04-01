package com.example.bookmyshow;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        User user = new User("U1", "Anuska", "Bangalore");

        Movie movie = new Movie("M1", "Inception");
        Theatre theatre = new Theatre("T1", "PVR", "Bangalore");

        List<String> seats = Arrays.asList("A1", "A2", "A3");

        Show show = new Show("S1", movie, theatre, seats);

        BookingController controller = new BookingController();

        Booking booking = controller.bookTickets(user, show, Arrays.asList("A1", "A2"));

        System.out.println("Booking successful: " + booking.bookingId);

        controller.cancel(booking);
    }
}