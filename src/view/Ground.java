package view;

import controller.Camera;
import controller.GameController;
import model.TerrainType;
import model.Tile;
import model.Unit;

import javax.swing.*;
import java.awt.*;

public class Ground extends JPanel{
    private static int width, height;
    private static GameController GC;


    Ground(){
        setBackground(Color.GRAY);
        GC = new GameController(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        width = getWidth();
        height = getHeight();

        g2.translate(-GC.getXOffset(), -GC.getYOffset());

        int a = Math.min(getWidth(), getHeight()) / 45;

        for(Tile tile : GC.getTiles()) {

            double x = ((tile.getCol() + 1) * 1.5) * a;
            double y = ((tile.getRow() + 1) * Math.sqrt(3) +
                    (tile.getCol() % 2 == 0 ? Math.sqrt(3)/2 : 0)) * a;

            Hex.show(x, y, a, g2, getTerrainColor(tile.getType()), tile.isVisible(), tile.isExplored());
        }

        g2.setColor(Color.RED);
        for (Unit unit : GC.getUnits()) {
            double x = ((unit.getCol() + 1) * 1.5) * a;
            double y = ((unit.getRow() + 1) * Math.sqrt(3) +
                    (unit.getCol() % 2 == 0 ? Math.sqrt(3)/2 : 0)) * a;

            g2.fillOval((int)x - a/4, (int)y - a/4, a/2, a/2);
        }

        g2.dispose();
    }

    private Color getTerrainColor(TerrainType type) {
        return switch (type) {
            case PLAIN -> new Color(180, 200, 100);
            case FOREST -> new Color(34, 139, 34);
            case MOUNTAIN -> new Color(128, 128, 128);
            case MEADOW -> new Color(144, 238, 144);
        };
    }

    public static int getwidth() {
        return width;
    }

    public static int getheight() {
        return height;
    }
}
