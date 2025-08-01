package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Jalapeno extends BombPlant {

    public Jalapeno(GameState gameState, int row, int col) {
        super(gameState, row, col);
    }

    public void act() {
        Timeline explode = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            for (Zombie zombie: gameState.getZombieList()) {
                if (zombie.getRow() == getRow()) {
                    zombie.burst();
                }
            }
            hp = 0;
        }));

        explode.setCycleCount(1);
        explode.play();
    }
}
