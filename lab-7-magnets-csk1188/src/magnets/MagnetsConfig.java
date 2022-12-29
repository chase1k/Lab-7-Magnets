package magnets;

import backtracking.Configuration;
import test.IMagnetTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * The representation of a magnet configuration, including the ability
 * to backtrack and also give information to the JUnit tester.
 *
 * This implements a more optimal pruning strategy in isValid():
 * - Pair checked each time a new cell is populated
 * - Polarity checked each time a new cell is populated
 * - When last column or row is populated, the pos/neg counts are checked
 *
 * @author RIT CS
 */
public class MagnetsConfig implements Configuration, IMagnetTest {
    /** a cell that has not been assigned a value yet */
    private final static char EMPTY = '.';
    /** a blank cell */
    private final static char BLANK = 'X';
    /** a positive cell */
    private final static char POS = '+';
    /** a negative cell */
    private final static char NEG = '-';
    /** left pair value */
    private final static char LEFT = 'L';
    /** right pair value */
    private final static char RIGHT = 'R';
    /** top pair value */
    private final static char TOP = 'T';
    /** bottom pair value */
    private final static char BOTTOM = 'B';
    /** and ignored count for pos/neg row/col */
    private final static int IGNORED = -1;
    private static int height;
    private static int width;
    private static int[] left;
    private static int[] right;
    private static int[] top;
    private static int[] bottom;
    private static char[][] pairs;
    private int[] cursor;
    private char[][] board;


    /**
     * Read in the magnet puzzle from the filename.  After reading in, it should display:
     * - the filename
     * - the number of rows and columns
     * - the grid of pairs
     * - the initial config with all empty cells
     *
     * @param filename the name of the file
     * @throws IOException thrown if there is a problem opening or reading the file
     */
    public MagnetsConfig(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            // read first line: rows cols
            String[] fields = in.readLine().split("\\s+");

            height = Integer.parseInt(fields[0]);
            width = Integer.parseInt(fields[1]);

            board = new char[height][width];
            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    board[h][w] = '.';
                }
            }

            String[] LeftSide = in.readLine().split(" ");
            left = new int[LeftSide.length];

            for (int i = 0; i < LeftSide.length; i++) {
                left[i] = Integer.parseInt(LeftSide[i]);
            }

            String[] TopSide = in.readLine().split(" ");
            top = new int[TopSide.length];

            for (int i = 0; i < TopSide.length; i++) {
                top[i] = Integer.parseInt(TopSide[i]);
            }

            String[] RightSide = in.readLine().split(" ");
            right = new int[RightSide.length];

            for (int i = 0; i < RightSide.length; i++) {
                right[i] = Integer.parseInt(RightSide[i]);
            }

            String[] BottomSide = in.readLine().split(" ");
            bottom = new int[BottomSide.length];

            for (int i = 0; i < BottomSide.length; i++) {
                bottom[i] = Integer.parseInt(BottomSide[i]);
            }

            String[] pears;
            pairs = new char[height][width];
            for (int h = 0; h < height; h++) {
                pears = in.readLine().split(" ");
                for (int w = 0; w < width; w++) {
                    pairs[h][w] = pears[w].charAt(0);
                }
            }

            cursor = new int[]{0,-1};

            System.out.println("Rows: "+height+", Columns: "+width+"\n"+"Pairs: ");
            for (int i = 0; i < pairs.length; i++) {
                for (int j = 0; j < pairs[i].length; j++) {
                    System.out.print(pairs[i][j]+" ");
                }
                System.out.println();
            }
            System.out.println("\nInitial config:\n"+this);

        } // <3 Jim
    }

    /**
     * The copy constructor which advances the cursor, creates a new grid,
     * and populates the grid at the cursor location with val
     * @param other the board to copy
     * @param val the value to store at new cursor location
     */
    private MagnetsConfig(MagnetsConfig other, char val) {
        board = new char[height][width];
        for (int r = 0; r < other.board.length; r++) {board[r] = other.board[r].clone();}
        this.cursor = other.cursor.clone();
        board[this.getCursorRow()][this.getCursorCol()] = val;
    }


    /**
     * Generate the successor configs.  For minimal pruning, this should be
     * done in the order: +, - and X.
     *
     * @return the collection of successors
     */
    @Override
    public List<Configuration> getSuccessors() {
        List<Configuration> successors = new ArrayList<>();

        int[] newCursor = cursor.clone();

        if(getCursorCol() == width-1){
            cursor = new int[]{getCursorRow()+1,0};
        }else{
            cursor = new int[]{getCursorRow(),getCursorCol()+1};
        }

        successors.add(new MagnetsConfig(this,POS));
        successors.add(new MagnetsConfig(this,NEG));
        successors.add(new MagnetsConfig(this,BLANK));
        cursor = newCursor;

        return successors;
    }


    /**
     * Checks to make sure a successor is valid or not.  For minimal pruning,
     * each newly placed cell at the cursor needs to make sure its pair
     * is valid, and there is no polarity violation.  When the last cell is
     * populated, all row/col pos/negative counts are checked.
     *
     * @return whether this config is valid or not
     */
    @Override
    public boolean isValid() {

        if (getCursorCol() < 0 || getCursorRow() <0){return false;}

        char val = board[getCursorRow()][getCursorCol()];
        char opposite;

        if (pairs[getCursorRow()][getCursorCol()] == LEFT){
            opposite = board[getCursorRow()][getCursorCol()+1];
        } else if (pairs[getCursorRow()][getCursorCol()] == TOP) {
            opposite = board[getCursorRow()+1][getCursorCol()];
        }else if (pairs[getCursorRow()][getCursorCol()] == RIGHT) {
            opposite = board[getCursorRow()][getCursorCol()-1];
        }else /*(pairs[getCursorRow()][getCursorCol()] == BOTTOM)*/{
            opposite = board[getCursorRow()-1][getCursorCol()];
        }

        if (opposite != EMPTY){
            if (val == BLANK) {if (opposite != BLANK) {return false;}}
            if (val == POS) {if (opposite != NEG) {return false;}}
            if (val == NEG) {if (opposite != POS) {return false;}}
        }

        if (getCursorRow() >0) {
            if (val == POS) {
                if (board[getCursorRow() - 1][getCursorCol()] == POS) {return false;}}
            if (val == NEG) {
                if (board[getCursorRow() - 1][getCursorCol()] == NEG) {return false;}}
        }

        if (getCursorCol() >0) {
            if (val == POS) {
                if (board[getCursorRow()][getCursorCol()-1] == POS) {return false;}}
            if (val == NEG) {
                if (board[getCursorRow()][getCursorCol()-1] == NEG) {return false;}}
        }

        int totalCount = 0;

        if(getCursorRow() == height-1){
            for (int i = 0; i < height; i++) {
                val = board[i][getCursorCol()];
                if(val == BLANK){continue;}
                if (val == NEG) {totalCount++;}
            }
                if (bottom[getCursorCol()] != IGNORED) {
                    if (totalCount != bottom[getCursorCol()]) {return false;}}
            totalCount = 0;

            for (int i = 0; i < height; i++) {
                val = board[i][getCursorCol()];
                if(val == BLANK){continue;}
                if (val == POS){totalCount++;}
            }
                if (top[getCursorCol()] != IGNORED) {
                    if (totalCount != top[getCursorCol()]) {return false;}}
        }
        if (getCursorCol() == width-1){
            totalCount = 0;
            for (int i = 0; i < width; i++) {
                val = board[getCursorRow()][i];
                if(val == BLANK){continue;}
                if (val == NEG) {totalCount++;}
            }
                if (right[getCursorRow()] != IGNORED) {
                    if (totalCount != right[getCursorRow()]) {return false;}}
            totalCount = 0;
            for (int i = 0; i < width; i++) {
                val = board[getCursorRow()][i];
                if(val == BLANK){continue;}
                if (val == POS){totalCount++;}
            }
                if (left[getCursorRow()] != IGNORED) {
                    if (totalCount != left[getCursorRow()]) {return false;}}
        }
        return true;
    }

    @Override
    public boolean isGoal() {
        if (getCursorRow() == height-1 && getCursorCol() == width-1) {
            return true;
        }
        return false;
    }

    /**
     * Returns a string representation of the puzzle including all necessary info.
     *
     * @return the string
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        // top row
        result.append("+ ");
        for (int col = 0; col < getCols(); ++col) {
            result.append(getPosColCount(col) != IGNORED ? getPosColCount(col) : " ");
            if (col < getCols() - 1) {
                result.append(" ");
            }
        }
        result.append(System.lineSeparator());
        result.append("  ");
        for (int col = 0; col < getCols(); ++col) {
            if (col != getCols() - 1) {
                result.append("--");
            } else {
                result.append("-");
            }
        }
        result.append(System.lineSeparator());

        // middle rows
        for (int row = 0; row < getRows(); ++row) {
            result.append(getPosRowCount(row) != IGNORED ? getPosRowCount(row) : " ").append("|");
            for (int col = 0; col < getCols(); ++col) {
                result.append(getVal(row, col));
                if (col < getCols() - 1) {
                    result.append(" ");
                }
            }
            result.append("|").append(getNegRowCount(row) != IGNORED ? getNegRowCount(row) : " ");
            result.append(System.lineSeparator());
        }

        // bottom row
        result.append("  ");
        for (int col = 0; col < getCols(); ++col) {
            if (col != getCols() - 1) {
                result.append("--");
            } else {
                result.append("-");
            }
        }
        result.append(System.lineSeparator());

        result.append("  ");
        for (int col = 0; col < getCols(); ++col) {
            result.append(getNegColCount(col) != IGNORED ? getNegColCount(col) : " ").append(" ");
        }
        result.append(" -").append(System.lineSeparator());
        return result.toString();
    }

    // IMagnetTest

    @Override
    public int getRows() {
        return height;
    }

    @Override
    public int getCols() {
        return width;
    }

    @Override
    public int getPosRowCount(int row) {
        return left[row];
    }

    @Override
    public int getPosColCount(int col) {
        return top[col];
    }

    @Override
    public int getNegRowCount(int row) {
        return right[row];
    }

    @Override
    public int getNegColCount(int col) {
        return bottom[col];
    }

    @Override
    public char getPair(int row, int col) {
        return pairs[row][col];
    }

    @Override
    public char getVal(int row, int col) {
        return board[row][col];    }

    @Override
    public int getCursorRow() {
        return cursor[0];
    }

    @Override
    public int getCursorCol() {
        return cursor[1];
    }
}
