package view;

import java.awt.*;

public class Hex {
    static void show(double centerX, double centerY, int radius, Graphics2D g2, Color color) {
        double drawRadius = radius * 0.90;

        int[] x = new int[6];
        int[] y = new int[6];

        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(i * 60);
            x[i] = (int)(centerX + (drawRadius * Math.cos(angle)));
            y[i] = (int)(centerY + (drawRadius * Math.sin(angle)));
        }

        g2.setColor(color);
        g2.fillPolygon(x, y, 6);

        g2.setColor(Color.BLACK);
        g2.drawPolygon(x, y, 6);
    }
}