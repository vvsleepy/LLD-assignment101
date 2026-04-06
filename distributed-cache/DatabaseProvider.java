/**
 * Functional interface representing a backend database.
 * The cache will fall back to this provider on a cache miss.
 */
public interface DatabaseProvider<K, V> {
    /**
     * Fetches the value for the given key from the database.
     * @param key The key to look up.
     * @return The value from the database, or null if not found.
     */
    V fetch(K key);
}
