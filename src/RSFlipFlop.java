public class RSFlipFlop extends FlipFlop {
    private java.util.List<String> iterationHistory = new java.util.ArrayList<>();

    public RSFlipFlop(java.util.Scanner scanner) {
        super("RS", scanner);
        continueProcessReset = true;
    }

    @Override
    protected void previousInputsTracker() {
        previousInputs = new int[2]; 
    }

    @Override
    public void clearOutputs() {
        previousInputs[0] = 0; 
        previousInputs[1] = 0; 
        previousOutput = 0;
        iterationHistory.clear();
    }

    @Override 
    protected void processInputReset() {
        System.out.println("\nNOTE: RS inputs have reset back to 0.");
        System.out.println("\n===== RS FLIP-FLOP RESET =====");

        displayTableHeader("Previous Input", "R", "S", "Q(t+1)");
        displayTableRow("Auto", "0", "0", ""+previousOutput);
        displayTableFooter(4);
    }

    @Override
    public void displayTruthTable() {
        displayTableHeader( "S", "R", "Q(t+1)");
        displayTableRow("0", "0", "Q(t) / No Change");
        displayTableRow("0", "1", "0    / Reset");
        displayTableRow("1", "0", "1    / Set");
        displayTableRow("1", "1", "?    / Unpredictable");
        displayTableFooter(3);
    }

    @Override
    public void simulate() {
        // Starts out with 0 (default value) as previous input
        System.out.println("\n===== RS FLIP-FLOP SIMULATION =====");
        System.out.println("Truth Table for RS Flip Flop:");
        displayTruthTable();
        System.out.println("Previous output (Q): " + previousOutput);

        boolean flipFlopRunning = true;

        String previousInputsDisplay = "N/A";
        if (previousInputs[0] != 0 || previousInputs[1] != 0) {
            previousInputsDisplay = "R=" + previousInputs[0] + ", S=" + previousInputs[1]; 
        } 
        System.out.println("Previous Inputs: " + previousInputsDisplay);

        while (flipFlopRunning) {
            System.out.println("\nCurrent RS Flip-Flop State:");
            System.out.println("- Previous Output (Q): " + previousOutput);

            // obtains user-input
            // R and S for an RS FlipFlop
            System.out.println("\nEnter input values (inputs will reset to 0 after a few seconds)");
            int r = getIntInput("Enter R (0 or 1): ", 0, 1);
            int s = getIntInput("Enter S (0 or 1): ", 0, 1);

            // Set inputs as active and schedule reset
            inputActive = true;
            scheduleInputReset();

            // uses values of r, s, and previous output to calculate the new output
            int newOutput = calculateOutput(r, s, previousOutput);

            // displays new results and makes the new output to previous output
            displayResults(r, s, previousOutput, newOutput);
            previousOutput = newOutput;

            previousInputs[0] = r; 
            previousInputs[1] = s; 
            previousInputsExist = true;

            // menu prompt
            System.out.println("\nDo you want to continue with RS Flip-Flop?");
            System.out.println("1. Continue with RS Flip-Flop");
            System.out.println("2. Back to Main Menu");

            // terminates FlipFlop if 2 is picked
            int choice = getIntInput("Enter your choice (1-2): ", 1, 2);
            if (choice == 2) {
                clearOutputs();
                flipFlopRunning = false;
            }
        }
    }

    private int calculateOutput(int r, int s, int previousQ) {
        // if r and s is 0 then it copies the previous Q
        // since this input showcases memory
        if (r == 0 && s == 0) {
            return previousQ; // No change
        // if r is 0 and s is 1, this showcases that the FlipFlop is activated
        // since set or S is 1 or activated
        } else if (r == 0 && s == 1) {
            return 1; // Set
        // if r is 1 and s is 0, this showcases that the FlipFlop is deactivated
        // since reset is 1 or activated
        } else if (r == 1 && s == 0) {
            return 0; // Reset
        // if r and s are 1, this showcases an invalid input
        // since the FlipFlop does not know whether to activate or deactivate
        // keep previous value but give a warning
        } else {
            System.out.println("Warning: Invalid state (R=1, S=1) - output is undefined.");
            return previousQ;
        }
    }

    // displays result of current iteration
    private void displayResults(int r, int s, int previousQ, int newQ) {
        System.out.println("\n===== RESULTS =====");
        System.out.println("Flip-Flop Type: RS");

        // Add current iteration to history
        String currentIteration;
        if (r == 1 && s == 1) {
            currentIteration = String.format("R=%d, S=%d → Q(t)=%d → Undefined", r, s, previousQ);
        } else {
            currentIteration = String.format("R=%d, S=%d → Q(t)=%d → %d", r, s, previousQ, newQ);
        }
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
        if (r == 1 && s == 1) {
            System.out.println("Note: R=1, S=1 is an invalid state for RS Flip-Flop.");
            displayTableHeader("Previous Input", "R", "S", "Q(t+1)");
            displayTableRow(previousInputsExist ? "R=" + previousInputs[0] + ", S=" + previousInputs[1] : "N/A", ""+r, ""+s, "Undefined");
            displayTableFooter(4);
        } else {
            displayTableHeader("Previous Input", "R", "S", "Q(t+1)");
            displayTableRow(previousInputsExist ? "R=" + previousInputs[0] + ", S=" + previousInputs[1] : "N/A", ""+r, ""+s, ""+newQ);
            displayTableFooter(4);
        }
    }
}