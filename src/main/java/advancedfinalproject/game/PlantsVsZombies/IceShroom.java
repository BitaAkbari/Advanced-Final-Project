package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class IceShroom extends Shroom {
    private Timeline freeze;

    public IceShroom(int row, int col) {
        super(PlantName.ICE_SHROOM, row, col);
    }

    public void act() {

        freeze = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            for (GraphicalZombie zombie: ZombieHandler.getGraphicalZombies()) {
                zombie.stop();
            }
            hp = 0;
        }),
        new KeyFrame(Duration.seconds(4), e -> {
            if (!GameHandler.isGameStopped())
                for (GraphicalZombie zombie: ZombieHandler.getGraphicalZombies()) {
                    zombie.start();
                }
        }));
        freeze.setCycleCount(1);
        freeze.play();

    }

    @Override
    public void clear() {
        if (freeze != null) {
            freeze.stop();
            freeze = null;
        }
    }
}
