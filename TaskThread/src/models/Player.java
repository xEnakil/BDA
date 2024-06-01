package models;

import entity.GameEntity;

import java.util.concurrent.locks.ReentrantLock;

public class Player extends GameEntity {
    public int armor;

    public Player(int x, int y,int health, int armor) {
        super(x, y, armor);
        this.armor = armor;
    }

    public void takeDamage(int damage) {
        lock.lock();
        try {
            int effectiveDamage = Math.max(damage - armor, 0);
            health -= effectiveDamage;
        } finally {
            lock.unlock();
        }
    }

    public void heal(int amount) {
        lock.lock();
        try {
            health += amount;
        } finally {
            lock.unlock();
        }
    }

    public void printStatus() {
        System.out.println("Player (HP: " + health + ", Armor: " + armor + ") at (" + x + ", " + y + ")");
    }
}
