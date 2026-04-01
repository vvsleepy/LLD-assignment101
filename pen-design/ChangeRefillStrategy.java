
public class ChangeRefillStrategy implements RefillableStrategy {
    @Override
    public void refill() {
        System.out.println("Refilling by replacing the old refill with a new one.");
    }
}
