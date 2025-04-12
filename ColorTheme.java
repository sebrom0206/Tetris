package no.uib.inf101.tetris.view;

import java.awt.Color;

public class ColorTheme {

    public Color getCellColor(Character c) {
        Color color = switch (c) {

            case 'r' -> Color.RED;
            case 'b' -> Color.BLUE;
            case 'y' -> Color.YELLOW;
            case 'w' -> Color.WHITE;
            case 'G' -> Color.GRAY;

            // case 'L' -> Color.BLUE;
            // case 'J' -> new Color(192, 0, 255);
            // case 'S' -> Color.GREEN;
            // case 'Z' -> Color.YELLOW;
            // case 'T' -> Color.RED;
            // case 'I' -> Color.ORANGE;
            // case 'O' -> Color.magenta;
            // case '-' -> Color.BLACK;

            // color inspiration https://www.color-hex.com/color-palette/764
            case 'L' -> new Color(178, 235, 242);
            case 'J' -> new Color(174, 198, 207);
            case 'S' -> new Color(255, 218, 185);
            case 'Z' -> new Color(255, 255, 204);
            case 'T' -> new Color(152, 251, 152);
            case 'I' -> new Color(216, 191, 216);
            case 'O' -> new Color(255, 182, 193);
            case '-' -> new Color(50, 50, 50);

            default -> throw new IllegalArgumentException(
                    "No available color for '" + c + "'");
        };
        return color;
    }

    public Color getFrameColor() {
        return Color.GRAY;
    }

    public Color getGridLineColor() {
        return new Color(0, 0, 0, 150);
    }

    public Color getBackgroundColor() {
        return new Color(0, 0, 0, 128);
    }

}
