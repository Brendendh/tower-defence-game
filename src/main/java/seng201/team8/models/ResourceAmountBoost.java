package seng201.team8.models;

import java.util.ArrayList;

public class ResourceAmountBoost implements Effect, Cloneable{

    private int boostAmount;

    public ResourceAmountBoost(int boostAmount){
        this.boostAmount = boostAmount;
    }

    @Override
    public void affects(ArrayList<Tower> towers) {
        for (Tower tower: towers){
            tower.getTowerStats().setResourceAmount(tower.getTowerStats().getResourceAmount() + boostAmount);
        }
    }

    @Override
    public ResourceAmountBoost clone(){
        try{
            return (ResourceAmountBoost) super.clone();
        }
        catch (CloneNotSupportedException e){
            return new ResourceAmountBoost(this.getBoostAmount());
        }
    }

    public int getBoostAmount() {
        return boostAmount;
    }
    public void setBoostAmount(int boostAmount){this.boostAmount = boostAmount;}

    public String getEffectName(){
        return "Resource Amount Boost";
    }

    public String toString(){
        return "Increases the resource production amount of towers by " + boostAmount;
    }
}
