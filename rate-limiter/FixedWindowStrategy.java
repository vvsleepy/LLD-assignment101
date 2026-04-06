import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FixedWindowStrategy implements RateLimitingStrategy {
    private final int maxRequests;
    private final long windowSizeInMillis;
    
    // Map of users to their specific request count.
    // ConcurrentHashMap allows safe multi-threading when adding new clients.
    private final ConcurrentHashMap<String, AtomicInteger> clientCounters;
    
    // Tracks when the current fixed window started globally via Atomic variables safely.
    private final AtomicLong windowStartTime;

    public FixedWindowStrategy(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.clientCounters = new ConcurrentHashMap<>();
        this.windowStartTime = new AtomicLong(System.currentTimeMillis());
    }

    @Override
    public boolean allowRequest(String key) {
        long currentTime = System.currentTimeMillis();
        long currentWindowStart = windowStartTime.get();

        // 1. Check if the fixed window block of time has fully eclipsed (e.g. the minute passed). 
        // If so, clear out the old records heavily to start a new window.
        if (currentTime - currentWindowStart >= windowSizeInMillis) {
            // Atomic check to ensure only ONE thread clears out the map at the turn of a window.
            if (windowStartTime.compareAndSet(currentWindowStart, currentTime)) {
                clientCounters.clear(); // Wipe the slate clean for everyone.
            }
        }

        // 2. Find out how many times this specific user has requested in the current window.
        // If they are brand new, start them at 0 using an AtomicInteger.
        clientCounters.putIfAbsent(key, new AtomicInteger(0));
        AtomicInteger userCounter = clientCounters.get(key);

        // 3. Keep trying to increase their count safely (in case multiple threads of the same user hit at once)
        while (true) {
            int currentCount = userCounter.get();
            if (currentCount >= maxRequests) {
                // They are at the limit for this time window.
                return false;
            }
            // Safely attempt to increment their specific count.
            // compareAndSet acts as a lock-free thread safety net for concurrent counting!
            if (userCounter.compareAndSet(currentCount, currentCount + 1)) {
                // They sneaked a request through correctly.
                return true;
            }
        }
    }
}
