package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class DoomShroom extends Shroom {
    private GameState gameState;

    public DoomShroom(GameState gameState, int row, int col) {
        super(PlantName.DOOM_SHROOM, row, col);
        this.gameState = gameState;
    }

    public void act() {
        Timeline explode = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            for (Zombie zombie: gameState.getZombieList()) {
                if (Math.abs(zombie.getRow() - getRow()) <= 2 &&
                        Math.abs(zombie.getCol() - getCol()) <= 2 &&
                        zombie.getCenterX() >= GameScreen.GRID_START_X) {
                    zombie.burst();
                }
            }
            gameState.getField()[getRow()][getCol()].setCellContent(Cell.Content.BLOCKED);
            gameState.getField()[getRow()][getCol()].unblock();
            hp = 0;
        }));
        explode.setCycleCount(1);
        explode.play();

    }
}
