package org.xenakil;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            GameEngine engine = new GameEngine();
            engine.start();

            Runtime.getRuntime().addShutdownHook(new Thread(engine::shutdown));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}