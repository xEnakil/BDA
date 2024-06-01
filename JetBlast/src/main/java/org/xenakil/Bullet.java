package org.xenakil;

import com.googlecode.lanterna.graphics.TextGraphics;

public class Bullet extends GameEntity {
    public Bullet(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.putString(x, y, "|");
    }

    public void moveUp() {
        y -= 1;
    }
}
