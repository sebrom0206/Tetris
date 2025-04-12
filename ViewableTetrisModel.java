package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.model.tetromino.Tetromino;

public interface ViewableTetrisModel {

    /**
     * The dimensions of the board, i.e. number of rows and columns
     *
     * @return an object of type GridDimension
     */
    GridDimension getDimension();

    /**
     * An object that when iterated over returns all positions
     * and corresponding values
     *
     * @return an iterable object
     */
    Iterable<GridCell> getTilesOnBoard();

    /**
     * An object when iterated over returns all cells of falling tetromino
     * 
     * @return an iterable object
     */
    Iterable<GridCell> getFallingTetromino();

    /**
     * Gets the current game state of game
     * 
     * @return a gameState
     */
    public GameState getGameState();

    /**
     * Gets the current score
     */
    public int getScore();

    /**
     * gets a copy/shadow of a tetromino at the lowest legal position
     * 
     * @return a copy of tetromino as "shadow"
     */
    public Tetromino getShadow();
}
