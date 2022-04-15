package com.techelevator.Products;

import java.math.BigDecimal;

public abstract class VendingItems {

    private String itemNumber;
    private String itemName;
    private String productType;
    public int stockAmount;
    private BigDecimal price;

    public VendingItems() {
    }

    public VendingItems(String itemNumber, String itemName, String productType, int stockAmount, BigDecimal price) {
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.productType = productType;
        this.stockAmount = stockAmount;
        this.price = price;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public String getProductType() {
        return productType;
    }

    public int getStockAmount() {
        return stockAmount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    // stock amount needs to decrease by the amount of items that are vended

    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount--;
    }
    // unique message will showcase once each subclass snack item dispenses
    public abstract String dispenseMessage();

    public String toString() {
        return itemNumber + " | " + itemName + " | " + stockAmount + " left | $" + price;
    }
}
