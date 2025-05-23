public class JKFlipFlop extends FlipFlop {
    private java.util.List<String> iterationHistory = new java.util.ArrayList<>();

    public JKFlipFlop(java.util.Scanner scanner) {
        super("JK", scanner);
        continueProcessReset = true;
    }

    // NOTE: J is S and K is R when comparing to an RS FlipFlop

    @Override
    protected void previousInputsTracker() {
        previousInputs = new int[2];
    }

    @Override 
    protected void processInputReset() {
        System.out.println("\nNOTE: JK inputs have reset back to 0.");
        System.out.println("\n===== JK FLIP-FLOP RESET =====");

        displayTableHeader("Previous Input", "J", "K", "Q(t+1)");
        displayTableRow("Auto", "0", "0", ""+previousOutput);
        displayTableFooter(4);
    }

    @Override
    public void clearOutputs() {
        previousInputs[0] = 0; 
        previousInputs[1] = 0; 
        previousOutput = 0;
        iterationHistory.clear();
    }

    @Override
    public void displayTruthTable() {
        displayTableHeader( "J", "K", "Q(t+1)");
        displayTableRow("0", "0", "Q(t)  / No Change");
        displayTableRow("0", "1", "0     / Reset");
        displayTableRow("1", "0", "1     / Set");
        displayTableRow("1", "1", "Q'(t) / Complement");
        displayTableFooter(3);
    }

    @Override
    public void simulate() {

        // Starts out as 0 (default value) for previous input
        System.out.println("\n===== JK FLIP-FLOP SIMULATION =====");
        displayTruthTable();
        System.out.println("Previous output (Q): " + previousOutput);

        boolean flipFlopRunning = true;

        String previousInputsDisplay = "N/A";
        if (previousInputs[0] != 0 || previousInputs[1] != 0) {
            previousInputsDisplay = "J=" + previousInputs[0] + ", K=" + previousInputs[1]; 
        }
        System.out.println("Previous Inputs: " + previousInputsDisplay);

        while (flipFlopRunning) {
            System.out.println("\nCurrent JK Flip-Flop State:");
            System.out.println("- Previous Output (Q): " + previousOutput); // starts out as 0

            // inputs for a JK FlipFlop are J and K
            // valid inputs for J and K: 0 or 1
            System.out.println("\nEnter input values (inputs will reset to 0 after a few seconds)");
            int j = getIntInput("Enter J (0 or 1): ", 0, 1);
            int k = getIntInput("Enter K (0 or 1): ", 0, 1);

            // Set inputs as active and schedule reset
            inputActive = true;
            scheduleInputReset();

            // gives out j, k, and previous Output as arguments to calculate Q
            int newOutput = calculateOutput(j, k, previousOutput);

            // displays current results and initializes previous output to current one
            displayResults(j, k, previousOutput, newOutput);
            previousOutput = newOutput;

            previousInputs[0] = j; 
            previousInputs[1] = k; 
            previousInputsExist = true;  

            System.out.println("\nDo you want to continue with JK Flip-Flop?");
            System.out.println("1. Continue with JK Flip-Flop");
            System.out.println("2. Back to Main Menu");

            // if 2 is chosen, goes back to main menu
            int choice = getIntInput("Enter your choice (1-2): ", 1, 2);
            if (choice == 2) {
                clearOutputs();
                flipFlopRunning = false;
            }
        }
    }

    private int calculateOutput(int j, int k, int previousQ) {
        // if j and k are 0 then this showcases only the previous Q since this is memory
        if (j == 0 && k == 0) {
            return previousQ; // No change
        // if j = 0 and k = 1, this means that the FlipFlop is deactivated
        // since k is r which stands for reset, the FlipFlop resets so Q = 0
        } else if (j == 0 && k == 1) {
            return 0; // Reset
        // if j = 1 and k = 0, this means that the FlipFlop is activated
        // since s is s which means set, the FlipFlop is set so Q = 1
        } else if (j == 1 && k == 0) {
            return 1; // Set
        // if j and k are 1 then the previous inputs toggles
        // so if previousInput was Q = 1, then current Q = 0; vice versa
        } else {
            return (previousQ == 0) ? 1 : 0; // Toggle
        }
    }

    // displays current results from calculated values
    private void displayResults(int j, int k, int previousQ, int newQ) {
        System.out.println("\n===== RESULTS =====");
        System.out.println("Flip-Flop Type: JK");

        // Add current iteration to history
        String currentIteration = String.format("J=%d, K=%d → Q(t)=%d → %d", j, k, previousQ, newQ);
        iterationHistory.add(currentIteration);

        // Display history of iterations
        System.out.println("\nIteration History:");
        displayTableHeader("Iteration", "Inputs", "Q(t)", "Q(t+1)");
        for (int i = 0; i < iterationHistory.size(); i++) {
            String[] parts = iterationHistory.get(i).split(" → ");
            displayTableRow("" + (i + 1), parts[0], parts[1], parts[2]);
        }
        displayTableFooter(4);

        // Display current state
        System.out.println("\nCurrent State:");
        if (j == 1 && k == 1) {
            displayTableHeader("Previous Input", "J", "K", "Q(t+1)");
            displayTableRow(previousInputsExist ? "J=" + previousInputs[0] + ", K=" + previousInputs[1] : "N/A", ""+j, ""+k, ""+newQ);
            displayTableFooter(4);
            System.out.println("\nNOTE: In a JK Flip-Flop, when J=1, K=1, the current state is complemented.");
        } else {
            displayTableHeader("Previous Input", "J", "K", "Q(t+1)");
            displayTableRow(previousInputsExist ? "J=" + previousInputs[0] + ", K=" + previousInputs[1] : "N/A", ""+j, ""+k, ""+newQ);
            displayTableFooter(4);
        }
    }
}