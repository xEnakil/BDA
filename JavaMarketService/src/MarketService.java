import Abstraction.AbstractItem;
import Abstraction.Item;

import java.util.HashMap;
import java.util.Map;

public class MarketService<T extends Item> {
    final Map<T, Integer> inventory;

    public MarketService() {
        this.inventory = new HashMap<>();
    }

    public void add(T item, int quantity) {
        inventory.put(item, inventory.getOrDefault(item, 0) + quantity);
    }

    public void removeItem (T item, int quantity) {
        inventory.computeIfPresent(item, (key, val) -> (val <= quantity) ? null : val - quantity);
    }

    public void updateItem(T item, int quantity) {
        if (inventory.containsKey(item)) {
            inventory.put(item, inventory.getOrDefault(item, 0) + quantity);
        } else {
            System.out.println("Item not found in inventory");
        }
    }

    public int getItemQuantity(T item) {
        return inventory.getOrDefault(item, 0);
    }

    public void printInventory() {
        inventory.forEach((item, quantity) -> {
            System.out.println("Item: " + item + " Quantity: " + quantity);
        });
    }
}
