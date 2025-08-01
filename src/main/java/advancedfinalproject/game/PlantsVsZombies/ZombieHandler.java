package advancedfinalproject.game.PlantsVsZombies;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class ZombieHandler {
    private ArrayList<GraphicalZombie> graphicalZombies;
    private Pane pane;
    private GameState gameState;


    public ZombieHandler(Pane pane, GameState gameState) {
        this.pane = pane;
        this.gameState = gameState;
        graphicalZombies = new ArrayList<GraphicalZombie>();
    }


    public void sendZombie() {
        Zombie zombie;
        Zombie zombie2;
        Zombie zombie3;
        Zombie zombie4, zombie5, zombie6;

        zombie = new Zombie(1);
        gameState.getZombieList().add(zombie);
        GraphicalZombie graphicalZombie = new GraphicalZombie(zombie, Zombie.ZombieType.ZOMBIE);
        graphicalZombies.add(graphicalZombie);
        pane.getChildren().add(graphicalZombie.getZombieImage());
        pane.getChildren().add(graphicalZombie.getDot());

        zombie2 = new Zombie(1, 11);
        gameState.getZombieList().add(zombie2);
        GraphicalZombie graphicalZombie2 = new GraphicalZombie(zombie2, Zombie.ZombieType.CONEHEAD_ZOMBIE);
        graphicalZombies.add(graphicalZombie2);
        pane.getChildren().add(graphicalZombie2.getZombieImage());
        pane.getChildren().add(graphicalZombie2.getDot());

        zombie3 = new Zombie(3);
        gameState.getZombieList().add(zombie3);
        GraphicalZombie graphicalZombie3 = new GraphicalZombie(zombie3, Zombie.ZombieType.SCREEN_DOOR_ZOMBIE);
        graphicalZombies.add(graphicalZombie3);
        pane.getChildren().add(graphicalZombie3.getZombieImage());
        pane.getChildren().add(graphicalZombie3.getDot());

        zombie4 = new Zombie(3, 11);
        gameState.getZombieList().add(zombie4);
        GraphicalZombie graphicalZombie4 = new GraphicalZombie(zombie4, Zombie.ZombieType.SCREEN_DOOR_ZOMBIE);
        graphicalZombies.add(graphicalZombie4);
        pane.getChildren().add(graphicalZombie4.getZombieImage());
        pane.getChildren().add(graphicalZombie4.getDot());

        zombie5 = new Zombie(2);
        gameState.getZombieList().add(zombie5);
        GraphicalZombie graphicalZombie5 = new GraphicalZombie(zombie5, Zombie.ZombieType.SCREEN_DOOR_ZOMBIE);
        graphicalZombies.add(graphicalZombie5);
        pane.getChildren().add(graphicalZombie5.getZombieImage());
        pane.getChildren().add(graphicalZombie5.getDot());
    }

    public ArrayList<GraphicalZombie> getGraphicalZombies() {
        return graphicalZombies;
    }

    public void updateImages() { //baraye inke akse zombie ha hamishe rooye akse giyah biyofte
        for (GraphicalZombie zombie: graphicalZombies) {
            pane.getChildren().remove(zombie.getZombieImage());
            pane.getChildren().remove(zombie.getDot());
            pane.getChildren().add(zombie.getZombieImage());
            pane.getChildren().add(zombie.getDot());
        }
    }
}
