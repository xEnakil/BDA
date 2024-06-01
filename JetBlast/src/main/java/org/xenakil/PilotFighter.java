package org.xenakil;

import com.googlecode.lanterna.graphics.TextGraphics;


public class PilotFighter extends GameEntity {
    public PilotFighter(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.putString(x, y, "^");
    }

    public void moveLeft() {
        x = Math.max(0, x - 1);
    }

    public void moveRight(int maxWidth) {
        x = Math.min(maxWidth - 1, x + 1);
    }
}