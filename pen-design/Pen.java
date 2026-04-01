// Default package used so VSCode Run button works

/*
 * The Pen base class provides standard functionality that all pens share.
 * It uses the Strategy Pattern for varying start/close and refill behaviors.
 */
public abstract class Pen implements Writable {
    private PenType type;
    private String colour;
    
    // We compose the pen with strategies instead of hardcoding how it opens or refills
    private OpenCloseStrategy openCloseStrategy;
    private RefillableStrategy refillStrategy;
    
    // State flag so I cannot write without starting!
    private boolean isStarted = false;

    // Constructor to setup the common pen fields
    public Pen(PenType type, String colour, OpenCloseStrategy openCloseStrategy, RefillableStrategy refillStrategy) {
        this.type = type;
        this.colour = colour;
        this.openCloseStrategy = openCloseStrategy;
        this.refillStrategy = refillStrategy;
    }

    @Override
    public void start() {
        // Delegate opening mechanism to strategy (like Cap or Click)
        openCloseStrategy.start();
        isStarted = true;
        System.out.println("The " + colour + " pen is ready to write.");
    }

    @Override
    public void write() {
        // Checking the flag as requested: "I cannot write without starting"
        if (!isStarted) {
            System.out.println("Error: Cannot write! Please start the pen before writing.");
            return;
        }
        System.out.println("Writing beautiful text with " + colour + " " + type + " pen...");
    }

    @Override
    public void close() {
        // Delegate closing mechanism to strategy
        openCloseStrategy.close();
        isStarted = false;
        System.out.println("The " + colour + " pen is now closed.");
    }

    public void refill() {
        System.out.println("Attempting to refill the " + type + " pen...");
        // Delegate refill action to strategy (ChangeRefill vs FillInk)
        refillStrategy.refill();
    }

    // Typical getters
    public String getColour() {
        return colour;
    }
}