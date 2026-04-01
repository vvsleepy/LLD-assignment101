package com.example.elevator;

import java.util.List;

public class ElevatorController {

    List<Elevator> elevators;
    RequestScheduler scheduler = new RequestScheduler();

    public ElevatorController(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    // public void requestElevator(int source, int destination) {
    //     Request req = new Request(source, destination);
    //     Elevator e = scheduler.assignElevator(elevators, req);

    //     if (e != null) {
    //         e.addRequest(source);
    //         e.addRequest(destination);
    //     }
    // }

    public void requestElevator(int source, int destination) {
        System.out.println("\n📞 Request from " + source + " to " + destination);

        Request req = new Request(source, destination);
        Elevator e = scheduler.assignElevator(elevators, req);

        if (e != null) {
            System.out.println("👉 Assigned Elevator: " + e.id);
            e.addRequest(source);
            e.addRequest(destination);
        }
    }

    public void selectFloor(Elevator e, int floor) {
        e.addRequest(floor);
    }

    public void emergency(Elevator e) {
        e.emergencyStop();
    }
}