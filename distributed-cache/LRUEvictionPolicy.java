import java.util.LinkedHashSet;

/**
 * Implementation of the Least Recently Used (LRU) eviction policy.
 * 
 * Uses a LinkedHashSet which maintains insertion order. 
 * Removing and re-adding a key moves it to the "most recently used" end.
 * The "least recently used" element will be at the beginning (iterator().next()).
 */
public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {
    
    // LinkedHashSet provides O(1) add, remove, and contains operations
    // while maintaining a predictable iteration order.
    private final LinkedHashSet<K> accessedKeys;

    public LRUEvictionPolicy() {
        this.accessedKeys = new LinkedHashSet<>();
    }

    @Override
    public synchronized void keyAccessed(K key) {
        // If it exists, remove it first so adding it again pushes it to the back
        accessedKeys.remove(key);
        accessedKeys.add(key);
    }

    @Override
    public synchronized K evictKey() {
        if (accessedKeys.isEmpty()) {
            return null;
        }
        // The first element is the least recently accessed
        K leastRecentlyUsed = accessedKeys.iterator().next();
        accessedKeys.remove(leastRecentlyUsed);
        return leastRecentlyUsed;
    }
}
