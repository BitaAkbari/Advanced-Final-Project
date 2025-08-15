package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Fan extends Plant {

    private GameState gameState;

    public Fan(GameState gameState, int row, int col) {
        super(PlantName.FAN, row, col);
        hp = 4;
        this.gameState = gameState;
    }

    public void act() {
        for (int i = 0; i < GameState.ROW_COUNT; i++)
            for (int j = 0; j < GameState.COL_COUNT; j++) {
                Cell cell = gameState.getField()[i][j];
                if (cell.getHasFog()) {
                    cell.setFanEffect(true);
                    cell.removeFogByFan();
                }
            }

        Timeline die = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            hp = 0;
        }));
        die.setCycleCount(1);
        die.play();
    }
}
