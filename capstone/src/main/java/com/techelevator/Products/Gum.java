package com.techelevator.Products;

import java.math.BigDecimal;

public class Gum extends VendingItems {

    public Gum(String itemNumber, String itemName, String productType, int stockAmount, BigDecimal price) {
        super(itemNumber, itemName, productType, stockAmount, price);
    }

    @Override
    public String dispenseMessage() {
        return " Chew Chew, Yum!";
    }
}
