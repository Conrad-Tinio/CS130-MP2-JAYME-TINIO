public class TFlipFlop extends FlipFlop {
    private java.util.List<String> iterationHistory = new java.util.ArrayList<>();

    public TFlipFlop(java.util.Scanner scanner) {
        super("T", scanner);
        continueProcessReset = false;
    }

    @Override
    protected void previousInputsTracker() {
        previousInputs = new int[1];
    }

    @Override
    protected void processInputReset() {
        System.out.println("T Flip Flop does not require any input reset");
    }

    @Override
    public void clearOutputs() {
        previousInputs[0] = 0;
        previousOutput = 0;
        iterationHistory.clear();
    }

    @Override
    public void displayTruthTable() {
        displayTableHeader( "T", "Q(t+1)");
        displayTableRow("0", "Q(t)  / No Change");
        displayTableRow("1", "Q'(t) / Complement");
        displayTableFooter(2);
    }

    @Override
    public void simulate() {
        // default previous input: 0
        System.out.println("\n===== T FLIP-FLOP SIMULATION =====");
        displayTruthTable();
        System.out.println("Previous output (Q): " + previousOutput);

        boolean flipFlopRunning = true;

        String previousInputsDisplay = "N/A";
        if (previousInputs[0] != 0) {
            previousInputsDisplay = "T=" + previousInputs[0];
        }
        System.out.println("Previous Inputs: " + previousInputsDisplay);

        while (flipFlopRunning) {
            // default previous input = 0
            System.out.println("\nCurrent T Flip-Flop State:");
            System.out.println("- Previous Output (Q): " + previousOutput);

            // Inputs for T FlipFlop is T
            // valid inputs: 0 or 1
            System.out.println("\nEnter input value (input will reset to 0 after a few seconds)");
            int t = getIntInput("Enter T (0 or 1): ", 0, 1);

            // sets inputs as active 
            inputActive = true;

            // uses inputted T value and previous output to calculate new output
            int newOutput = calculateOutput(t, previousOutput);

            // displays results
            displayResults(t, previousOutput, newOutput);
            previousOutput = newOutput;

            previousInputs[0] = t;
            previousInputsExist = true; 

            System.out.println("\nDo you want to continue with T Flip-Flop?");
            System.out.println("1. Continue with T Flip-Flop");
            System.out.println("2. Back to Main Menu");

            // if 2 is chosen, goes back to main menu
            int choice = getIntInput("Enter your choice (1-2): ", 1, 2);
            if (choice == 2) {
                clearOutputs();
                flipFlopRunning = false;
            }
        }
    }

    private int calculateOutput(int t, int previousQ) {
        // if t = 0, this means that previous output is kept
        // ex: if previous Q was 0, then current Q will be 0 too; vice versa
        if (t == 0) {
            return previousQ; // No change
        // if t = 1, this means that the previous output will toggle
        // ex: if previous Q was 0, then current Q will be 1; vice versa
        } else { // t == 1
            return (previousQ == 0) ? 1 : 0; // Toggle
        }
    }

    // displays current results
    private void displayResults(int t, int previousQ, int newQ) {
        System.out.println("\n===== RESULTS =====");
        System.out.println("Flip-Flop Type: T");

        // Add current iteration to history
        String currentIteration = String.format("T=%d → Q(t)=%d → %d", t, previousQ, newQ);
        iterationHistory.add(currentIteration);

        // Display history of iterations
        System.out.println("\nIteration History:");
        displayTableHeader("Iteration", "Input", "Q(t)", "Q(t+1)");
        for (int i = 0; i < iterationHistory.size(); i++) {
            String[] parts = iterationHistory.get(i).split(" → ");
            displayTableRow("" + (i + 1), parts[0], parts[1], parts[2]);
        }
        displayTableFooter(4);

        // Display current state
        System.out.println("\nCurrent State:");
        if (t == 1) {
            displayTableHeader("Previous Input", "T", "Q", "Q(t+1)");
            displayTableRow(previousInputsExist ? "T=" + previousInputs[0] : "N/A", ""+t, ""+previousQ, ""+newQ);
            displayTableFooter(4);
            System.out.println("NOTE: When t=1, it 'toggles' (complements) the current Q");
        } else {
            displayTableHeader("Previous Input", "T", "Q", "Q(t+1)");
            displayTableRow(previousInputsExist ? "T=" + previousInputs[0] : "N/A", ""+t, ""+previousQ, ""+newQ);
            displayTableFooter(4);
        }
    }
}