package model;

public enum TerrainType {
    PLAIN(2, 0, 0, 0),
    FOREST(1, 2, 0, 0),
    MOUNTAIN(0, 0, 2, 1),
    MEADOW(3, 0, 0, 0);

    public final int food, wood, stone, iron;

    TerrainType(int food, int wood, int stone, int iron) {
        this.food = food;
        this.wood = wood;
        this.stone = stone;
        this.iron = iron;
    }
}