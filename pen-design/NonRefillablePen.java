// Default package used so VSCode Run button works

public class NonRefillablePen extends Pen {

    // These pens can be any type, but they cannot be refilled!
    public NonRefillablePen(PenType type, String colour, OpenCloseStrategy mechanism) {
        super(type, colour, mechanism, new NonRefillableStrategy());
    }
}
