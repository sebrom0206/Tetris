package no.uib.inf101.tetris.model.tetromino;

import java.util.Random;

public class RandomTetrominoFactory implements TetrominoFactory {

    @Override
    public Tetromino getNext() {
        String shapes = "LJSZTIO";
        Random random = new Random();

        int random_num = random.nextInt(shapes.length());
        char random_shape = shapes.charAt(random_num);

        return Tetromino.newTetromino(random_shape);

    }

}
