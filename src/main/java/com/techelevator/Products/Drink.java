package com.techelevator.Products;

import java.math.BigDecimal;

public class Drink extends VendingItems {

    public Drink(String itemNumber, String itemName, String productType, int stockAmount, BigDecimal price) {
        super(itemNumber, itemName, productType, stockAmount, price);
    }

    @Override
    public String dispenseMessage() {
        return "Glug Glug, Yum!";
    }
}
