package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.RandomTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel {

    private TetrisBoard tetrisBoard;
    private TetrominoFactory tetrominoFactory;
    private Tetromino fallingTetromino;
    private GameState gameState;
    private int score;
    private int timerDelay = 1000;
    private int totRowsCleared = 0;

    /**
     * constructs a TetrisModel with a random tetromino at the top center of board
     * with size (15, 10)
     * 
     * @param tetrominoFactory
     */
    public TetrisModel(TetrominoFactory tetrominoFactory) {
        tetrisBoard = new TetrisBoard(15, 10);
        this.tetrominoFactory = new RandomTetrominoFactory();
        // this.gameState = GameState.ACTIVE_GAME;
        this.gameState = GameState.WELCOME_SCREEN;
        this.score = 0;

        fallingTetromino = this.tetrominoFactory.getNext().shiftedToTopCenterOf(tetrisBoard);
    }

    @Override
    public Tetromino getShadow() {
        Tetromino shadow = fallingTetromino;
        while (checkLegalPosition(shadow.shiftedBy(1, 0))) {
            shadow = shadow.shiftedBy(1, 0);
        }
        return shadow;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void startGame() {
        gameState = GameState.ACTIVE_GAME;
    }

    @Override
    public void getWelcomeScreen() {
        gameState = GameState.WELCOME_SCREEN;
    }

    @Override
    public void resetGame() {
        tetrisBoard.clearBoard();
        score = 0;
        getWelcomeScreen();
    }

    @Override
    public GridDimension getDimension() {
        return new Grid(tetrisBoard.rows(), tetrisBoard.cols());
        // return tetrisBoard;
    }

    @Override
    public Iterable<GridCell> getTilesOnBoard() {
        return tetrisBoard;
    }

    @Override
    public Iterable<GridCell> getFallingTetromino() {
        return fallingTetromino;
    }

    @Override
    public boolean moveTetromino(int deltaRow, int deltaCol) {
        Tetromino tetromino = fallingTetromino.shiftedBy(deltaRow, deltaCol);
        if (checkLegalPosition(tetromino)) {
            fallingTetromino = tetromino;
            return true;
        }
        return false;
    }

    @Override
    public boolean rotateTetromino() {
        Tetromino tetromino = fallingTetromino.rotateMe();
        if (checkLegalPosition(tetromino)) {
            fallingTetromino = tetromino;
            return true;
        }
        return false;

    }

    /**
     * checks if the tetromino is inside the grid and not on top of
     * another symbol
     * 
     * @param tetromino
     * @return true if the is in a legal position, false otherwise
     */
    private boolean checkLegalPosition(Tetromino tetromino) {
        for (GridCell cell : tetromino) {
            CellPosition pos = cell.pos();

            if (!tetrisBoard.positionIsOnGrid(pos)) {
                return false;
            }
            Character symbol = tetrisBoard.get(pos);
            if (symbol != null && symbol != '-') {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean dropTetromino() {

        if (!checkLegalPosition(fallingTetromino)) {
            return false;
        }
        while (moveTetromino(1, 0)) {

        }
        freezeTetromino();
        getNewTetromino();
        return true;

    }

    /**
     * Iterates over the cells of fallingTetromino and stores the symbol of cell at
     * given position
     */
    private void freezeTetromino() {
        for (GridCell cell : fallingTetromino) {
            tetrisBoard.set(cell.pos(), cell.symbol());
        }
        int removedRowes = tetrisBoard.clearFilledRows();

        if (removedRowes == 1) {
            score += 100;
        } else if (removedRowes == 2) {
            score += 300;
        } else if (removedRowes == 3) {
            score += 500;
        } else if (removedRowes == 4) {
            score += 800;
        }
        totRowsCleared += removedRowes;

        if (totRowsCleared >= 5) {
            timerDelay = 750;
        } else if (totRowsCleared >= 10) {
            timerDelay = 500;
        } else if (totRowsCleared >= 15) {
            timerDelay = 250;
        } else if (totRowsCleared >= 20) {
            timerDelay = 100;
        }

    }

    /**
     * If the newfallingTetromino has a illegal position game stops, otherwise
     * creates a new random tetromino at top and center of grid and updates
     * FallingTetromino
     */
    private void getNewTetromino() {
        Tetromino newFallTetromino = tetrominoFactory.getNext().shiftedToTopCenterOf(tetrisBoard);
        if (!checkLegalPosition(newFallTetromino)) {
            gameState = GameState.GAME_OVER;
            return;
        }
        fallingTetromino = newFallTetromino;

    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public int getTimerDelay() {
        return timerDelay;
    }

    @Override
    public boolean clockTick() {
        if (moveTetromino(1, 0)) {
            return true;
        } else {
            freezeTetromino();
            getNewTetromino();
            return false;
        }
    }
}
