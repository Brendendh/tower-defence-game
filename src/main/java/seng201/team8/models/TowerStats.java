package seng201.team8.models;

public class TowerStats implements Cloneable{
    private int resourceAmount;
    private Resource resourceType;
    private int cooldown;

    public TowerStats(int resourceAmount, Resource resourceType, int cooldown){
        this.resourceAmount = resourceAmount;
        this.resourceType = resourceType;
        this.cooldown = cooldown;
    }

    public int getResourceAmount() {
        return resourceAmount;
    }

    public Resource getResourceType() {
        return resourceType;
    }

    public int getCooldown(){
        return cooldown;
    }

    public void setResourceAmount(int resourceAmount) {
        this.resourceAmount = resourceAmount;
    }

    public void setResourceType(Resource resourceType) {
        this.resourceType = resourceType;
    }

    public void setCooldown(int cooldown) {
        if (cooldown >= 1){
            this.cooldown = cooldown;
        }
    }

    @Override
    public TowerStats clone(){
        try{
            return (TowerStats) super.clone();
        }
        catch (CloneNotSupportedException e){
            return new TowerStats(this.getResourceAmount(), this.getResourceType(), this.getCooldown());
        }
    }
}
