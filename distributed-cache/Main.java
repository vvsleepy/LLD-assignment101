import java.util.ArrayList;
import java.util.List;

/**
 * Main application class to demonstrate the Distributed Cache behavior.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("====== INITIALIZING DISTRIBUTED CACHE ======");
        
        // 1. Create a simulated database provider
        DatabaseProvider<String, String> mockDatabase = key -> {
            // Simulated slow DB fetch
            return "DB_VALUE_FOR_" + key;
        };

        // 2. Create the distribution strategy (Modulo)
        DistributionStrategy<String, String> routingStrategy = new ModuloDistributionStrategy<>();

        // 3. Setup a cluster of 3 Cache Nodes.
        // We give each node a tiny capacity of '2' so we can see eviction trigger easily.
        List<CacheNode<String, String>> cluster = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            // Each node uses the LRU Policy
            EvictionPolicy<String> lruPolicy = new LRUEvictionPolicy<>();
            CacheNode<String, String> node = new CacheNode<>("Node_" + i, 2, lruPolicy);
            cluster.add(node);
        }

        // 4. Instantiate the centralized DistributedCache
        DistributedCache<String, String> cache = new DistributedCache<>(cluster, routingStrategy, mockDatabase);

        System.out.println("\n====== TEST 1: CACHE MISS AND READ-THROUGH ======");
        // Client requests "User1" - Should miss cache, hit DB, and load into a node.
        String val1 = cache.get("User1");
        System.out.println("Client received: " + val1);

        System.out.println("\n====== TEST 2: CACHE HIT ======");
        // Second request for "User1" should be blazing fast from the targeted cache node.
        String val2 = cache.get("User1");
        System.out.println("Client received: " + val2);

        System.out.println("\n====== TEST 3: TESTING LRU EVICTION EXHAUSTION ======");
        // A single node only has capacity for 2 items.
        // Modulo distribution might distribute these keys or stack them sequentially depending on hash.
        // For demonstration, let's force feed multiple keys rapidly. We expect random evictions.
        
        // Pushing 10 completely new keys into the cluster
        for (int i = 0; i < 10; i++) {
            cache.put("DataA_" + i, "Some Payload_" + i);
        }
        
        System.out.println("\n====== TEST 4: VERIFYING EVICTION IMPACT ======");
        // The cluster holds 3 nodes with 2 capacity each (total 6 items max).
        // Since we pushed 10 items in Loop 3, we forced multiple LRU evictions.
        // Reading "DataA_1" will likely be a Cache Miss now because it got pushed out!
        String checkMissingVal = cache.get("DataA_1");
        System.out.println("Client received: " + checkMissingVal);
    }
}
