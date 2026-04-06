/**
 * This is the Proxy object perfectly following the Proxy Design Pattern.
 * Notice that it implements the EXACT SAME interface (RemoteResource) 
 * as the real endpoint we want to hit.
 * 
 * To the client, they don't even know they are talking to a Rate Limiter.
 * They think they are talking directly to the External API!
 */
public class RateLimitingProxy implements RemoteResource {

    // The Proxy holds a reference to the REAL object
    private RemoteResource realSubject;
    // The Proxy holds its operational logic
    private RateLimitingStrategy limiterStrategy;

    public RateLimitingProxy(RemoteResource realSubject, RateLimitingStrategy limiterStrategy) {
        this.realSubject = realSubject;
        this.limiterStrategy = limiterStrategy;
    }

    @Override
    public String fetchData(Request request) {
        // Figure out who is making the request out of the payload.
        String clientKey = request.getClientId();

        // The Proxy intercepts the call and asks the strategy if THIS specific user is allowed.
        if (limiterStrategy.allowRequest(clientKey)) {
            // If passed, it delegates the method call entirely to the real subject.
            return realSubject.fetchData(request);
        } else {
            // If blocked, the Proxy aborts the request before it ever reaches the real target.
            return "HTTP 429: Too many requests for client: " + clientKey;
        }
    }
}
