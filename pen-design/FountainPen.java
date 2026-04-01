// Default package used so VSCode Run button works

public class FountainPen extends Pen {

    // Fountain pens usually have a cap and are refilled by filling ink.
    public FountainPen(String colour) {
        super(PenType.FOUNTAIN, colour, new CapStrategy(), new FillInkStrategy());
    }
}
