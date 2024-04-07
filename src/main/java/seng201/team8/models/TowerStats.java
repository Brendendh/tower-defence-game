package seng201.team8.models;

public class TowerStats {
    private int resourceAmount;
    private String resourceType;
    private int cooldown;

    public TowerStats(int resourceAmount, String resourceType, int cooldown){
        this.resourceAmount = resourceAmount;
        this.resourceType = resourceType;
        this.cooldown = cooldown;
    }

    public int getResourceAmount() {
        return resourceAmount;
    }

    public String getResourceType() {
        return resourceType;
    }

    public int getCooldown(){
        return cooldown;
    }

    public void setResourceAmount(int resourceAmount) {
        this.resourceAmount = resourceAmount;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
