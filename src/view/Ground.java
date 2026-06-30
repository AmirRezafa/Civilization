package view;

import controller.Camera;
import controller.GameController;
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

        width = getWidth();
        height = getHeight();

        g2.translate(-GC.getXOffset(), -GC.getYOffset());

        for(Tile tile: GC.getTiles()){
            int a = Math.min(getWidth(), getHeight()) / 45;
            Hex.show(tile.getCenterX() * a, tile.getCenterY() * a, a, g2);
        }

        g2.dispose();
    }

    public static int getwidth() {
        return width;
    }

    public static int getheight() {
        return height;
    }
}
