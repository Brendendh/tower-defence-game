package seng201.team8.models;

public class Item {
    private int buyingPrice;
    private int sellingPrice;
    private Rarity rarity;

    public Item(int buyingPrice, int sellingPrice, Rarity rarity){
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.rarity = rarity;
    }

    public void setBuyingPrice(int buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public void setSellingPrice(int sellingPrice){
        this.sellingPrice = sellingPrice;
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }
}