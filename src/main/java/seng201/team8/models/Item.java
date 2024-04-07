package seng201.team8.models;

public class Item {
    private int buyingPrice;
    private int sellingPrice;
    private String rarity;

    public Item(int buyingPrice, int sellingPrice){
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
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