package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Sunflower extends Plant {
    private Timeline sunGenerator;
    private final int DELAY = 6;


    public Sunflower(int row, int col) {
        super(PlantName.SUNFLOWER, row, col);
        hp = 4;
    }

    public void act() {
        sunGenerator = new Timeline(new KeyFrame(Duration.seconds(DELAY), e -> {
            double x = GameScreen.GRID_START_X + getCol() * GameScreen.CELL_WIDTH - 10;
            double y = GameScreen.GRID_START_Y + getRow() * GameScreen.CELL_HEIGHT - 10;

            Sun sun = new Sun(x, y);
            GameHandler.handleSun(sun);
        }));
        sunGenerator.setCycleCount(Timeline.INDEFINITE);
        sunGenerator.play();
    }

    @Override
    public void clear() {
        if (sunGenerator != null) {
            sunGenerator.stop();
            sunGenerator = null;
        }
    }
}
