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

    public int getResourceAmount(ResourceType type) {
        return resources.getOrDefault(type, 0);
    }
}