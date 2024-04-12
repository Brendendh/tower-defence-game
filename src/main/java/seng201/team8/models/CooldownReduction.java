package seng201.team8.models;

import java.util.ArrayList;

public class CooldownReduction implements Effect{

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

    public int getReductionAmount() {
        return reductionAmount;
    }
    public void setReductionAmount(int reductionAmount){this.reductionAmount = reductionAmount;}
}
