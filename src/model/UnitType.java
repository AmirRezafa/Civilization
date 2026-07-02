package model;

public enum UnitType {
    EXPLORER(1, 2),
    BUILDER(2, 1),
    WORKER(1, 1);

    private final int cost;
    private final int movementPoints;

    UnitType(int cost, int movementPoints) {
        this.cost = cost;
        this.movementPoints = movementPoints;
    }

    public int getCost() {
        return cost;
    }

    public int getMovementPoints() {
        return movementPoints;
    }
}