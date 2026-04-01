
// A specific strategy where clicking a button starts the pen
public class ClickStrategy implements OpenCloseStrategy {
    @Override
    public void start() {
        System.out.println("Clicking the button to reveal the nib.");
    }

    @Override
    public void close() {
        System.out.println("Clicking the button again to retract the nib.");
    }
}
