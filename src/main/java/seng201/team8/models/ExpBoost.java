package seng201.team8.models;

import java.util.ArrayList;

public class ExpBoost implements Effect{
    private int boostAmount;

    public ExpBoost(int boostAmount){this.boostAmount = boostAmount;}

    @Override
    public void affects(ArrayList<Tower> towers) {
        for(Tower tower: towers){
            tower.setExperiencePoints(tower.getExperiencePoints() + boostAmount);
        }
    }

    public int getBoostAmount(){
        return boostAmount;
    }
    public void setBoostAmount(int boostAmount){
        this.boostAmount = boostAmount;
    }

}
