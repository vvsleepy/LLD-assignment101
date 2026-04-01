package com.example.elevator;

import java.util.*;

public class Elevator {
    int id;
    int currentFloor;
    int maxWeight;
    int currentWeight;
    ElevatorState state;

    PriorityQueue<Integer> upQueue = new PriorityQueue<>();
    PriorityQueue<Integer> downQueue = new PriorityQueue<>((a,b) -> b-a);

    public Elevator(int id, int maxWeight) {
        this.id = id;
        this.maxWeight = maxWeight;
        this.currentFloor = 0;
        this.state = ElevatorState.IDLE;
    }

    public synchronized void addRequest(int floor) {
        if (state == ElevatorState.MAINTENANCE) return;

        if (floor > currentFloor) upQueue.offer(floor);
        else downQueue.offer(floor);
    }

    // public synchronized void move() {
    //     if (state == ElevatorState.MAINTENANCE) return;

    //     if (!upQueue.isEmpty()) {
    //         state = ElevatorState.UP;
    //         currentFloor = upQueue.poll();
    //     } else if (!downQueue.isEmpty()) {
    //         state = ElevatorState.DOWN;
    //         currentFloor = downQueue.poll();
    //     } else {
    //         state = ElevatorState.IDLE;
    //     }
    // }

    public synchronized void move() {
        if (state == ElevatorState.MAINTENANCE) {
            System.out.println("Elevator " + id + " is under maintenance");
            return;
        }

        if (!upQueue.isEmpty()) {
            state = ElevatorState.UP;
            int nextFloor = upQueue.poll();
            System.out.println("Elevator " + id + " moving UP from " 
                + currentFloor + " to " + nextFloor);
            currentFloor = nextFloor;

        } else if (!downQueue.isEmpty()) {
            state = ElevatorState.DOWN;
            int nextFloor = downQueue.poll();
            System.out.println("Elevator " + id + " moving DOWN from " 
                + currentFloor + " to " + nextFloor);
            currentFloor = nextFloor;

        } else {
            state = ElevatorState.IDLE;
            System.out.println("Elevator " + id + " is IDLE at floor " + currentFloor);
        }
    }

    public synchronized void addWeight(int weight) {
        currentWeight += weight;
        if (currentWeight > maxWeight) {
            System.out.println("⚠️ Overweight! Doors opening...");
            alarm();
            state = ElevatorState.IDLE;
        }
    }

    public void alarm() {
        System.out.println("🚨 Alarm in elevator " + id);
    }

    public void emergencyStop() {
        System.out.println("🛑 Emergency stop in elevator " + id);
        state = ElevatorState.IDLE;
    }

    public void setMaintenance(boolean flag) {
        state = flag ? ElevatorState.MAINTENANCE : ElevatorState.IDLE;
    }

    public boolean hasPendingRequests() {
        return !upQueue.isEmpty() || !downQueue.isEmpty();
    }
}