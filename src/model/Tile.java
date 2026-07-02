package model;

public class Tile {
    private int col, row;
    private TerrainType type;

    private boolean isVisible = false;
    private boolean Explored = false;

    public Tile(int col, int row, TerrainType type) {
        this.col = col;
        this.row = row;
        this.type = type;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
        if (visible) this.Explored = true;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean isExplored() {
        return Explored;
    }

    public TerrainType getType() {
        return type;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
