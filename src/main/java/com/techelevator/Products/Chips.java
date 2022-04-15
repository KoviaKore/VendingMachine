package com.techelevator.Products;

import java.math.BigDecimal;

public class Chips extends VendingItems {

    public Chips(String itemNumber, String itemName, String productType, int stockAmount, BigDecimal price) {
        super(itemNumber, itemName, productType, stockAmount, price);
    }
    @Override
    public String dispenseMessage() {
        return "Crunch Crunch, Yum!";
    }

}
