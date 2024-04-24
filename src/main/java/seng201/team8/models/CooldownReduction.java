package seng201.team8.models;

import java.util.ArrayList;

public class CooldownReduction implements Effect, Cloneable{

    private int reductionAmount;

    public CooldownReduction(int reductionAmount){
        this.reductionAmount = reductionAmount;
    }

    @Override
    public void affects(ArrayList<Tower> towers) {
        for(Tower tower: towers){
            tower.getTowerStats().setCooldown(tower.getTowerStats().getCooldown() - reductionAmount);
        }
    }

    @Override
    public CooldownReduction clone(){
        try {
            return (CooldownReduction) super.clone();
        }
        catch (CloneNotSupportedException e){
            return new CooldownReduction(this.getReductionAmount());
        }
    }

    public int getReductionAmount() {
        return reductionAmount;
    }
    public void setReductionAmount(int reductionAmount){this.reductionAmount = reductionAmount;}
}
