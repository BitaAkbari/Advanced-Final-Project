package advancedfinalproject.game.PlantsVsZombies;

import javafx.scene.layout.Pane;

public class ZombieHandler {
    public enum ZombieType {ZOMBIE, CONEHEAD_ZOMBIE, SCREEN_DOOR_ZOMBIE, IMP_ZOMBIE};

    public static void sendZombie(Pane pane) {
        Zombie zombie;
        Zombie zombie2;
        Zombie zombie3;
        double startX = 1500;

        zombie = new Zombie(startX, 106 + 123 + GraphicalZombie.ZOMBIE_HEIGHT / 2);
        GraphicalZombie graphicalZombie = new GraphicalZombie(zombie, ZombieType.ZOMBIE);
        pane.getChildren().add(graphicalZombie.getZombieImage());
        pane.getChildren().add(graphicalZombie.getDot());

        zombie2 = new Zombie(startX, 106 + GraphicalZombie.ZOMBIE_HEIGHT / 2);
        GraphicalZombie graphicalZombie2 = new GraphicalZombie(zombie2, ZombieType.CONEHEAD_ZOMBIE);
        pane.getChildren().add(graphicalZombie2.getZombieImage());
        pane.getChildren().add(graphicalZombie2.getDot());

        zombie3 = new Zombie(startX, 106 + 3 * 123 + GraphicalZombie.ZOMBIE_HEIGHT / 2);
        GraphicalZombie graphicalZombie3 = new GraphicalZombie(zombie3, ZombieType.SCREEN_DOOR_ZOMBIE);
        pane.getChildren().add(graphicalZombie3.getZombieImage());
        pane.getChildren().add(graphicalZombie3.getDot());
    }
}
