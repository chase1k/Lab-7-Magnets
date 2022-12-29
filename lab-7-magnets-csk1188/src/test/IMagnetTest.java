package test;

/**
 * An interface to the magnets puzzle representation that is used by the JUnit test
 * and Magnet's toString().
 */
public interface IMagnetTest {
    /**
     * Get the number of rows in the puzzle.
     * @return number of rows
     */
    int getRows();

    /**
     * Get the number of columns in the puzzle.
     * @return number of columns
     */
    int getCols();

    /**
     * Get the positive row count value for a particular row.
     * @param row the row
     * @return the val count
     */
    int getPosRowCount(int row);

    /**
     * Get the positive column count value for a particular column.
     * @param col the the column
     * @return the val count
     */
    int getPosColCount(int col);

    /**
     * Get the negative row count value for a particular row.
     * @param row the row
     * @return the val count
     */
    int getNegRowCount(int row);

    /**
     * Get the negative column count value for a particular column.
     * @param col the column
     * @return the val count
     */
    int getNegColCount(int col);

    /**
     * Get the value for a pairing at (row, col).
     * @param row the row
     * @param col the column
     * @return the val
     */
    char getPair(int row, int col);

    /**
     * Get the stored value at (row, col) for a particular configuration.
     * @param row the row
     * @param col the column
     * @return the val
     */
    char getVal(int row, int col);

    /**
     * Get the cursor row location.
     * @return cursor row
     */
    int getCursorRow();

    /**
     * Get the cursor column location.
     * @return cursor column
     */
    int getCursorCol();
}
