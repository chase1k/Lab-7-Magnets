package magnets;

import backtracking.Backtracker;
import backtracking.Configuration;

import java.io.IOException;
import java.util.Optional;

/**
 * The main program for the magnets puzzle solver.  The intention is to run
 * this on the command line with the name of the puzzle file, and whether
 * you want debug printing enabled or not.
 *
 * @author RIT CS
 */
public class Magnets {
    /**
     * Run the main program.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Magnets filename debug");
        } else {
            try {
                MagnetsConfig config = new MagnetsConfig(args[0]);

                // create the backtracker with the debug flag
                boolean debug = args[1].equals("true");
                Backtracker bt = new Backtracker(debug);

                // start the clock
                double start = System.currentTimeMillis();

                // attempt to solve the puzzle
                Optional<Configuration> sol = bt.solve(config);

                // compute the elapsed time
                double elapsed =  (System.currentTimeMillis() - start) / 1000.0;

                // display the solution, if one exists
                if (sol.isPresent()) {
                    System.out.println("Solution:\n" + sol.get());
                } else {
                    System.out.println("No solution exists!");
                }

                System.out.println("Elapsed time: " + elapsed + " seconds.") ;
                System.out.println(bt.getConfigCount() + " configurations generated.");
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}
