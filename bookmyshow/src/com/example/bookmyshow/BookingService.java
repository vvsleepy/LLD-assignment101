package com.example.bookmyshow;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class BookingService {

    public Booking bookTickets(User user, Show show, List<String> seatIds) {
        List<Seat> bookedSeats = new ArrayList<>();
        List<ReentrantLock> locks = new ArrayList<>();

        try {
            for (String seatId : seatIds) {
                ReentrantLock lock = LockManager.getLock(show.showId + "_" + seatId);
                lock.lock();
                locks.add(lock);

                Seat seat = show.seats.get(seatId);

                if (seat.status != SeatStatus.AVAILABLE) {
                    throw new RuntimeException("Seat not available");
                }

                seat.status = SeatStatus.LOCKED;
                bookedSeats.add(seat);
            }

            // payment success assumed

            for (Seat seat : bookedSeats) {
                seat.status = SeatStatus.BOOKED;
            }

            return new Booking(UUID.randomUUID().toString(), user, show, bookedSeats);

        } finally {
            for (ReentrantLock lock : locks) {
                lock.unlock();
            }
        }
    }

    public void cancelBooking(Booking booking) {
        for (Seat seat : booking.seats) {
            seat.status = SeatStatus.AVAILABLE;
        }

        booking.cancel();

        System.out.println("Refund processed");
    }
}