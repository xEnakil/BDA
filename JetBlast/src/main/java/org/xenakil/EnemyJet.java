package org.xenakil;

import com.googlecode.lanterna.graphics.TextGraphics;


public class EnemyJet extends GameEntity {
    public EnemyJet(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.putString(x, y, "V");
    }

    public void moveDown() {
        y += 1;
    }
}