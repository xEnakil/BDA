package org.xenakil;

import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class GameEntity {
    protected int x, y;

    public GameEntity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void draw(TextGraphics graphics);
}
