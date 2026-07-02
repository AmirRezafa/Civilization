package view;

import controller.Camera;
import controller.GameController;
import model.TerrainType;
import model.Tile;

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

        width = getWidth();
        height = getHeight();

        g2.translate(-GC.getXOffset(), -GC.getYOffset());

        for(Tile tile : GC.getTiles()) {
            int a = Math.min(getWidth(), getHeight()) / 45;

            double x = ((tile.getCol() + 1) * 1.5) * a;
            double y = ((tile.getRow() + 1) * Math.sqrt(3) +
                    (tile.getCol() % 2 == 0 ? Math.sqrt(3)/2 : 0)) * a;

            Hex.show(x, y, a, g2, getTerrainColor(tile.getType()), tile.isVisible(), tile.isExplored());
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
