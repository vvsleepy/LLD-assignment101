/**
 * Strategy pattern interface for evicting elements from a CacheNode.
 * Pluggable policy allows switching between LRU, LFU, FIFO, etc.
 */
public interface EvictionPolicy<K> {
    
    /**
     * Called whenever a key is accessed (read or write) 
     * to update its usage history.
     */
    void keyAccessed(K key);
    
    /**
     * Called when the cache is full and an item needs to be removed.
     * @return The key that should be evicted according to the policy.
     */
    K evictKey();
}
