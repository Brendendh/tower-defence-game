package seng201.team8.models;

import java.util.ArrayList;

public class RepairTower implements Effect, Cloneable{

    @Override
    public void affects(ArrayList<Tower> towers){
        for (Tower tower: towers){
            tower.setBroken(false);
        }
    }
    @Override
    public RepairTower clone(){
        try{
            return (RepairTower) super.clone();
        }
        catch (CloneNotSupportedException e){
            return new RepairTower();
        }
    }


}
