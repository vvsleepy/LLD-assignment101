# Rate Limiter System

This project contains an easily extensible Low-Level Design (LLD) for an API Rate Limiter mapping client traffic traversing an internal network toward an external paid resource API.

## Design Details

### Data Structures & Algorithms (DSA)
The primary mechanism powering the **Sliding Window** rate limiter logic is a simple linear algorithmic approach wrapped across a concurrent Queue data structure (`java.util.LinkedList` implemented into `Queue<Long>`).
1. **Queue Property**: First In, First Out (FIFO).
2. **Algorithm**: 
   - Every time a request attempts entry, we log the exact millisecond (`System.currentTimeMillis()`).
   - Before evaluating if there is room for the request, we iteratively `poll()` (pop) the head of the Queue if the distance between current time and the head's timestamp implies it is older than the `windowSizeInMillis`. This guarantees the Queue exactly maps to the designated sliding window.
3. **Concurrency Control**: 
   - Traffic hits a web server unevenly and parallelly. By nesting this operation block under a generic `synchronized` modifier targeting our Strategy's instance, we entirely prevent simultaneous writes bypassing the mathematical boundaries of the Sliding Window due to micro-stutters.

### Software Engineering Principles
- **Single Responsibility Principle (SRP)**: The `Orchestrator` holds no limiting logic. It manages its single job: calling the proxy pipeline and delivering the result. 
- **Open/Closed Principle (OCP)**: By utilizing the Interface `RateLimitingStrategy` via the Proxy layer, developers can introduce a `FixedWindowStrategy` or a `TokenBucketStrategy` simply by extending logic isolated entirely outside the `Orchestrator`, never risking disrupting central business processes.
- **Dependency Inversion**: `Orchestrator` strictly relies on the `RemoteResource` Interface, remaining completely uncoupled from `SlidingWindow` specifics.

## Running the Application
The `Main.java` executes a 15-thread simultaneous traffic burst test simulating clients aggressively overriding a 5 Request/sec boundary. Run via:
```sh
javac *.java
java Main
```
