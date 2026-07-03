package model;

public enum BuildingType {
    LUMBER_MILL("Lumber Mill", TerrainType.FOREST, ResourceType.WOOD),
    STONE_MINE("Stone Mine", TerrainType.MOUNTAIN, ResourceType.STONE),
    IRON_MINE("Iron Mine", TerrainType.MOUNTAIN, ResourceType.IRON),
    FARM("Farm", TerrainType.MEADOW, ResourceType.WHEAT),
    STABLE("Stable", TerrainType.PLAIN, ResourceType.CATTLE),
    TOWN_HALL("Town Hall", null, ResourceType.NONE),
    SETTLEMENT("Settlement", null, ResourceType.NONE);

    private final String displayName;
    private final TerrainType requiredTerrain;
    private final ResourceType requiredResource;

    BuildingType(String displayName, TerrainType requiredTerrain, ResourceType requiredResource) {
        this.displayName = displayName;
        this.requiredTerrain = requiredTerrain;
        this.requiredResource = requiredResource;
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
}