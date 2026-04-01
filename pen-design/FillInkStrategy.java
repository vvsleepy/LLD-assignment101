
// This strategy handles pens that are refilled by dropping ink
public class FillInkStrategy implements RefillableStrategy {

    @Override
    public void refill() {
        System.out.println("Refilling the pen by dropping ink into the reservoir...");
    }

}
