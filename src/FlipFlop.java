import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

// abstract class for all FlipFlops
public abstract class FlipFlop {
    protected int previousOutput = 0; // default value for a flip flop
    protected boolean inputActive = false; // initial value for input
    protected String type;
    protected Scanner scanner;

    // constructor class to obtain type of FlipFlop and scanner
    public FlipFlop(String type, Scanner scanner) {
        this.type = type;
        this.scanner = scanner;
    }

    // get and set functions
    public int getOutput() {
        return previousOutput;
    }

    public void setOutput(int output) {
        this.previousOutput = output;
    }

    // function is overridden for each FlipFlop class type that extends this class
    public abstract void simulate();

    // resets input signals to 0 after a certain amount of time
    protected void scheduleInputReset() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (inputActive) {
                    inputActive = false;
                    System.out.println("\n[Input signals have reset to 0]");
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
}