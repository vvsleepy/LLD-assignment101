import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== RATE LIMITER SYSTEM INITIALIZATION ==========");
        
        // Scenario: We only allow 5 requests every 1000 milliseconds (1 second) per client.
        RateLimitingStrategy slidingWindow = new SlidingWindowStrategy(5, 1000);
        RateLimitingStrategy fixedWindow = new FixedWindowStrategy(5, 1000);
        
        RemoteResource realBillingApi = new ExternalApiService();
        
        // 1. Setup Proxy with the Sliding Window algorithm.
        RemoteResource rateLimiterProxy = new RateLimitingProxy(realBillingApi, slidingWindow);
        Orchestrator orchestrator = new Orchestrator(rateLimiterProxy);

        System.out.println("\n========== TEST 1: PER-USER LIMITS (SLIDING WINDOW) ==========");
        System.out.println("Scenario: Client_A and Client_B both rapidly fire 7 requests.");
        System.out.println("Expected: They both get 5 successes individually, and 2 rejections each.");
        System.out.println("----------------------------------------------------");
        
        // Spawning parallel threads hammering the system under two different user identities
        ExecutorService executor = Executors.newFixedThreadPool(15);
        for (int i = 1; i <= 7; i++) {
            executor.submit(() -> {
                System.out.println("Client_A -> " + orchestrator.routeRequest(new Request("Client_A", "DataA")));
            });
            executor.submit(() -> {
                System.out.println("Client_B -> " + orchestrator.routeRequest(new Request("Client_B", "DataB")));
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.SECONDS);

        System.out.println("\n========== TEST 2: SWITCHING ALGORITHMS (FIXED WINDOW) ==========");
        System.out.println("Swapping to the FixedWindowStrategy behind the scenes...");
        
        // Easily swapping algorithms by plugging the new strategy into a new proxy.
        // Notice how Orchestrator doesn't have to change anything about how it works!
        RemoteResource newProxy = new RateLimitingProxy(realBillingApi, fixedWindow);
        Orchestrator orchestratorFixed = new Orchestrator(newProxy);

        System.out.println("\nWaiting for 1 second so old times naturally clear...");
        Thread.sleep(1000); 

        System.out.println("\nTesting Client_A again rapidly on the NEW fixed window algorithm:");
        System.out.println("Expected: 5 successes, then failures.");
        System.out.println("----------------------------------------------------");

        for (int i = 1; i <= 7; i++) {
            System.out.println("Client_A (Fixed) -> " + orchestratorFixed.routeRequest(new Request("Client_A", "FreshData")));
        }
    }
}
