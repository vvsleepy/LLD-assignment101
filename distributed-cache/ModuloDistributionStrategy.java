import java.util.List;

/**
 * A simple distribution strategy that assigns a key to a node 
 * using a modulo hashing algorithm.
 */
public class ModuloDistributionStrategy<K, V> implements DistributionStrategy<K, V> {

    @Override
    public CacheNode<K, V> getNode(K key, List<CacheNode<K, V>> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            throw new IllegalStateException("No cache nodes available.");
        }
        
        // Ensure positive hash code since modulo with negative numbers returns negatives
        int hash = Math.abs(key.hashCode());
        int nodeIndex = hash % nodes.size();
        
        return nodes.get(nodeIndex);
    }
}
