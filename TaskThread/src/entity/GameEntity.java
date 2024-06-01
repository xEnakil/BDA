package entity;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class GameEntity {
    protected String id;
    protected int x;
    protected int y;
    public int health;
    public final ReentrantLock lock;

    public GameEntity(int health, int y, int x) {
        this.lock = new ReentrantLock();
        this.health = health;
        this.y = y;
        this.x = x;
        this.id = UUID.randomUUID().toString();
    }


    public String getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public boolean isAlive() {
        return health > 0;
    }
}
