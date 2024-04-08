package seng201.team8.models;

import java.util.ArrayList;

public class ResourceAmountBoost implements Effect{

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

    public int getBoostAmount() {
        return boostAmount;
    }
}
