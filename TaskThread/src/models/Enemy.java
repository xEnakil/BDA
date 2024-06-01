package models;

import entity.GameEntity;

public class Enemy extends GameEntity {
    private final String type;

    public Enemy(String type, int x, int y, int health) {
        super(x, y, health);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void attack(Player player) {
        player.lock.lock();
        try {
            if (player.isAlive()) {
                player.takeDamage(10);
            }
        } finally {
            player.lock.unlock();
        }
    }

    public void printStatus() {
        System.out.println(type + " (HP: " + health + ") at (" + x + ", " + y + ")");
    }
}
