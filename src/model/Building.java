package model;

import java.util.ArrayList;

public class Building {
    private final BuildingType type;
    private final int col;
    private final int row;
    private boolean isOccupied;
    private static final int BASE_PRODUCTION_RATE = 2;
    private ArrayList<Unit> workers;

    private UnitType producingUnit = null;
    private int productionTurnsLeft = 0;

    public Building(BuildingType type, int col, int row) {
        this.type = type;
        this.col = col;
        this.row = row;
        this.isOccupied = false;
        workers = new ArrayList<>();
    }

    public void processTurnProduction(Tile tile, GlobalResourceManager economy) {
        if (type == BuildingType.TOWN_HALL) {
            economy.addResource(ResourceType.WHEAT, 1);
            return;
        }

        if (!isOccupied || type == BuildingType.SETTLEMENT) return;

        ResourceType targetResource = type.getOutputResource();
        System.out.println(targetResource);

        if (targetResource != null && targetResource != ResourceType.NONE) {
            if (tile.hasResource(targetResource)) {
                System.out.println(targetResource);
                System.out.println(workers.size());
                economy.addResource(targetResource,
                        tile.extractResource(targetResource, BASE_PRODUCTION_RATE * workers.size()));
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

    public void addWorker(Unit worker){
        workers.add(worker);
        isOccupied = true;
    }

    public void removeWorker(Unit worker){
        workers.remove(worker);
        if(workers.isEmpty()) isOccupied = false;
    }

    public boolean needWorker() {
        return workers.size() < type.getMaxWorkerCapacity();
    }

    public ArrayList<Unit> getStationedWorkers() {
        return workers;
    }

    public Unit getLastWorker() {
        return workers.get(workers.size() - 1);
    }

    public void startProducing(UnitType type) {
        this.producingUnit = type;
        this.productionTurnsLeft = type.getBuildTurns();
    }

    public boolean isProducing() {
        return (producingUnit != null);
    }

    public UnitType getProducingUnit() {
        return producingUnit;
    }

    public int getProductionTurnsLeft() {
        return productionTurnsLeft;
    }

    public void decrementProductionTurns() {
        productionTurnsLeft--;
    }

    public void clearProduction() {
        producingUnit = null;
        productionTurnsLeft = 0;
    }
}