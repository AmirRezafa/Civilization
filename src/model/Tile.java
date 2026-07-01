package model;

public class Tile {
    private double centerX, centerY;
    private TerrainType type;

    public Tile(double centerX, double centerY, TerrainType type) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.type = type;
    }

    public TerrainType getType() {
        return type;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }
}
