package no.uib.inf101.tetris.model.tetromino;

public interface TetrominoFactory {

    /**
     * Selects a random shape of tetromino and returns it
     * 
     * @return a new tetromino object with a random shape
     */
    public Tetromino getNext();
}
