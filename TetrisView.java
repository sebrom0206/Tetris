package no.uib.inf101.tetris.view;

import javax.swing.JPanel;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class TetrisView extends JPanel {

    public static final int OUTERMARGIN = 15;
    public static final int CELLMARGIN = 2;
    public static final int PREFERREDSIDESIZE = 40;

    private ViewableTetrisModel viewableTetrisModel;
    private ColorTheme colorTheme;

    public TetrisView(ViewableTetrisModel viewableTetrisModel) {
        this.viewableTetrisModel = viewableTetrisModel;

        this.colorTheme = new ColorTheme();
        this.setBackground(colorTheme.getFrameColor());

        this.setFocusable(true);
        this.setPreferredSize(getDefaultSize(viewableTetrisModel.getDimension()));

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);

        // g2.setColor(Color.BLACK);
        // g2.drawString("SCORE: " + viewableTetrisModel.getScore(), 0, 13);
    }

    private void drawGame(Graphics2D g2) {

        Rectangle2D box = new Rectangle2D.Double(
                OUTERMARGIN,
                OUTERMARGIN,
                this.getWidth() - OUTERMARGIN * 2,
                this.getHeight() - OUTERMARGIN * 2);

        GameState gameState = viewableTetrisModel.getGameState();

        if (gameState == GameState.WELCOME_SCREEN) {

            g2.setColor(colorTheme.getFrameColor());
            g2.fill(box);
            g2.setColor(colorTheme.getCellColor('w'));
            Inf101Graphics.drawCenteredString(g2, "SPACE TO START", box);

        } else if (gameState == GameState.GAME_OVER) {
            g2.setColor(colorTheme.getGridLineColor());
            g2.fill(box);

            CellPositionToPixelConverter converter = new CellPositionToPixelConverter(box,
                    viewableTetrisModel.getDimension(), CELLMARGIN);

            drawCells(g2, viewableTetrisModel.getTilesOnBoard(), converter, colorTheme);

            drawCells(g2, viewableTetrisModel.getFallingTetromino(), converter, colorTheme);

            g2.setColor(colorTheme.getBackgroundColor());
            g2.fill(box);

            g2.setColor(colorTheme.getCellColor('-'));
            g2.drawString("SCORE: " + viewableTetrisModel.getScore(), 0, 13);

            g2.setColor(Color.WHITE);
            // g2.setFont(new Font("Monospaced", Font.BOLD, 40));
            Inf101Graphics.drawCenteredString(g2, "SPACE TO RESTART", box);

        } else {

            g2.setColor(colorTheme.getGridLineColor());
            g2.fill(box);

            g2.setColor(colorTheme.getCellColor('-'));
            g2.drawString("SCORE: " + viewableTetrisModel.getScore(), 0, 13);
            CellPositionToPixelConverter converter = new CellPositionToPixelConverter(box,
                    viewableTetrisModel.getDimension(), CELLMARGIN);

            drawCells(g2, viewableTetrisModel.getTilesOnBoard(), converter, colorTheme);

            drawCells(g2, viewableTetrisModel.getShadow(), converter, Color.GRAY);

            drawCells(g2, viewableTetrisModel.getFallingTetromino(), converter, colorTheme);
        }

    }

    private static void drawCells(Graphics2D g2, Iterable<GridCell> iterable,
            CellPositionToPixelConverter converter, ColorTheme colorTheme) {
        for (GridCell cell : iterable) {
            CellPosition pos = cell.pos();
            Character c = cell.symbol();
            Rectangle2D tile = converter.getBoundsForCell(pos);

            g2.setColor(colorTheme.getCellColor(c));
            g2.fill(tile);
        }
    }

    private static void drawCells(Graphics2D g2, Iterable<GridCell> iterable,
            CellPositionToPixelConverter converter, Color color) {
        for (GridCell cell : iterable) {
            CellPosition pos = cell.pos();
            Rectangle2D tile = converter.getBoundsForCell(pos);
            g2.setColor(color);
            g2.fill(tile);
        }
    }

    private static Dimension getDefaultSize(GridDimension gd) {
        int width = (int) (PREFERREDSIDESIZE * gd.cols() + CELLMARGIN * (gd.cols() + 1) + 2 * OUTERMARGIN);
        int height = (int) (PREFERREDSIDESIZE * gd.rows() + CELLMARGIN * (gd.rows() + 1) + 2 * OUTERMARGIN);
        return new Dimension(width, height);
    }

}
