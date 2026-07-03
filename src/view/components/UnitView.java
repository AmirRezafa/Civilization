package view.components;

import model.Unit;

import java.awt.*;

public class UnitView {
    public static void show(Unit unit, int a, boolean selected, Graphics2D g2){
        double x = unit.getX() * a;
        double y = unit.getY() * a;

        if (selected) {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(3));
            g2.drawOval((int)x - a/2, (int)y - a/2, a, a);
            g2.setColor(Color.RED);
        }

        g2.fillOval((int)x - a/4, (int)y - a/4, a/2, a/2);
    }
}
