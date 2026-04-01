// Default package used so VSCode Run button works

// A refill strategy where the pen cannot be refilled
public class NonRefillableStrategy implements RefillableStrategy {
    @Override
    public void refill() {
        System.out.println("This pen cannot be refilled. Please dispose of it when empty.");
    }
}
