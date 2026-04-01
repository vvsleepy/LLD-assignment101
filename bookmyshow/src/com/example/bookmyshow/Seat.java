package com.example.bookmyshow;

public class Seat {
    String seatId;
    SeatStatus status;

    public Seat(String seatId) {
        this.seatId = seatId;
        this.status = SeatStatus.AVAILABLE;
    }
}