package no.uib.inf101.tetris.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;

public class NextTetrominoView extends JPanel {

    public static final int OUTERMARGIN = 10;
    public static final int CELLMARGIN = 2;
    public static final int PREFERREDSIDESIZE = 150;

    private ViewableTetrisModel viewableTetrisModel;
    private ColorTheme colorTheme;

    public NextTetrominoView(ViewableTetrisModel viewableTetrisModel) {
        this.viewableTetrisModel = viewableTetrisModel;
        this.colorTheme = new ColorTheme();

        this.setBackground(colorTheme.getBackgroundColor());

        this.setFocusable(true);
        this.setPreferredSize(new Dimension(PREFERREDSIDESIZE, PREFERREDSIDESIZE));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawNewTetrominoView(g2);
    }

    private void drawNewTetrominoView(Graphics2D g2) {

        Rectangle2D box = new Rectangle2D.Double(
                OUTERMARGIN,
                OUTERMARGIN,
                this.getWidth(),
                getHeight());

        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(box,
                viewableTetrisModel.getDimension(), CELLMARGIN);

        drawCells(g2, viewableTetrisModel.getFallingTetromino(), converter, colorTheme);
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
}
