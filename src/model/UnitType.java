package model;

public enum UnitType {
    EXPLORER("Explorer", 4, 3, 0, 1),
    BUILDER("Builder", 2, 1, 3, 1),
    WORKER("Worker", 2, 1, 0, 1),
    BORDER_EXPANDER("Border Expander", 2, 1, 1, 1);

    private final String displayName;
    private final int maxAP;
    private final int visionRadius;
    private final int baseCharges;
    private final int foodConsumption;

    UnitType(String displayName, int maxAP, int visionRadius, int baseCharges, int foodConsumption) {
        this.displayName = displayName;
        this.maxAP = maxAP;
        this.visionRadius = visionRadius;
        this.baseCharges = baseCharges;
        this.foodConsumption = foodConsumption;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMaxAP() {
        return maxAP;
    }

    public int getVisionRadius() {
        return visionRadius;
    }

    public int getBaseCharges() {
        return baseCharges;
    }

    public int getFoodConsumption() {
        return foodConsumption;
    }
}