package model;

import view.UnitActionPanel;

public class Unit {
    private UnitType type;
    private int col;
    private int row;

    private int currentAP;
    private boolean isMoving;
    private double x, y;
    private double targetX, targetY;

    public Unit(UnitType type, int startCol, int startRow) {
        this.type = type;
        this.col = startCol;
        this.row = startRow;
        x = ((col + 1) * 1.5);
        y = ((row + 1) * Math.sqrt(3) +
                (col % 2 == 0 ? Math.sqrt(3)/2 : 0));
        this.currentAP = type.getMaxAP();
        this.isMoving = false;
    }

    public boolean move(int targetCol, int targetRow, int movementCost) {
        if (this.currentAP >= movementCost) {
            this.currentAP -= movementCost;
            x = ((col + 1) * 1.5);
            y = ((row + 1) * Math.sqrt(3) +
                    (col % 2 == 0 ? Math.sqrt(3)/2 : 0));

            this.col = targetCol;
            this.row = targetRow;
            targetX = ((col + 1) * 1.5);
            targetY = ((row + 1) * Math.sqrt(3) +
                    (col % 2 == 0 ? Math.sqrt(3)/2 : 0));

            this.isMoving = true;

            return true;
        }
        return false;
    }

    public UnitType getType() {
        return type;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getCurrentAP() {
        return currentAP;
    }

    public void setCurrentAP(int currentAP) {
        this.currentAP = currentAP;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getTargetX() {
        return targetX;
    }

    public double getTargetY() {
        return targetY;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void animationDone() {
        isMoving = false;
        x = targetX;
        y = targetY;
    }

    public void resetActionPoints(){
        currentAP = type.getMaxAP();
    }
}