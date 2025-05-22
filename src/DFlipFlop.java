public class DFlipFlop extends FlipFlop {
    private java.util.List<String> iterationHistory = new java.util.ArrayList<>();

    public DFlipFlop(java.util.Scanner scanner) {
        super("D", scanner);
        continueProcessReset = false;
    }

    @Override 
    protected void previousInputsTracker() {
        previousInputs = new int[1];
    }

    @Override
    protected void processInputReset() {
        System.out.println("D Flip Flop does not require any input reset");
    }

    @Override
    public void clearOutputs() {
        previousInputs[0] = 0; 
        previousOutput = 0;
        iterationHistory.clear();
    }

    @Override
    public void displayTruthTable() {
        displayTableHeader( "D", "Q(t+1)");
        displayTableRow("0", "0");
        displayTableRow("1", "1");
        displayTableFooter(2);
    }

    @Override
    public void simulate() {
        // starts out with 0 (default value)
        System.out.println("\n===== D FLIP-FLOP SIMULATION =====");
        displayTruthTable();
        System.out.println("Previous output (Q): " + previousOutput);

        boolean flipFlopRunning = true;

        String previousInputsDisplay = "N/A";
        if (previousInputs[0] != 0) {
            previousInputsDisplay = "D=" + previousInputs[0]; 
        } 
        System.out.println("Previous Inputs: " + previousInputsDisplay);

        while (flipFlopRunning) {
            System.out.println("\nCurrent D Flip-Flop State:");
            System.out.println("- Previous Output (Q): " + previousOutput); // starts out with 0

            System.out.println("\nEnter input value (input will reset to 0 after a few seconds)");

            // D FlipFlops only have D as their input
            // valid inputs: 0 or 1
            int d = getIntInput("Enter D (0 or 1): ", 0, 1);

            // inputs are now active
            inputActive = true;

            // gives out calculated d as argument
            int newOutput = calculateOutput(d);

            // displays results
            displayResults(d, previousOutput, newOutput);
            previousOutput = newOutput;

            previousInputs[0] = d;
            previousInputsExist = true;

            System.out.println("\nDo you want to continue with D Flip-Flop?");
            System.out.println("1. Continue with D Flip-Flop");
            System.out.println("2. Back to Main Menu");

            // stops running if 2 is chosen
            int choice = getIntInput("Enter your choice (1-2): ", 1, 2);
            if (choice == 2) {
                clearOutputs();
                flipFlopRunning = false;
            }
        }
    }

    // for D FlipFlops if D = 0, then s = 0, r = 1
    // this simulates a deactivated FlipFlop which outputs Q=0
    // for D FlipFlops if D = 1, then s = 1, r = 0
    // this simulates an activated FlipFlop which outputs Q=1
    // which is why the inputted D value is equivalent to the output
    private int calculateOutput(int d) {
        return d; // For D flip-flop, output equals input
    }

    // displays current results
    private void displayResults(int d, int previousQ, int newQ) {
        System.out.println("\n===== RESULTS =====");
        System.out.println("Flip-Flop Type: D");

        // Add current iteration to history
        String currentIteration = String.format("D=%d → %d", d, newQ);
        iterationHistory.add(currentIteration);

        // Display history of iterations
        System.out.println("\nIteration History:");
        displayTableHeader("Iteration", "Input", "Q(t+1)");
        for (int i = 0; i < iterationHistory.size(); i++) {
            String[] parts = iterationHistory.get(i).split(" → ");
            displayTableRow("" + (i + 1), parts[0], parts[1]);
        }
        displayTableFooter(3);

        // Display current state
        System.out.println("\nCurrent State:");
        displayTableHeader("Previous Input", "D", "Q", "Q(t+1)");
        displayTableRow(previousInputsExist ? "D=" + previousInputs[0] : "N/A", ""+d, ""+previousQ, ""+newQ);
        displayTableFooter(4);
    }
}