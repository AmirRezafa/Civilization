package view;

import java.awt.*;

public class Hex {
    private static final Color FOG_COLOR = new Color(30, 30, 30, 200);
    private static final Color UNEXPLORED_COLOR = Color.BLACK;

    static void show(double centerX, double centerY, int radius, Graphics2D g2, Color terrainColor, boolean isVisible, boolean wasExplored) {
        double drawRadius = radius * 0.90;

        int[] x = new int[6];
        int[] y = new int[6];

        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(i * 60);
            x[i] = (int)(centerX + (drawRadius * Math.cos(angle)));
            y[i] = (int)(centerY + (drawRadius * Math.sin(angle)));
        }

        Color finalColor;
        if (!wasExplored) {
            finalColor = UNEXPLORED_COLOR;
        } else if (!isVisible) {
            finalColor = FOG_COLOR;
        } else {
            finalColor = terrainColor;
        }

        g2.setColor(finalColor);
        g2.fillPolygon(x, y, 6);

        if (wasExplored) {
            g2.setColor(Color.BLACK);
            g2.drawPolygon(x, y, 6);
        }
    }
}