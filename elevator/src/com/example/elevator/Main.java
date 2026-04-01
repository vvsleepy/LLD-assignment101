package com.example.elevator;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Elevator e1 = new Elevator(1, 700);
        Elevator e2 = new Elevator(2, 800);

        List<Elevator> elevators = Arrays.asList(e1, e2);

        ElevatorController controller = new ElevatorController(elevators);

        controller.requestElevator(0, 5);
        controller.requestElevator(2, 8);

        // Run until all requests are done
        while (hasPendingWork(elevators)) {

            for (Elevator e : elevators) {
                e.move();
            }

            try { Thread.sleep(1000); }
            catch (Exception ignored) {}
        }

        System.out.println("\n All requests completed. System shutting down.");
    }

    private static boolean hasPendingWork(List<Elevator> elevators) {
        for (Elevator e : elevators) {
            if (e.hasPendingRequests()) return true;
        }
        return false;
    }
}