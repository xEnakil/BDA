package org.xenakil;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameEngine {

    private final PilotFighter pilot;
    private final List<EnemyJet> enemies;
    private final List<Bullet> bullets;
    private final ExecutorService executor;
    private final Screen screen;
    private final Random random;
    private boolean spacePressed;

    public GameEngine() throws IOException {
        this.pilot = new PilotFighter(10, 20);
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.executor = Executors.newFixedThreadPool(4);
        this.screen = new DefaultTerminalFactory().createScreen();
        this.random = new Random();
        this.spacePressed = false;
        screen.startScreen();
        screen.setCursorPosition(null);
        System.out.println("Screen started and cursor set to null.");
    }

    public void start() {
        System.out.println("Game started.");
        executor.submit(this::handleInput);
        executor.submit(this::spawnEnemies);
        executor.submit(this::moveEnemies);
        executor.submit(this::moveBullets);
        executor.submit(this::render);
    }

    private void handleInput() {
        try {
            while (true) {
                KeyStroke keyStroke = screen.pollInput();
                if (keyStroke != null) {
                    switch (keyStroke.getKeyType()) {
                        case ArrowLeft -> {
                            pilot.moveLeft();
                            System.out.println("Pilot moved left to: " + pilot.getX() + ", " + pilot.getY());
                        }
                        case ArrowRight -> {
                            pilot.moveRight(screen.getTerminalSize().getColumns());
                            System.out.println("Pilot moved right to: " + pilot.getX() + ", " + pilot.getY());
                        }
                        case Character -> {
                            if (keyStroke.getCharacter() == ' ' && !spacePressed) {
                                bullets.add(new Bullet(pilot.getX(), pilot.getY() - 1));
                                spacePressed = true;
                                System.out.println("Bullet fired.");
                            }
                        }
                        case Escape -> {
                            shutdown();
                            return;
                        }
                        default -> spacePressed = false;
                    }
                }
                Thread.sleep(50); // Adding a slight delay to reduce CPU usage
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void spawnEnemies() {
        while (true) {
            enemies.add(new EnemyJet(random.nextInt(screen.getTerminalSize().getColumns()), 0));
            System.out.println("Enemy spawned at: " + enemies.get(enemies.size() - 1).getX() + ", " + enemies.get(enemies.size() - 1).getY());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void moveEnemies() {
        while (true) {
            synchronized (enemies) {
                for (EnemyJet enemy : enemies) {
                    enemy.moveDown();
                    System.out.println("Enemy moved down to: " + enemy.getX() + ", " + enemy.getY());
                }
                enemies.removeIf(enemy -> enemy.getY() >= screen.getTerminalSize().getRows());
            }
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void moveBullets() {
        while (true) {
            synchronized (bullets) {
                for (Bullet bullet : bullets) {
                    bullet.moveUp();
                    System.out.println("Bullet moved up to: " + bullet.getX() + ", " + bullet.getY());
                }
                bullets.removeIf(bullet -> bullet.getY() < 0);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void render() {
        try {
            while (true) {
                screen.clear();
                TextGraphics graphics = screen.newTextGraphics();
                pilot.draw(graphics);
                System.out.println("Pilot drawn at: " + pilot.getX() + ", " + pilot.getY());
                synchronized (enemies) {
                    for (EnemyJet enemy : enemies) {
                        enemy.draw(graphics);
                        System.out.println("Enemy drawn at: " + enemy.getX() + ", " + enemy.getY());
                    }
                }
                synchronized (bullets) {
                    for (Bullet bullet : bullets) {
                        bullet.draw(graphics);
                        System.out.println("Bullet drawn at: " + bullet.getX() + ", " + bullet.getY());
                    }
                }
                handleCollisions();
                screen.refresh();
                System.out.println("Screen refreshed.");
                TimeUnit.MILLISECONDS.sleep(50);
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void handleCollisions() {
        List<EnemyJet> enemiesToRemove = new ArrayList<>();
        List<Bullet> bulletsToRemove = new ArrayList<>();

        synchronized (enemies) {
            synchronized (bullets) {
                for (EnemyJet enemy : enemies) {
                    for (Bullet bullet : bullets) {
                        if (bullet.getX() == enemy.getX() && bullet.getY() == enemy.getY()) {
                            enemiesToRemove.add(enemy);
                            bulletsToRemove.add(bullet);
                            System.out.println("Collision detected at: " + enemy.getX() + ", " + enemy.getY());
                        }
                    }
                }
            }
        }

        enemies.removeAll(enemiesToRemove);
        bullets.removeAll(bulletsToRemove);
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
            screen.stopScreen();
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}