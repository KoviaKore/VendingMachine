package com.techelevator.Products;

import java.math.BigDecimal;

public class Candy extends VendingItems {

    public Candy(String itemNumber, String itemName, String productType, int stockAmount, BigDecimal price) {
        super(itemNumber, itemName, productType, stockAmount, price);
    }
    @Override
    public String dispenseMessage() {
        return "Munch Munch, Yum!";
    }
}
