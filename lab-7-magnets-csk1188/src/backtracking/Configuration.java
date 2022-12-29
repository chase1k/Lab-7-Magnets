package backtracking;

import java.util.List;

/**
 * The representation of a single configuration for a puzzle.
 * The Backtracker depends on these routines in order to
 * solve a puzzle.  Therefore, all puzzles must implement this
 * interface.
 *
 * @author RIT CS
 */
public interface Configuration {
    /**
     * Get the collection of successors from the current one.
     *
     * @return All successors, valid and invalid
     */
    List<Configuration> getSuccessors();

    /**
     * Is the current configuration valid or not?
     *
     * @return true if valid; false otherwise
     */
    boolean isValid();

    /**
     * Is the current configuration a goal?
     *
     * @return true if goal; false otherwise
     */
    boolean isGoal();
}
