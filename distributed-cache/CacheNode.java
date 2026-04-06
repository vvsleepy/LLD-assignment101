import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a single server/node in the distributed cache.
 * Has a strict capacity and relies on an EvictionPolicy to manage memory.
 */
public class CacheNode<K, V> {
    
    private final String nodeId;
    private final int capacity;
    private final Map<K, V> store;
    private final EvictionPolicy<K> evictionPolicy;

    public CacheNode(String nodeId, int capacity, EvictionPolicy<K> evictionPolicy) {
        this.nodeId = nodeId;
        this.capacity = capacity;
        // ConcurrentHashMap allows thread-safe reading/writing
        this.store = new ConcurrentHashMap<>();
        this.evictionPolicy = evictionPolicy;
    }

    public String getNodeId() {
        return nodeId;
    }

    /**
     * Reads a value from the cache.
     * Updates eviction policy history on access.
     */
    public V get(K key) {
        if (store.containsKey(key)) {
            evictionPolicy.keyAccessed(key);
            System.out.println("[Node " + nodeId + "] Cache HIT for key: " + key);
            return store.get(key);
        }
        System.out.println("[Node " + nodeId + "] Cache MISS for key: " + key);
        return null;
    }

    /**
     * Writes a value into the cache.
     * If capacity is reached, it will evict an item based on the policy.
     */
    public synchronized void put(K key, V value) {
        if (!store.containsKey(key)) {
            // Need to check capacity before adding a new item
            if (store.size() >= capacity) {
                K evictedKey = evictionPolicy.evictKey();
                if (evictedKey != null) {
                    store.remove(evictedKey);
                    System.out.println("[Node " + nodeId + "] Capacity reached. EVICTED key: " + evictedKey);
                }
            }
        }
        
        // Add or update the value and notify the eviction policy
        store.put(key, value);
        evictionPolicy.keyAccessed(key);
        System.out.println("[Node " + nodeId + "] Put key: " + key);
    }
}
