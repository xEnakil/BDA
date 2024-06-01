package models;

import java.util.UUID;

public class Item {
    private final String id;
    private final String name;
    private final int x;
    private final int y;
    private final String type;
    private final int value;

    public Item(String name, int x, int y, String type, int value) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.x = x;
        this.y = y;
        this.type = type;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public void printStatus() {
        System.out.println(name + " (" + type + ": " + value + ") at (" + x + ", " + y + ")");
    }
}
