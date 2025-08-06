package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GraveBuster extends Plant {
    private GameState state;
    public GraveBuster(GameState state, int row, int col) {
        super(PlantName.GRAVE_BUSTER, row, col);
        this.state = state;
        hp = 4;
    }

    public void act(){
        Timeline removeGrave = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            state.getField()[getRow()][getCol()].setCellContent(Cell.Content.BLANK);
            hp = 0;
        }));
        removeGrave.setCycleCount(1);
        removeGrave.play();
    }
}
