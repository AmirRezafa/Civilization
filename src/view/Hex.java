package view;

import java.awt.*;

public class Hex{
    static void show(double centerX, double centerY, int radius, Graphics2D g2){
        int[] x = new int[6];
        int[] y = new int[6];

        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(i * 60);
            x[i] = (int)(centerX + (radius * Math.cos(angle)));
            y[i] = (int)(centerY + (radius * Math.sin(angle)));
        }
        g2.drawPolygon(x, y, 6);
    }
}
