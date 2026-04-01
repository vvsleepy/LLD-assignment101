package com.example.bookmyshow;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class LockManager {
    private static ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    public static ReentrantLock getLock(String key) {
        locks.putIfAbsent(key, new ReentrantLock());
        return locks.get(key);
    }
}