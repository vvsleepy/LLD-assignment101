import java.util.List;

/**
 * The orchestrator/facade for the Distributed Cache.
 * Hides the complexity of routing and database fallbacks from the client.
 */
public class DistributedCache<K, V> {

    private final List<CacheNode<K, V>> nodes;
    private final DistributionStrategy<K, V> distributionStrategy;
    private final DatabaseProvider<K, V> dbProvider;

    public DistributedCache(
            List<CacheNode<K, V>> nodes, 
            DistributionStrategy<K, V> strategy, 
            DatabaseProvider<K, V> dbProvider) {
        this.nodes = nodes;
        this.distributionStrategy = strategy;
        this.dbProvider = dbProvider;
    }

    /**
     * Client-facing read request.
     * Implements Cache-Aside (Lazy Loading) pattern.
     */
    public V get(K key) {
        // 1. Find the correct node
        CacheNode<K, V> targetNode = distributionStrategy.getNode(key, nodes);
        
        // 2. Attempt to read from cache
        V value = targetNode.get(key);
        
        // 3. Cache Miss strategy
        if (value == null && dbProvider != null) {
            System.out.println("[Coordinator] Fetching key '" + key + "' from DB...");
            value = dbProvider.fetch(key);
            
            if (value != null) {
                // 4. Update the cache for future reads
                targetNode.put(key, value);
            }
        }
        
        return value;
    }

    /**
     * Client-facing write request.
     */
    public void put(K key, V value) {
        // 1. Find the correct node
        CacheNode<K, V> targetNode = distributionStrategy.getNode(key, nodes);
        
        // 2. Put into the node
        targetNode.put(key, value);
    }
}
