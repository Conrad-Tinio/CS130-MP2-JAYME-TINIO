public class DFlipFlop extends FlipFlop {

    public DFlipFlop(java.util.Scanner scanner) {
        super("D", scanner);
    }

    @Override 
    protected void previousInputsTracker() {
        previousInputs = new int[1];
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
            scheduleInputReset();

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

        // for printing previous inputs 
        String prevInputs = "N/A";
        if (previousInputsExist) {
            prevInputs = "D=" + previousInputs[0]; 
        }
        // System.out.println("Previous Inputs: " + prevInputs);
        // System.out.println("Current Input: D=" + d);
        // System.out.println("Previous Output: Q=" + previousQ);
        // System.out.println("New Output: Q=" + newQ);

        displayTableHeader("Previous Inputs", "D", "Q", "Q(t+1)");
        displayTableRow(prevInputs, ""+d, ""+previousQ, ""+newQ);
        displayTableFooter(4);
    }
}