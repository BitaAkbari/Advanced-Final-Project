package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class CherryBomb extends BombPlant {
    public CherryBomb(GameState gameState, int row, int col) {
        super(gameState, PlantName.CHERRY_BOMB, row, col);
    }

    public void act() {
        Timeline explode = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            for (Zombie zombie: gameState.getZombieList()) {
                if (Math.abs(zombie.getRow() - getRow()) <= 1 &&
                        Math.abs(zombie.getCol() - getCol()) <= 1 &&
                        zombie.getCenterX() >= GameScreen.GRID_START_X) {
                    zombie.burst();
                }
            }
            hp = 0;
        }));

        explode.setCycleCount(1);
        explode.play();
    }
}
