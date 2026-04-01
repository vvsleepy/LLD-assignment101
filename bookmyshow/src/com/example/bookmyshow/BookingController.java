package com.example.bookmyshow;

import java.util.*;

public class BookingController {

    BookingService bookingService = new BookingService();

    public Booking bookTickets(User user, Show show, List<String> seats) {
        return bookingService.bookTickets(user, show, seats);
    }

    public void cancel(Booking booking) {
        bookingService.cancelBooking(booking);
    }
}