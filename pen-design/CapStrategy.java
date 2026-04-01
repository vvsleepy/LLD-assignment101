
public class CapStrategy implements OpenCloseStrategy {
    @Override
    public void start() {
        System.out.println("Removing the cap to start writing.");
    }

    @Override
    public void close() {
        System.out.println("Putting the cap back on.");
    }
}
