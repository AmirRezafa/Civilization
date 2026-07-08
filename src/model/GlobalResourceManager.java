package model;

import java.util.HashMap;
import java.util.Map;

public class GlobalResourceManager {
    private final Map<ResourceType, Integer> resources;

    public GlobalResourceManager() {
        this.resources = new HashMap<>();
        initRegistry();
    }

    private void initRegistry() {
        for (ResourceType type : ResourceType.values()) {
            if (type != ResourceType.NONE) {
                this.resources.put(type, 0);
            }
        }

        this.resources.put(ResourceType.WHEAT, 50);
        this.resources.put(ResourceType.WOOD, 50);
        this.resources.put(ResourceType.IRON, 10);
    }

    public void addResource(ResourceType type, int amount) {
        int current = resources.getOrDefault(type, 0);
        resources.put(type, current + amount);
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
}