package model;

import java.util.HashMap;
import java.util.Map;

public class GlobalResourceManager {
    private final Map<ResourceType, Integer> resources;
    private final Map<ResourceType, Integer> resourcesCapacity;

    public GlobalResourceManager() {
        this.resources = new HashMap<>();
        this.resourcesCapacity = new HashMap<>();

        this.resourcesCapacity.put(ResourceType.CATTLE, 50);
        this.resourcesCapacity.put(ResourceType.WHEAT, 50);
        this.resourcesCapacity.put(ResourceType.WOOD, 100);
        this.resourcesCapacity.put(ResourceType.STONE, 90);
        this.resourcesCapacity.put(ResourceType.IRON, 80);

        initRegistry();
    }

    private void initRegistry() {
        for (ResourceType type : ResourceType.values()) {
            if (type != ResourceType.NONE) {
                this.resources.put(type, 0);
            }
        }

        this.resources.put(ResourceType.WHEAT, 25);
        this.resources.put(ResourceType.CATTLE, 25);
        this.resources.put(ResourceType.WOOD, 50);
        this.resources.put(ResourceType.IRON, 20);
        this.resources.put(ResourceType.STONE, 30);

    }

    public boolean addResource(ResourceType type, int amount) {
        int current = resources.getOrDefault(type, 0);
        if(current + amount > resourcesCapacity.getOrDefault(type, 0)) return false;
        resources.put(type, current + amount);
        return true;
    }

    public boolean spendResource(ResourceType type, int amount) {
        int current = resources.getOrDefault(type, 0);
        if (current >= amount) {
            resources.put(type, current - amount);
            return true;
        }
        return false;
    }

    public boolean spendFood(int amount){
        int current = resources.getOrDefault(ResourceType.WHEAT, 0);
        if(current >= amount){
            resources.put(ResourceType.WHEAT, current - amount);
            return true;
        }else{
            int secCurrent = resources.getOrDefault(ResourceType.CATTLE, 0);
            if(secCurrent + current >= amount){
                resources.put(ResourceType.WHEAT, 0);
                resources.put(ResourceType.CATTLE, secCurrent - (amount - current));
                return true;
            }
        }
        return false;
    }

    public boolean hasEnough(ResourceType type, int amount){
        int current = resources.getOrDefault(type, 0);
        return (amount <= current);
    }

    public int getResourceAmount(ResourceType type) {
        return resources.getOrDefault(type, 0);
    }

    public boolean hasEnoughFood(int amount) {
        int current = resources.getOrDefault(ResourceType.WHEAT, 0) +
                resources.getOrDefault(ResourceType.CATTLE, 0);
        return (amount <= current);
    }

    public int getResourceCapacityAmount(ResourceType type) {
        return resourcesCapacity.getOrDefault(type, 0);
    }
}