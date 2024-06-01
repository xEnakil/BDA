import engine.GameManager;
import models.Enemy;
import models.Item;
import models.Player;

public class Main {
    public static void main(String[] args) {
        Player player = new Player(0, 0, 100, 10);
        GameManager gameManager = new GameManager(10, player);

        Enemy dragon = new Enemy("Dragon", 3, 3, 50);
        Enemy skeleton = new Enemy("Skeleton", 6, 6, 30);

        gameManager.addEnemy(dragon);
        gameManager.addEnemy(skeleton);

        Item potion = new Item("Health Potion", 2, 2, "health", 20);
        Item armor = new Item("Armor", 5, 5, "armor", 5);

        gameManager.addItem(potion);
        gameManager.addItem(armor);

        gameManager.handleCommands();

        gameManager.shutdown();
    }
}