import Abstraction.Item;
import Items.Dairy;
import Items.Fruit;
import Items.Vegetable;

import java.util.Scanner;

public class Main {
/*          1.	Abstraction.Item interfeysini ve onu implementasiya eden klaslari (meselen: Fruit, Vegetable, Dairy ve s. ) yaradin.
            2.	MarketService generik klasini yaradin, klas yalniz Abstraction.Item interefeysini implement eden tipleri saxlayacag.
            3.	Inventory map yaradin, map her Abstraction.Item-dan marketde neche sayda oldughunu saxlayacag.
            4.	MarketService klasinda ashaghidaki metodlari yazin
                public void addItem(T item, int quantity)
                public void removeItem(T item, int quantity)
                public void updateItem(T item, int quantity)
                public int getItemQuantity(T item)
                public void printInventory()
            5. main metodda marketservice instance yaradib yazdighiniz metodlari test edin*/

    private static MarketService<Item> marketService = new MarketService<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
    boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Inventory Management System ---");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Update Item");
            System.out.println("4. View Inventory");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    removeItem();
                    break;
                case 3:
                    updateItem();
                    break;
                case 4:
                    marketService.printInventory();
                    break;
                case 5:
                    exit = true;
                    exit = true;
                default:
                    System.out.println("Invalid option!");
            }
        }

        System.out.println("Exiting Inventory Management System. Goodbye!");
    }

    private static void addItem() {
        System.out.println("\n--- Add Item ---");
        System.out.print("Enter item type (Fruit/Dairy): ");
        String type = scanner.nextLine();
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter item price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter item quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        Item item = null;
        if (type.equalsIgnoreCase("Fruit")) {
            item = new Fruit(name, price);
        } else if (type.equalsIgnoreCase("Dairy")) {
            item = new Dairy(name, price);
        } else if (type.equalsIgnoreCase("Vegetable")) {
            item = new Vegetable(name, price);
        } else {
            System.out.println("Invalid item type!");
            return;
        }
        marketService.add(item, quantity);
        System.out.println("Item added successfully.");
    }

    private static void removeItem() {
        System.out.println("\n--- Remove Item ---");
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter item quantity to remove: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        Item item = findItemByName(name);
        if (item != null) {
            marketService.removeItem(item, quantity);
            System.out.println("Item removed successfully.");
        } else {
            System.out.println("Item not found!");
        }
    }

    private static void updateItem() {
        System.out.println("\n--- Update Item ---");
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter item quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter item price: ");
        double price = scanner.nextDouble();

        Item item = findItemByName(name);
        if (item != null) {
            marketService.updateItem(item, quantity);
            System.out.println("Item updated successfully.");
        } else {
            System.out.println("Item not found!");
        }
    }

    private static Item findItemByName(String name) {
        for (Item item : marketService.inventory.keySet()) {
            ;
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    // In Case of faster demonstration ;)
    private static void defaultMode () {
        Fruit apple = new Fruit("Apple", 1.5);
        Fruit banana = new Fruit("Banana", 0.75);
        Dairy milk = new Dairy("Milk", 2.0);
        Dairy cheese = new Dairy("Cheese", 5.0);

        marketService.add(apple, 10);
        marketService.add(banana, 20);
        marketService.add(milk, 15);
        marketService.add(cheese, 5);

        marketService.printInventory();

        marketService.removeItem(banana, 5);
        marketService.updateItem(milk, 10);

        System.out.println("\nAfter updates:");
        marketService.printInventory();

        System.out.println("\nQuantity of Cheese: " + marketService.getItemQuantity(cheese));
    }
}