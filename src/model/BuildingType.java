package model;

import java.util.ArrayList;

public enum BuildingType {
    LUMBER_MILL("Lumber Mill", TerrainType.FOREST, ResourceType.WOOD
            , 0, 0, 0, 2, 1, 0),
    STONE_MINE("Stone Mine", TerrainType.MOUNTAIN, ResourceType.STONE,
            15, 0, 0, 3, 2, 0),
    IRON_MINE("Iron Mine", TerrainType.MOUNTAIN, ResourceType.IRON,
            25, 0, 0, 3, 2, 0),
    FARM("Farm", TerrainType.MEADOW, ResourceType.WHEAT,
            0, 0, 0, 2, 2, 0),
    STABLE("Stable", TerrainType.PLAIN, ResourceType.CATTLE,
            20, 0, 0, 2, 3, 0),
    TOWN_HALL("Town Hall", null, ResourceType.NONE,
            0, 0, 0, 0, 0, 3),
    SETTLEMENT("Settlement", null, ResourceType.NONE,
            25, 15, 10, 0, 2, 2);

    private final String displayName;
    private final TerrainType requiredTerrain;
    private final ResourceType outputResource;

    private final int woodCost;
    private final int stoneCost;
    private final int ironCost;
    private final int apCost;

    private final int maxWorkerCapacity;

    private final int visionRadius;

    BuildingType(String displayName, TerrainType requiredTerrain, ResourceType outputResource,
                 int woodCost, int stoneCost, int ironCost, int maxWorkerCapacity, int apCost, int visionRadius) {
        this.displayName = displayName;
        this.requiredTerrain = requiredTerrain;
        this.outputResource = outputResource;
        this.woodCost = woodCost;
        this.stoneCost = stoneCost;
        this.ironCost = ironCost;
        this.maxWorkerCapacity = maxWorkerCapacity;
        this.apCost = apCost;
        this.visionRadius = visionRadius;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TerrainType getRequiredTerrain() {
        return requiredTerrain;
    }

    public ResourceType getOutputResource() {
        return outputResource;
    }

    public int getWoodCost() {
        return woodCost;
    }

    public int getStoneCost() {
        return stoneCost;
    }

    public int getIronCost() {
        return ironCost;
    }

    public int getMaxWorkerCapacity() {
        return maxWorkerCapacity;
    }

    public int getApCost() {
        return apCost;
    }

    public String getCostString() {
        ArrayList<String> costs = new ArrayList<>();

        if (woodCost > 0) costs.add(woodCost + " Wood");
        if (stoneCost > 0) costs.add(stoneCost + " Stone");
        if (ironCost > 0) costs.add(ironCost + " Iron");

        if (costs.isEmpty()) return "Free";

        return String.join(", ", costs);
    }

    public int getVisionRadius() {
        return visionRadius;
    }
}