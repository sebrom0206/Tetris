package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;

public class TetrisBoard extends Grid {

    /**
     * Creates a TetrisBoard with given rows and cols
     * 
     * @param rows in Tetrisboard
     * @param cols in Tetrisboard
     */
    public TetrisBoard(int rows, int cols) {
        super(rows, cols, '-');

    }

    /**
     * Returns the TetrisBoard shown as a string
     * 
     * @return TetrisBoard as a String
     */
    public String prettyString() {
        String prettyString = "";
        for (int row = 0; row < rows(); row++) {
            for (int col = 0; col < cols(); col++) {
                prettyString += get(new CellPosition(row, col));

            }
            if (row < rows() - 1) {
                prettyString += "\n";
            }
        }
        return prettyString;
    }

    /**
     * iterates over all cells and changes the symbol to -, black
     */
    public void clearBoard() {
        for (int row = 0; row < rows(); row++) {
            for (int col = 0; col < cols(); col++) {
                set(new CellPosition(row, col), '-');
            }
        }
    }

    /**
     * Iterates over rows and removes if the row is filled. Adds amount of removed
     * rows to count
     * 
     * @return count of rows removed
     */
    public int clearFilledRows() {
        int count = 0;
        for (int r = 0; r < rows(); r++) {
            // for (int r = rows() - 1; r >= 0; r--) {
            if (isRowFilled(r)) {
                removeRow(r);
                count++;
            }
        }
        return count;
    }

    private boolean isRowFilled(int row) {
        for (int c = 0; c < cols(); c++) {
            CellPosition pos = new CellPosition(row, c);
            if (get(pos) == '-') {
                return false;
            }
        }
        return true;

    }

    private void removeRow(int row) {
        for (int r = row; r > 0; r--) {
            copyRowTo(r - 1, r);

        }
        fillTopRowEmpty(0);
    }

    private void copyRowTo(int originalRow, int targetRow) {
        for (int c = 0; c < cols(); c++) {
            CellPosition originalPos = new CellPosition(originalRow, c);
            Character symbol = get(originalPos);

            CellPosition targetPos = new CellPosition(targetRow, c);
            set(targetPos, symbol);
        }
    }

    private void fillTopRowEmpty(int row) {
        for (int c = 0; c < cols(); c++) {
            CellPosition pos = new CellPosition(row, c);
            set(pos, '-');
        }
    }
}
