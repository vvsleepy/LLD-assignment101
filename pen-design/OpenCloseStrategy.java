
// This strategy handles the varying start and close behaviors
// like taking a cap off or clicking a button.
public interface OpenCloseStrategy {
    void start();
    void close();
}
