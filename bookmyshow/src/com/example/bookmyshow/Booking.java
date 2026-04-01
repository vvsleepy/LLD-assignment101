package com.example.bookmyshow;

import java.util.*;

public class Booking {
    String bookingId;
    User user;
    Show show;
    List<Seat> seats;
    BookingStatus status;

    public Booking(String id, User user, Show show, List<Seat> seats) {
        this.bookingId = id;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        status = BookingStatus.CANCELLED;
    }
}