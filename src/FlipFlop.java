import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

// abstract class for all FlipFlops
public abstract class FlipFlop {
    protected int previousOutput = 0; // default value for a flip flop
    protected int[] previousInputs; 
    protected boolean inputActive = false; // initial value for input
    protected boolean previousInputsExist = false;
    protected String type;
    protected Scanner scanner;

    // constructor class to obtain type of FlipFlop and scanner
    public FlipFlop(String type, Scanner scanner) {
        this.type = type;
        this.scanner = scanner;
    }

    protected abstract void previousInputsTracker();

    // get and set functions
    public int getOutput() {
        return previousOutput;
    }

    public void setOutput(int output) {
        this.previousOutput = output;
    }

    public int[] getPreviousInputs() {
        return previousInputs;
    }

    public void setPreviousInputs(int[] inputs) {
        this.previousInputs = inputs; 
    }

    public abstract void clearOutputs(); 

    // function is overridden for each FlipFlop class type that extends this class
    public abstract void simulate();

    public abstract void displayTruthTable(); 

    protected abstract void processInputReset(); 

    protected boolean continueProcessReset = true;

    // resets input signals to 0 after a certain amount of time
    protected void scheduleInputReset() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (inputActive) {
                    inputActive = false;
                    System.out.println("\n\n[Input signals have reset to 0]");
                    if (continueProcessReset) {
                        processInputReset();
                    } else {
                        System.out.println("Flip Flop type does not need input reset.");
                    }
                }
                timer.cancel();
            }
        }, 3000); // Reset after 3 seconds
    }

    // obtains valid input from each FlipFlop
    // for all FlipFlops, all valid inputs are 0 or 1
    // anything that is not 0 or 1 is showcased as an error
    protected int getIntInput(String prompt, int min, int max) {
        int input;
        while (true) {
            try {
                System.out.print(prompt);
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Error: Input must be between " + min + " and " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer");
            }
        }
    }

    // table format for displaying results
    protected String createHorizontalLine(int... widths) {
        StringBuilder line = new StringBuilder("+"); 
        for (int width : widths) {
            line.append("-".repeat(width+2)).append("+");
        }
        return line.toString();
    }

    protected void displayTableRow(String... columns) {
        StringBuilder row = new StringBuilder("|");
        for (String column : columns) {
            row.append(" ").append(String.format("%-20s", column)).append(" |");
        }
        System.out.println(row.toString());
    }
    
    protected void displayTableHeader(String... headers) {
        int[] widths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            widths[i] = 20; 
        }
        
        String line = createHorizontalLine(widths);
        System.out.println(line);
        displayTableRow(headers);
        System.out.println(line);
    }

    protected void displayTableFooter(int columns) {
        int[] widths = new int[columns];
        for (int i = 0; i < columns; i++) {
            widths[i] = 20;
        }
        System.out.println(createHorizontalLine(widths));
    }
}