package com.techelevator;

import com.techelevator.Products.Goodies;
import com.techelevator.view.Menu;
import com.techelevator.Products.VendingItems;


import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String VENDING_MACHINE_STORE_NAME = "\n" + "\n" + "      ******* Welcome to GOOD VIBEZ Vending *******" + "\n"
			+ "******* Where your snack cravings will be fulfilled *******";
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_EXIT};

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_SELECT_PRODUCT,
			PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

	private Menu menu;
	private Map<String, VendingItems> snacks;
	private BigDecimal currentBalance = new BigDecimal("0.00");
	private BigDecimal totalFeed = new BigDecimal("0.00");
	private BigDecimal changeGiven = new BigDecimal("0.00");
	private File log = new File("Log.txt");
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu hh:mm:ss a");


	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		System.out.println(VENDING_MACHINE_STORE_NAME);


		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);


			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				for (VendingItems products : snacks.values()) {
					System.out.println(products);
				}
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {

				// do purchase

				while (true) {
				System.out.println("\n" + "Current balance: $" + currentBalance);
				String nextChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

				//Feed money process
				if (nextChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {

					Scanner money = new Scanner(System.in);
					System.out.println("\n" + "Please Insert Money (Bills Only)");

					String dollars = money.nextLine();
					// Using big decimal because it's more precise when dealing with money compared to a standard double
					BigDecimal moneyFeed = new BigDecimal(dollars);
					totalFeed = totalFeed.add(moneyFeed);

					//Feed money Logs

					try (PrintWriter vendingLogs = new PrintWriter(new FileOutputStream(log, true))) {
						vendingLogs.println(LocalDateTime.now().format(formatter) + " FEED MONEY: $" + dollars + ".00 $" + totalFeed);
					} catch (FileNotFoundException e) {
						System.err.println("File not found");
					}


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
						System.out.println("Oh No! Invalid Item Number");
					} else if (snacks.get(itemLocation).getStockAmount() == 0) {
						System.out.println("Sorry! You've Selected A Sold Out Item");
					} else if (totalFeed.compareTo(snacks.get(itemLocation).getPrice()) == -1) {
						System.out.println("\n" + "Oops! Insufficient Funds For Purchase");

						// If no exceptions and users enter and chooses correctly
					} else {
						System.out.println(snacks.get(itemLocation).getItemName() + " $" + snacks.get(itemLocation).getPrice());
						currentBalance = totalFeed.subtract(snacks.get(itemLocation).getPrice());


						// Dispenses specific snack message
						System.out.println(snacks.get(itemLocation).dispenseMessage());
						// decrements item stock
						snacks.get(itemLocation).setStockAmount(snacks.get(itemLocation).getStockAmount() - 1);

						// Product Purchase Log
						try (PrintWriter vendingLogs = new PrintWriter(new FileOutputStream(log, true))) {
							vendingLogs.println(LocalDateTime.now().format(formatter) + " " + snacks.get(itemLocation).getItemName()
									+ " " + snacks.get(itemLocation).getItemNumber() + " $" + totalFeed + " $" + currentBalance);
						} catch (FileNotFoundException e) {
							System.out.println("File not Found");
						}

					}

				} else if (nextChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
					int exactChange = currentBalance.multiply(new BigDecimal("100")).intValue();

					int quarters = 0;
					int quarterWorth = 25;

					int dimes = 0;
					int dimeWorth = 10;

					int nickels = 0;
					int nickelWorth = 5;

					// calculate amount of coins
					while (exactChange > 0) {
						quarters = exactChange / quarterWorth;
						exactChange -= quarters * quarterWorth;

						dimes = exactChange / dimeWorth;
						exactChange -= dimes * dimeWorth;

						nickels = exactChange / nickelWorth;
						exactChange -= nickels * nickelWorth;

						changeGiven = currentBalance;

						System.out.println("Your change is $" + changeGiven + "\n" + "That's " + quarters + " Quarters " + dimes + " Dimes " +
								nickels + " Nickels!" + "\n" + "* Let this snack calm your mind! *" + "\n" +
								"  *** And soothe your soul! ***  " +
								"\n" + "  * Thank you for VIBIN with us! *  ");
					}

					//changeGiven = currentBalance;

					currentBalance = currentBalance.subtract(currentBalance);
					System.out.println("\n" + "Current balance: $" + currentBalance);

					// give change logs
					try (PrintWriter vendingLogs = new PrintWriter(new FileOutputStream(log, true))) {
						vendingLogs.println(LocalDateTime.now().format(formatter) + " GIVE CHANGE $" + changeGiven + " $" + currentBalance);
					} catch (FileNotFoundException e) {
						System.out.println("File not Found");
					}


					// return to main menu so that we can exit the program
					//menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
					run();
				   }
				}
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.exit(0);
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
