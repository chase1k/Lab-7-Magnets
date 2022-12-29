package test;

import backtracking.Configuration;
import magnets.MagnetsConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A Junit 5 unit test for the In-Lab activity.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestMagnetsInLab {
    /** Test loading a puzzle file and fully storing all the data. */
    @Test
    @Order(1)
    public void testLoad() {
        try {
            // load magnets-8.txt puzzle
            MagnetsConfig config = new MagnetsConfig("data/magnets-8.txt");

            // check dimensions
            assertEquals(4, config.getRows());
            assertEquals(3, config.getCols());

            // check cursor - initially at (0, -1)
            assertEquals(0, config.getCursorRow());
            assertEquals(-1, config.getCursorCol());

            // check pos/neg row/col counts
            final int[] POS_ROW_COUNTS = {2, -1, 1, 1};
            for (int row = 0; row < config.getRows(); ++row) {
                assertEquals(POS_ROW_COUNTS[row], config.getPosRowCount(row));
            }
            final int[] POS_COL_COUNTS = {1, 1, 2};
            for (int col = 0; col < config.getCols(); ++col) {
                assertEquals(POS_COL_COUNTS[col], config.getPosColCount(col));
            }
            final int[] NEG_ROW_COUNTS = {1, 1, 1, 1};
            for (int row = 0; row < config.getRows(); ++row) {
                assertEquals(NEG_ROW_COUNTS[row], config.getNegRowCount(row));
            }
            final int[] NEG_COL_COUNTS = {-1, 2, 2};
            for (int col = 0; col < config.getCols(); ++col) {
                assertEquals(NEG_COL_COUNTS[col], config.getNegColCount(col));
            }

            // check pairings
            final char[][] PAIRS= {
                    {'L', 'R', 'T'},
                    {'L', 'R', 'B'},
                    {'T', 'L', 'R'},
                    {'B', 'L', 'R'}
            };
            for (int row=0; row<config.getRows(); ++row) {
                for (int col=0; col<config.getCols(); ++col) {
                    assertEquals(PAIRS[row][col], config.getPair(row, col));
                }
            }

            // check toString
            final String expected =
                    "+ 1 1 2" + System.lineSeparator() +
                            "  -----" + System.lineSeparator() +
                            "2|. . .|1" + System.lineSeparator() +
                            " |. . .|1" + System.lineSeparator() +
                            "1|. . .|1" + System.lineSeparator() +
                            "1|. . .|1" + System.lineSeparator() +
                            "  -----" + System.lineSeparator() +
                            "    2 2  -" + System.lineSeparator();
            System.out.println(config);
            assertEquals(expected, config.toString());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    /** Test successors for magnets-1.txt */
    @Test
    @Order(2)
    public void testSuccessors1() {
        try {
            MagnetsConfig config = new MagnetsConfig("data/magnets-1.txt");

            // GENERATE ALL SUCCESSORS
            List<Configuration> successors = config.getSuccessors();
            List<Configuration> posSuccessors = successors.get(0).getSuccessors();
            List<Configuration> negSuccessors = successors.get(1).getSuccessors();
            List<Configuration> xSuccessors = successors.get(2).getSuccessors();

            // VERIFY successors in (0,0)
            assertEquals(3, successors.size());

            // VERIFY + at (0,0)
            MagnetsConfig mc = (MagnetsConfig) successors.get(0);
            assertEquals(0, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('+', mc.getVal(0, 0));
            assertEquals('.', mc.getVal(0, 1));

            // VERIFY - at (0,0)
            mc = (MagnetsConfig) successors.get(1);
            assertEquals(0, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('-', mc.getVal(0, 0));
            assertEquals('.', mc.getVal(0, 1));

            // VERIFY X at (0,0)
            mc = (MagnetsConfig) successors.get(2);
            assertEquals(0, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('X', mc.getVal(0, 0));
            assertEquals('.', mc.getVal(0, 1));

            // VERIFY successors in (0, 1)
            assertEquals(3, posSuccessors.size());

            // VERIFY + at (0,0), + at (0,1)
            mc = (MagnetsConfig) posSuccessors.get(0);
            assertEquals(0, mc.getCursorRow());
            assertEquals(1, mc.getCursorCol());
            assertEquals('+', mc.getVal(0, 0));
            assertEquals('+', mc.getVal(0, 1));

            // VERIFY + at (0,0), - at (0,1)
            mc = (MagnetsConfig) posSuccessors.get(1);
            assertEquals(0, mc.getCursorRow());
            assertEquals(1, mc.getCursorCol());
            assertEquals('+', mc.getVal(0, 0));
            assertEquals('-', mc.getVal(0, 1));

            // VERIFY + at (0,0), X at (0,1)
            mc = (MagnetsConfig) posSuccessors.get(2);
            assertEquals(0, mc.getCursorRow());
            assertEquals(1, mc.getCursorCol());
            assertEquals('+', mc.getVal(0, 0));
            assertEquals('X', mc.getVal(0, 1));

            // VERIFY - at (0,0), + at (0,1)
            mc = (MagnetsConfig) negSuccessors.get(0);
            assertEquals(0, mc.getCursorRow());
            assertEquals(1, mc.getCursorCol());
            assertEquals('-', mc.getVal(0, 0));
            assertEquals('+', mc.getVal(0, 1));

            // VERIFY - at (0,0), - at (0,1)
            mc = (MagnetsConfig) negSuccessors.get(1);
            assertEquals(0, mc.getCursorRow());
            assertEquals(1, mc.getCursorCol());
            assertEquals('-', mc.getVal(0, 0));
            assertEquals('-', mc.getVal(0, 1));

            // VERIFY - at (0,0), X at (0,1)
            mc = (MagnetsConfig) negSuccessors.get(2);
            assertEquals(0, mc.getCursorRow());
            assertEquals(1, mc.getCursorCol());
            assertEquals('-', mc.getVal(0, 0));
            assertEquals('X', mc.getVal(0, 1));

            // VERIFY X at (0,0), + at (0,1)
            mc = (MagnetsConfig) xSuccessors.get(0);
            assertEquals(0, mc.getCursorRow());
            assertEquals(1, mc.getCursorCol());
            assertEquals('X', mc.getVal(0, 0));
            assertEquals('+', mc.getVal(0, 1));

            // VERIFY X at (0,0), - at (0,1)
            mc = (MagnetsConfig) xSuccessors.get(1);
            assertEquals(0, mc.getCursorRow());
            assertEquals(1, mc.getCursorCol());
            assertEquals('X', mc.getVal(0, 0));
            assertEquals('-', mc.getVal(0, 1));

            // VERIFY X at (0,0), X at (0,1)
            mc = (MagnetsConfig) xSuccessors.get(2);
            assertEquals(0, mc.getCursorRow());
            assertEquals(1, mc.getCursorCol());
            assertEquals('X', mc.getVal(0, 0));
            assertEquals('X', mc.getVal(0, 1));

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    /** Test successors for magnets-2.txt */
    @Test
    @Order(2)
    public void testSuccessors2() {
        try {
            MagnetsConfig config = new MagnetsConfig("data/magnets-2.txt");

            // GENERATE ALL SUCCESSORS
            List<Configuration> successors = config.getSuccessors();
            List<Configuration> posSuccessors = successors.get(0).getSuccessors();
            List<Configuration> negSuccessors = successors.get(1).getSuccessors();
            List<Configuration> xSuccessors = successors.get(2).getSuccessors();

            // VERIFY successors in (0,0)
            assertEquals(3, successors.size());

            // VERIFY + at (0,0)
            MagnetsConfig mc = (MagnetsConfig) successors.get(0);
            assertEquals(0, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('+', mc.getVal(0, 0));
            assertEquals('.', mc.getVal(1, 0));

            // VERIFY - at (0,0)
            mc = (MagnetsConfig) successors.get(1);
            assertEquals(0, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('-', mc.getVal(0, 0));
            assertEquals('.', mc.getVal(1, 0));

            // VERIFY X at (0,0)
            mc = (MagnetsConfig) successors.get(2);
            assertEquals(0, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('X', mc.getVal(0, 0));
            assertEquals('.', mc.getVal(1, 0));

            // VERIFY successors in (1, 0)
            assertEquals(3, posSuccessors.size());

            // VERIFY + at (0,0), + at (1,0)
            mc = (MagnetsConfig) posSuccessors.get(0);
            assertEquals(1, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('+', mc.getVal(0, 0));
            assertEquals('+', mc.getVal(1, 0));

            // VERIFY + at (0,0), - at  (1,0)
            mc = (MagnetsConfig) posSuccessors.get(1);
            assertEquals(1, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('+', mc.getVal(0, 0));
            assertEquals('-', mc.getVal(1, 0));

            // VERIFY + at (0,0), X at  (1,0)
            mc = (MagnetsConfig) posSuccessors.get(2);
            assertEquals(1, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('+', mc.getVal(0, 0));
            assertEquals('X', mc.getVal(1, 0));

            // VERIFY - at (0,0), + at  (1,0)
            mc = (MagnetsConfig) negSuccessors.get(0);
            assertEquals(1, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('-', mc.getVal(0, 0));
            assertEquals('+', mc.getVal(1, 0));

            // VERIFY - at (0,0), - at  (1,0)
            mc = (MagnetsConfig) negSuccessors.get(1);
            assertEquals(1, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('-', mc.getVal(0, 0));
            assertEquals('-', mc.getVal(1, 0));

            // VERIFY - at (0,0), X at  (1,0)
            mc = (MagnetsConfig) negSuccessors.get(2);
            assertEquals(1, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('-', mc.getVal(0, 0));
            assertEquals('X', mc.getVal(1, 0));

            // VERIFY X at (0,0), + at  (1,0)
            mc = (MagnetsConfig) xSuccessors.get(0);
            assertEquals(1, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('X', mc.getVal(0, 0));
            assertEquals('+', mc.getVal(1, 0));

            // VERIFY X at (0,0), - at  (1,0)
            mc = (MagnetsConfig) xSuccessors.get(1);
            assertEquals(1, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('X', mc.getVal(0, 0));
            assertEquals('-', mc.getVal(1, 0));

            // VERIFY X at (0,0), X at  (1,0)
            mc = (MagnetsConfig) xSuccessors.get(2);
            assertEquals(1, mc.getCursorRow());
            assertEquals(0, mc.getCursorCol());
            assertEquals('X', mc.getVal(0, 0));
            assertEquals('X', mc.getVal(1, 0));

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }
}
