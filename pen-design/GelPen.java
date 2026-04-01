

public class GelPen extends Pen {

    // Gel pens usually have a cap, but here we can pass any mechanism (cap or click).
    // They are refilled by changing the refill entirely.
    public GelPen(String colour, OpenCloseStrategy mechanism) {
        super(PenType.GEL, colour, mechanism, new ChangeRefillStrategy());
    }
}
