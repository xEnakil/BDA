package engine;

import entity.GameEntity;
import models.Enemy;
import models.Item;
import models.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameManager {
    private final Map<String, GameEntity> entities = new ConcurrentHashMap<>();
    private final List<Item> items = Collections.synchronizedList(new ArrayList<>());
    private final int gridSize;
    private final Player player;
    private final ExecutorService executor;
    private final Random random = new Random();

    public GameManager(int gridSize, Player player) {
        this.gridSize = gridSize;
        this.player = player;
        this.executor = Executors.newCachedThreadPool();
        entities.put(player.getId(), player);
    }

    public void addEnemy(Enemy enemy) {
        entities.put(enemy.getId(), enemy);
        executor.submit(() -> runEnemy(enemy));
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void movePlayer(int dx, int dy) {
        player.lock.lock();
        try {
            player.move(dx, dy);
            System.out.println("Player moved to (" + player.getX() + ", " + player.getY() + ")");
            checkForItemPickup();
        } finally {
            player.lock.unlock();
        }
    }

    public void attackEnemy(String enemyId) {
        Enemy enemy = (Enemy) entities.get(enemyId);
        if (enemy != null && enemy.isAlive()) {
            enemy.lock.lock();
            try {
                enemy.health -= 10;
                System.out.println("Attacked " + enemy.getType() + " (HP: " + enemy.getHealth() + ")");
                if (!enemy.isAlive()) {
                    System.out.println(enemy.getType() + " is dead.");
                }
            } finally {
                enemy.lock.unlock();
            }
        }
    }

    private void runEnemy(Enemy enemy) {
        while (enemy.isAlive()) {
            int dx = random.nextInt(3) - 1;
            int dy = random.nextInt(3) - 1;
            enemy.lock.lock();
            try {
                enemy.move(dx, dy);
                System.out.println(enemy.getType() + " moved to (" + enemy.getX() + ", " + enemy.getY() + ")");
                if (Math.abs(enemy.getX() - player.getX()) <= 1 && Math.abs(enemy.getY() - player.getY()) <= 1) {
                    enemy.attack(player);
                    System.out.println(enemy.getType() + " attacked Player (HP: " + player.getHealth() + ")");
                }
            } finally {
                enemy.lock.unlock();
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void checkForItemPickup() {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getX() == player.getX() && item.getY() == player.getY()) {
                System.out.println("Picked up " + item.getName());
                if (item.getType().equals("health")) {
                    player.heal(item.getValue());
                } else if (item.getType().equals("armor")) {
                    player.armor += item.getValue();
                }
                iterator.remove();
            }
        }
    }

    public void printGameState() {
        player.printStatus();
        entities.values().forEach(entity -> {
            if (entity instanceof Enemy) {
                ((Enemy) entity).printStatus();
            }
        });
        synchronized (items) {
            items.forEach(Item::printStatus);
        }
    }

    public void handleCommands() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter command: [move <dx> <dy> | attack <enemyId> | status]");
            String command = scanner.nextLine();
            String[] parts = command.split(" ");
            switch (parts[0]) {
                case "move":
                    movePlayer(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    break;
                case "attack":
                    attackEnemy(parts[1]);
                    break;
                case "status":
                    printGameState();
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }
    }

    public void shutdown() {
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
