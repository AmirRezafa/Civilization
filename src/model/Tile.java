package model;

import java.util.Map;

public class Tile {
    private int col, row;

    private TerrainType terrain;
    private Map<ResourceType, Integer> resources;
    private BuildingType building;

    private int resourceCapacity;
    private int stationedWorkersCount;
    private boolean isWithinBorders;

    private boolean isVisible = false;
    private boolean Explored = false;

    public Tile(int col, int row, TerrainType terrain, Map<ResourceType, Integer> resources) {
        this.col = col;
        this.row = row;
        this.terrain = terrain;
        this.resources = resources;
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

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public TerrainType getTerrain() {
        return terrain;
    }
}
