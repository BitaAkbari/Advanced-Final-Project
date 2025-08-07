package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class CoffeeBean extends Plant {

    Shroom underShroom;

    public CoffeeBean(int row, int col) {
        super(PlantName.COFFEE_BEAN, row, col);
        hp = 4;
    }

    public void act() {

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> {
            underShroom.setSleeping(false);
            hp = 0;
        }));
        timeline.setCycleCount(1);
        timeline.play();

    }

    public void setUnderPlant(Plant plant) {
        underShroom = (Shroom) plant;
    }
}
