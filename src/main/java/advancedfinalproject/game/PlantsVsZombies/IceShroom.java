package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class IceShroom extends Shroom {

    public IceShroom(int row, int col) {
        super(PlantName.ICE_SHROOM, row, col);
    }

    public void act() {

        Timeline freeze = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            for (GraphicalZombie zombie: ZombieHandler.getGraphicalZombies()) {
                zombie.stop();
            }
            hp = 0;
        }),
        new KeyFrame(Duration.seconds(4), e -> {
            for (GraphicalZombie zombie: ZombieHandler.getGraphicalZombies()) {
                zombie.start();
            }
        }));
        freeze.setCycleCount(1);
        freeze.play();

    }
}
