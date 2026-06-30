package model;

public class Tile {
    private double centerX, centerY;

    public Tile(double centerX, double centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }
}
