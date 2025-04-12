package no.uib.inf101.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

public class Tetromino implements Iterable<GridCell> {
    char symbol;
    boolean[][] shape;
    CellPosition pos;

    static boolean[][] L_SHAPE = { { false, false, false }, { true, true, true }, { true, false, false } };
    static boolean[][] J_SHAPE = { { false, false, false }, { true, true, true }, { false, false, true } };
    static boolean[][] S_SHAPE = { { false, false, false }, { false, true, true }, { true, true, false } };
    static boolean[][] Z_SHAPE = { { false, false, false }, { true, true, false }, { false, true, true } };
    static boolean[][] T_SHAPE = { { false, false, false }, { true, true, true }, { false, true, false } };
    static boolean[][] I_SHAPE = { { false, false, false, false }, { true, true, true, true },
            { false, false, false, false }, { false, false, false, false } };
    static boolean[][] O_SHAPE = { { false, false, false, false }, { false, true, true, false },
            { false, true, true, false }, { false, false, false, false } };

    private Tetromino(char symbol, boolean[][] shape, CellPosition pos) {
        this.symbol = symbol;
        this.shape = shape;
        this.pos = pos;
    }

    /**
     * Returns a tetromino with the shape of input symbol
     * 
     * @param symbol to determine which tetromino
     * @return a new tetromino object with the shape of input (symbol)
     */
    public static Tetromino newTetromino(char symbol) {
        boolean[][] shape;
        switch (symbol) {
            case 'L':
                shape = L_SHAPE;
                break;
            case 'J':
                shape = J_SHAPE;
                break;
            case 'S':
                shape = S_SHAPE;
                break;
            case 'Z':
                shape = Z_SHAPE;
                break;
            case 'T':
                shape = T_SHAPE;
                break;
            case 'I':
                shape = I_SHAPE;
                break;
            case 'O':
                shape = O_SHAPE;
                break;

            default:
                throw new IllegalArgumentException("Invalid symbol");
        }
        return new Tetromino(symbol, shape, new CellPosition(0, 0));
    }

    /**
     * Shifts the tetromino to a new position
     * 
     * @param deltaRow coordinate row
     * @param deltaCol coordinate column
     * @return a tetromino with position (deltaRow, deltaCol)
     */
    public Tetromino shiftedBy(int deltaRow, int deltaCol) {
        return new Tetromino(symbol, shape, new CellPosition(this.pos.row() + deltaRow, this.pos.col() + deltaCol));
    }

    /**
     * Shifts the tetromino to the top row, and in the center of the grid
     * 
     * @param grid of tetrisboard
     * @return a tetromino object at the top row and center of grid
     */
    public Tetromino shiftedToTopCenterOf(GridDimension grid) {
        return new Tetromino(symbol, shape, new CellPosition(-1, (grid.cols() / 2) - 2));
    }

    @Override
    public Iterator<GridCell> iterator() {
        List<GridCell> gridCells = new ArrayList<GridCell>();

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == true) {
                    CellPosition pos = new CellPosition(this.pos.row() + row, this.pos.col() + col);
                    GridCell cell = new GridCell(pos, symbol);
                    gridCells.add(cell);
                }
            }
        }
        return gridCells.iterator();
    }

    /**
     * inspiration from "https://www.baeldung.com/java-equals-hashcode-contracts"
     * and coursenotes
     * 
     * Checks that a tetromino object is equal to another or itself
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Tetromino)) {
            return false;
        }
        Tetromino other = ((Tetromino) obj);
        return this.symbol == (other.symbol) && Arrays.deepEquals(this.shape, other.shape)
                && this.shape.equals(other.shape) && this.pos.equals(other.pos);
    }

    /**
     * Calculates hash for tetromino
     */
    @Override
    public int hashCode() {
        int hashed_shape_array = Arrays.deepHashCode(shape);
        return Objects.hash(symbol, hashed_shape_array, pos);
    }

    /**
     * 
     * Inspiration from
     * https://www.geeksforgeeks.org/rotate-a-matrix-by-90-degrees/
     * 
     * rotates a tetromino
     * 
     * @return a new tetromino object that is rotated, with the same shape and
     *         symbol
     */
    public Tetromino rotateMe() {
        int rows = shape.length; // row
        int cols = shape[0].length; // both should be 3

        boolean[][] rotated = new boolean[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotated[rows - c - 1][r] = shape[r][c];
            }
        }
        return new Tetromino(symbol, rotated, pos);
    }

}
