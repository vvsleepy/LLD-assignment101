import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class SlidingWindowStrategy implements RateLimitingStrategy {
    private int maxRequests;
    private long windowSizeInMillis;
    
    // We use a map to keep a separate history queue for each unique user/key.
    // ConcurrentHashMap safely handles multiple threads creating new users at once.
    private Map<String, Queue<Long>> clientTimestamps;

    public SlidingWindowStrategy(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.clientTimestamps = new ConcurrentHashMap<>();
    }

    @Override
    public boolean allowRequest(String key) {
        long currentTime = System.currentTimeMillis();

        // Get the history queue for this specific user. 
        // If they don't exist yet, create a new empty queue for them.
        Queue<Long> userQueue = clientTimestamps.computeIfAbsent(key, k -> new LinkedList<>());

        // We lock ONLY this specific user's queue so other users checking limits aren't frozen out.
        synchronized (userQueue) {
            // 1. Throw away any old timestamps that fall outside our current time window.
            while (!userQueue.isEmpty() && currentTime - userQueue.peek() > windowSizeInMillis) {
                userQueue.poll(); 
            }

            // 2. Check if this user still has room in their personal queue.
            if (userQueue.size() < maxRequests) {
                // They have space. Add the current time and allow it.
                userQueue.add(currentTime);
                return true;
            }

            // No space left in the limits for this specific user.
            return false;
        }
    }
}
