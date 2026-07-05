package model;

public enum BuildingType {
    LUMBER_MILL("Lumber Mill", TerrainType.FOREST, ResourceType.WOOD, 0, 0, 0),
    STONE_MINE("Stone Mine", TerrainType.MOUNTAIN, ResourceType.STONE, 15, 0, 0),
    IRON_MINE("Iron Mine", TerrainType.MOUNTAIN, ResourceType.IRON, 25, 0, 0),
    FARM("Farm", TerrainType.MEADOW, ResourceType.WHEAT, 0, 0, 0),
    STABLE("Stable", TerrainType.PLAIN, ResourceType.CATTLE, 20, 0, 0),
    TOWN_HALL("Town Hall", null, ResourceType.NONE, 0, 0, 0),
    SETTLEMENT("Settlement", null, ResourceType.NONE, 25, 15, 10);

    private final String displayName;
    private final TerrainType requiredTerrain;
    private final ResourceType requiredResource;

    private final int woodCost;
    private final int stoneCost;
    private final int ironCost;

    BuildingType(String displayName, TerrainType requiredTerrain, ResourceType requiredResource,
                 int woodCost, int stoneCost, int ironCost) {
        this.displayName = displayName;
        this.requiredTerrain = requiredTerrain;
        this.requiredResource = requiredResource;
        this.woodCost = woodCost;
        this.stoneCost = stoneCost;
        this.ironCost = ironCost;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TerrainType getRequiredTerrain() {
        return requiredTerrain;
    }

    public ResourceType getRequiredResource() {
        return requiredResource;
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
}