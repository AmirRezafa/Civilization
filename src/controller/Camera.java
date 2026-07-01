package controller;

import view.Ground;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Camera implements MouseMotionListener, Runnable, MouseListener{
    private int xOffset = 0, yOffset = 0;

    private int left = 0, right = 0;
    private int up = 0, down = 0;
    @Override
    public void mouseMoved(MouseEvent e) {
        left = Math.max(0, (int)((Ground.getwidth() * 0.1) - e.getX()));
        right = Math.max(0, (int)(e.getX() - (Ground.getwidth() * 0.9)));
        up = Math.max(0, (int)((Ground.getheight() * 0.1) - e.getY()));
        down = Math.max(0, (int)(e.getY() - (Ground.getheight() * 0.9)));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void run() {
        xOffset += right / 3;
        xOffset -= left / 3;
        yOffset -= up / 3;
        yOffset += down / 3;
        xOffset = Math.max(0, xOffset);
        yOffset = Math.max(0, yOffset);
        int a = Math.min(Ground.getheight(), Ground.getwidth()) / 45;
        double h = a * Math.sqrt(3);
        xOffset = Math.min((int)(a * ((GameController.COLS * 1.5) + 2)) - Ground.getwidth(), xOffset);
        yOffset = Math.min((int)(h * GameController.ROWS + 2 * a) - Ground.getheight(), yOffset);

    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        left = 0;
        right = 0;
        up = 0;
        down = 0;
    }
}
