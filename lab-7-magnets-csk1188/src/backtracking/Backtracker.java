package backtracking;

import java.util.Collection;
import java.util.Optional;

/**
 * This class represents the classic recursive backtracking algorithm.
 * It has a solver that can take a valid configuration and return a
 * solution, if one exists.
 *
 * @author RIT CS
 */
public class Backtracker {
    /** Should debug output be enabled? */
    private boolean debug;
    /** counts number of configurations generated */
    private int configCount;

    /**
     * Initialize a new backtracker.
     * @param debug Is debugging output enabled?
     */
    public Backtracker(boolean debug) {
        this.debug = debug;
        if (this.debug) {
            System.out.println("Backtracker debugging enabled...");
        }
        this.configCount = 1;  // counts the initial config sent to solve()
    }

    /**
     * A utility routine for printing out various debug messages.
     *
     * @param msg The type of config being looked at (current, goal,
     *  successor, e.g.)
     * @param config The config to display
     */
    private void debugPrint(String msg, Configuration config) {
        if (this.debug) {
            System.out.println(msg + ": " + "\n" + config);
        }
    }

    /**
     * Try find a solution, if one exists, for a given configuration.
     *
     * @param config A valid configuration
     * @return A solution config, or null if no solution
     */
    public Optional<Configuration> solve(Configuration config) {
        debugPrint("Current config", config);
        if (config.isGoal()) {
            return Optional.of(config);
        } else {
            Collection<Configuration> successors = config.getSuccessors();
            configCount += successors.size();
            for (Configuration child : successors) {
                if (child.isValid()) {
                    debugPrint("Valid successor", child);
                    Optional<Configuration> sol = solve(child);
                    if (sol.isPresent()) {
                        return sol;
                    }
                } else {
                    debugPrint("\tInvalid successor", child);
                }
            }
            // implicit backtracking happens here
        }
        return Optional.empty();
    }

    /**
     * Get the number of configurations processed during backtracking.
     *
     * @return config count
     */
    public int getConfigCount() {
        return this.configCount;
    }
}
