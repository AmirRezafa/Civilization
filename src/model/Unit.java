package model;

public class Unit {
    private int col, row;
    private UnitType type;

    public Unit(int col, int row, UnitType type) {
        this.col = col;
        this.row = row;
        this.type = type;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public UnitType getType() {
        return type;
    }
}
