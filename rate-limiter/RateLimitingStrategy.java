/**
 * The Strategy interface. By keeping this independent, we easily satisfy the
 * Open/Closed Principle. If you decide to add a FixedWindowStrategy tomorrow,
 * you don't need to touch the existing RequestHandlerService logic at all.
 */
public interface RateLimitingStrategy {
    // We pass a 'key' (like client ID or API key) so each user has their own limits.
    boolean allowRequest(String key);
}
