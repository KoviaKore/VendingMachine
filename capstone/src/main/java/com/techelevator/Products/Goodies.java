package com.techelevator.Products;
import com.techelevator.VendingMachineCLI;
import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Goodies {
    public Map<String, VendingItems> snacks() {

        // using LinkedHashMap because it maintains insertion order and that's what we want
        Map<String, VendingItems> items = new LinkedHashMap<>();

        File itemFile = new File("vendingmachine.csv");

        try (Scanner productChoice = new Scanner(itemFile)) {

            while (productChoice.hasNext()) {

                String lineOfInput = productChoice.nextLine();
                String[] term = lineOfInput.split("\\|");

                if (term[3].equals("Chips")) {
                    Chips chip = new Chips(term[0], term[1], term[3], 5, new BigDecimal(term[2]));
                    items.put(term[0], chip);
                } else if (term[3].equals("Candy")) {
                    Candy candy = new Candy(term[0], term[1], term[3], 5, new BigDecimal(term[2]));
                    items.put(term[0], candy);
                } else if (term[3].equals("Gum")) {
                    Gum gum = new Gum(term[0], term[1], term[3], 5, new BigDecimal(term[2]));
                    items.put(term[0], gum);
                } else {
                    Drink drink = new Drink(term[0], term[1], term[3], 5, new BigDecimal(term[2]));
                    items.put(term[0], drink);
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        }
        return items;
    }


}
