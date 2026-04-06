import java.util.List;

/**
 * Strategy pattern interface to determine which CacheNode 
 * a specific key should reside on.
 */
public interface DistributionStrategy<K, V> {
    
    /**
     * Given a key and a list of available nodes, 
     * routes the key to its designated node.
     * 
     * @param key The cache key
     * @param nodes The list of active cache nodes
     * @return The target CacheNode
     */
    CacheNode<K, V> getNode(K key, List<CacheNode<K, V>> nodes);
}
