// Default package used so VSCode Run button works

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Testing Gel Pen (Click Mechanism) ===");
        // Create a blue gel pen with a click strategy
        Pen gelPen = new GelPen("Blue", new ClickStrategy());
        
        // Let's test the state constraint: trying to write before starting
        System.out.println("> 1. Attempting to write without starting:");
        gelPen.write(); 
        
        System.out.println("> 2. Starting, writing, and closing normally:");
        gelPen.start();
        gelPen.write();
        gelPen.close();
        
        System.out.println("> 3. Testing refill strategy:");
        gelPen.refill();

        System.out.println("\n=== Testing Fountain Pen (Cap Mechanism) ===");
        // Fountain pen inherently uses a CapStrategy and FillInkStrategy
        Pen fountainPen = new FountainPen("Black");
        fountainPen.start();
        fountainPen.write();
        fountainPen.close();
        fountainPen.refill();

        System.out.println("\n=== Testing Non-Refillable Ball Pen ===");
        // A standard use-and-throw pen
        Pen cheapPen = new NonRefillablePen(PenType.BALL, "Red", new CapStrategy());
        cheapPen.start();
        cheapPen.write();
        cheapPen.close();
        cheapPen.refill();
    }
}
