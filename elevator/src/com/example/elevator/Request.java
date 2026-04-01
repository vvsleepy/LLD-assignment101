package com.example.elevator;

public class Request {
    int source;
    int destination;
    Direction direction;

    public Request(int source, int destination) {
        this.source = source;
        this.destination = destination;
        this.direction = destination > source ? Direction.UP : Direction.DOWN;
    }
}