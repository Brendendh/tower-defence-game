package seng201.team8.models;

public enum Resource {
    CORN(1), WOOD(4), IRON(10);

    final private int resourceValue;
    Resource(int resourceValue) {this.resourceValue = resourceValue;}

    public int getResourceValue(){
        return resourceValue;
    }

}
