package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.midi.CustomSong;
import no.uib.inf101.tetris.midi.TetrisSong;

import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.view.NextTetrominoView;
import no.uib.inf101.tetris.view.TetrisView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
// import java.awt.event;

import javax.swing.Timer;

public class TetrisController implements KeyListener {

    ControllableTetrisModel controllableTetrisModel;
    TetrisView tetrisView;
    NextTetrominoView nextTetrominoView;
    Timer timer;
    TetrisSong tetrisSong = new TetrisSong();
    CustomSong customSong = new CustomSong();

    /**
     * Constructs a controller
     * starts tetrissong and timer
     * 
     * @param controllableTetrisModel model of the game thats being controlled
     * @param tetrisView              view of board and tetrominos
     */
    public TetrisController(ControllableTetrisModel controllableTetrisModel, TetrisView tetrisView) {
        this.controllableTetrisModel = controllableTetrisModel;
        this.tetrisView = tetrisView;
        this.timer = new Timer(controllableTetrisModel.getTimerDelay(), this::clockTick);

        tetrisView.addKeyListener(this);
        tetrisView.setFocusable(true);

        tetrisSong.run();
        // customSong.run();
        timer.start();
    }

    /**
     * If we are in a ACTIVE_GAME game state, clockTick() is being called every time
     * timer ticks and view/board is repainted
     * 
     * @param e ActionEvent
     */
    private void clockTick(ActionEvent e) {
        if (controllableTetrisModel.getGameState() == GameState.ACTIVE_GAME) {
            controllableTetrisModel.clockTick();
            getDelay();
            tetrisView.repaint();
        }
    }

    /**
     * Sets delay using getTimerDelay()
     */
    private void getDelay() {
        timer.setDelay(controllableTetrisModel.getTimerDelay());
        timer.setInitialDelay(controllableTetrisModel.getTimerDelay());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (controllableTetrisModel.getGameState() == GameState.WELCOME_SCREEN) {

            if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
                controllableTetrisModel.startGame();
                tetrisView.repaint();
                nextTetrominoView.repaint();
            }

        } else if (controllableTetrisModel.getGameState() == GameState.ACTIVE_GAME) {

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                controllableTetrisModel.moveTetromino(0, -1);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                controllableTetrisModel.moveTetromino(0, 1);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                controllableTetrisModel.moveTetromino(1, 0);
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                controllableTetrisModel.rotateTetromino();
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                controllableTetrisModel.dropTetromino();
            }

            tetrisView.repaint();
            nextTetrominoView.repaint();

        } else if ((controllableTetrisModel.getGameState() == GameState.GAME_OVER)) {

            if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
                controllableTetrisModel.resetGame();
                tetrisView.repaint();
                nextTetrominoView.repaint();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
