import java.util.Scanner;


public class FlipFlopSimulator {
    private static final Scanner scanner = new Scanner(System.in);
    private static int sharedOutput = 0;

    private static int[] rsInputs = new int[2]; 
    private static int[] dInput = new int[1]; 
    private static int[] jkInputs = new int[2]; 
    private static int[] tInput = new int[1];

    public static void main(String[] args) {
        System.out.println("Welcome to Flip-Flop Simulator");

        // instantiation of class objects
        RSFlipFlop rsFlipFlop = new RSFlipFlop(scanner);
        DFlipFlop dFlipFlop = new DFlipFlop(scanner);
        JKFlipFlop jkFlipFlop = new JKFlipFlop(scanner);
        TFlipFlop tFlipFlop = new TFlipFlop(scanner);

        boolean running = true;

        while (running) {
            displayMainMenu();

            // use getInput method from each FlipFlop
            // if the input is outside the range of 1 to 5, then it is showcased as an error
            int choice = rsFlipFlop.getIntInput("Enter your choice (1-5): ", 1, 5);

            switch (choice) {
                case 1:
                    // RS FlipFlop for case 1
                    // simulates the function of an RS FlipFlop
                    rsFlipFlop.setOutput(sharedOutput);
                    rsFlipFlop.setPreviousInputs(rsInputs);
                    rsFlipFlop.simulate(); // overridden function found in FlipFlop abstract class
                    sharedOutput = rsFlipFlop.getOutput();
                    rsInputs = rsFlipFlop.getPreviousInputs();
                    break;
                case 2:
                    // D FlipFlop for case 2
                    // simulates the function of a D FlipFlop
                    dFlipFlop.setOutput(sharedOutput);
                    dFlipFlop.setPreviousInputs(dInput);
                    dFlipFlop.simulate();
                    sharedOutput = dFlipFlop.getOutput();
                    dInput = dFlipFlop.getPreviousInputs();
                    break;
                case 3:
                    // JK FlipFlop for case 3
                    // simulates the function of a JK FlipFlop  
                    jkFlipFlop.setOutput(sharedOutput);
                    jkFlipFlop.setPreviousInputs(jkInputs);
                    jkFlipFlop.simulate();
                    sharedOutput = jkFlipFlop.getOutput();
                    jkInputs = jkFlipFlop.getPreviousInputs();
                    break;
                case 4:
                    // T FlipFlop for case 4
                    // simulates the function of a T FlipFlop
                    tFlipFlop.setOutput(sharedOutput);
                    tFlipFlop.setPreviousInputs(tInput);
                    tFlipFlop.simulate();
                    sharedOutput = tFlipFlop.getOutput();
                    tInput = tFlipFlop.getPreviousInputs();
                    break;
                case 5:
                    // Stops running if 5 is picked
                    running = false;
                    System.out.println("Exiting the program. Goodbye!");
                    break;
            }
        }

        scanner.close(); // close when the program stops running
    }

    // displays FlipFlop type options
    private static void displayMainMenu() {
        System.out.println("\n===== FLIP-FLOP SIMULATOR =====");
        System.out.println("Current Output State (Q): " + sharedOutput);
        System.out.println("1. RS Flip-Flop");
        System.out.println("2. D Flip-Flop");
        System.out.println("3. JK Flip-Flop");
        System.out.println("4. T Flip-Flop");
        System.out.println("5. Exit");
        System.out.println("==============================");
    }
}