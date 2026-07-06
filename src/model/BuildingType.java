package model;

public enum BuildingType {
    LUMBER_MILL("Lumber Mill", TerrainType.FOREST, ResourceType.WOOD
            , 0, 0, 0, 2),
    STONE_MINE("Stone Mine", TerrainType.MOUNTAIN, ResourceType.STONE,
            15, 0, 0, 3),
    IRON_MINE("Iron Mine", TerrainType.MOUNTAIN, ResourceType.IRON,
            25, 0, 0, 3),
    FARM("Farm", TerrainType.MEADOW, ResourceType.WHEAT,
            0, 0, 0, 2),
    STABLE("Stable", TerrainType.PLAIN, ResourceType.CATTLE,
            20, 0, 0, 2),
    TOWN_HALL("Town Hall", null, ResourceType.NONE,
            0, 0, 0, 0),
    SETTLEMENT("Settlement", null, ResourceType.NONE,
            25, 15, 10, 0);

    private final String displayName;
    private final TerrainType requiredTerrain;
    private final ResourceType outputResource;

    private final int woodCost;
    private final int stoneCost;
    private final int ironCost;

    private final int maxWorkerCapacity;

    BuildingType(String displayName, TerrainType requiredTerrain, ResourceType outputResource,
                 int woodCost, int stoneCost, int ironCost, int maxWorkerCapacity) {
        this.displayName = displayName;
        this.requiredTerrain = requiredTerrain;
        this.outputResource = outputResource;
        this.woodCost = woodCost;
        this.stoneCost = stoneCost;
        this.ironCost = ironCost;
        this.maxWorkerCapacity = maxWorkerCapacity;
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
}