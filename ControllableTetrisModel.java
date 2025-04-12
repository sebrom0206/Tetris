package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

public interface ControllableTetrisModel {

    /**
     * Moves the tetromino around the board if the move is to a legal position
     * 
     * @param deltaRow
     * @param deltaCol
     * @return true if the moving where completed, false otherwise
     */
    public boolean moveTetromino(int deltaRow, int deltaCol);

    /**
     * Rotates the tetromino if the rotated tetromino is in a legal position
     * 
     * @return true if the rotation where completed, false otherwise
     */
    public boolean rotateTetromino();

    /**
     * Drops tetromino to downwards until last legal position, and freezes the
     * tetromino
     * 
     * @return true if the drop where completed, false otherwise
     */
    public boolean dropTetromino();

    /**
     * Gets the gamestate
     * 
     * @return GameState
     */
    public GameState getGameState();

    /**
     * Gets a delay for the timer, in milliseconds
     * 
     * @return milliseconds as integer
     */
    public int getTimerDelay();

    /**
     * checks if tetromino can move downwards and moves if true. If not the
     * tetromino
     * freezes and a new tetromino is created at the top
     * 
     * @return true if tetromino has a legal straight-downwards move, false
     *         otherwise
     */
    public boolean clockTick();

    /**
     * sets the gameState to ACTIVE_GAME
     */
    public void startGame();

    /**
     * sets the gameState to WELCOME_SCREEN
     */
    public void getWelcomeScreen();

    /**
     * resets/repaints the game
     */
    public void resetGame();

}
