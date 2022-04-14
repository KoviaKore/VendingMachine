package com.techelevator;

import com.techelevator.Products.Goodies;
import com.techelevator.view.Menu;
import com.techelevator.Products.VendingItems;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String VENDING_MACHINE_STORE_NAME = "      ******* Welcome to Melanated Vending *******" + "\n"
			+ "******* Where your snack cravings will be fulfilled *******";
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_EXIT };

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_SELECT_PRODUCT,
			PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

	private Menu menu;
	private Map<String, VendingItems> snacks;
	private File receipt;
	private BigDecimal currentBalance = new BigDecimal("0.00");


	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		System.out.println(VENDING_MACHINE_STORE_NAME);

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				for(VendingItems products : snacks.values()) {
					System.out.println(products);
				}
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {

				// do purchase
				while(true) {
					System.out.println("\n" + "Current balance: $" + currentBalance);
					String nextChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

					//Feed money process
					if (nextChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {

						Scanner money = new Scanner(System.in);
						System.out.println("\n" + "Please Insert Money (Bills Only)");

						String dollars = money.nextLine();
						BigDecimal moneyFeed = new BigDecimal(dollars); // Using big decimal because it's more precise when dealing with money compared to a standard double
						currentBalance = currentBalance.add(moneyFeed);

						//Feed money Logs


						//Display items to select for purchase
					} else if (nextChoice.equals(PURCHASE_MENU_SELECT_PRODUCT)) {
						for (VendingItems products : snacks.values()) {
							System.out.println(products);
						}
						Scanner itemSelection = new Scanner(System.in);
						System.out.println("\n" + "Please Select Item Number");
						String itemLocation = itemSelection.next();


						// Exceptions for if items not in stock or user errors
						if (!snacks.containsKey(itemLocation)) {
							System.out.println("Invalid Item Number");
						} else if (snacks.get(itemLocation).getStockAmount() == 0) {
							System.out.println("You've Selected A Sold Out Item");
						} else if (currentBalance.compareTo(snacks.get(itemLocation).getPrice()) == -1) {
							System.out.println("\n" + "Insufficient Funds For Purchase");

							// If no exceptions and users enter and chooses correctly
						} else {
							System.out.println(snacks.get(itemLocation).getItemName() + " S" + snacks.get(itemLocation).getPrice());
							currentBalance = currentBalance.subtract(snacks.get(itemLocation).getPrice());

							System.out.println(snacks.get(itemLocation).dispenseMessage());
							System.out.println("\n" + "Current Balance S" + currentBalance);

							// Product Purchase Log
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);

		Goodies goods = new Goodies();
		cli.snacks = goods.snacks();
		cli.run();
	}
}
