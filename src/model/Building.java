package model;

public class Building {
    private final BuildingType type;
    private final int col;
    private final int row;
    private boolean isOccupied;
    private static final int BASE_PRODUCTION_RATE = 2;

    public Building(BuildingType type, int col, int row) {
        this.type = type;
        this.col = col;
        this.row = row;
        this.isOccupied = false;
    }

    public void processTurnProduction(Tile tile, GlobalResourceManager economy) {
        if (type == BuildingType.TOWN_HALL || type == BuildingType.SETTLEMENT) {
            return;
        }

        if (!isOccupied) return;

        ResourceType targetResource = type.getRequiredResource();

        if (targetResource != null && targetResource != ResourceType.NONE) {
            if (tile.hasResource(targetResource)) {
                economy.addResource(targetResource,
                        tile.extractResource(targetResource, BASE_PRODUCTION_RATE));
            }
        }
    }

    public BuildingType getType() {
        return type;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}