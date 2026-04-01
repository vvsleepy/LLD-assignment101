package com.example.elevator;

import java.util.List;

public class RequestScheduler {

    public Elevator assignElevator(List<Elevator> elevators, Request req) {
        Elevator best = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            if (e.state == ElevatorState.MAINTENANCE) continue;

            int dist = Math.abs(e.currentFloor - req.source);
            if (dist < minDistance) {
                minDistance = dist;
                best = e;
            }
        }
        return best;
    }
}