package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Repeater extends PeaPlant {
    public Repeater(int row, int col) {
        super(row, col);
    }

    @Override
    public void act() {
        if (shootTimeline == null) {
            shootTimeline = new Timeline(new KeyFrame(Duration.seconds(DELAY), e -> {
                Bullet bullet1 = new Bullet(type, getRow(), getCol());
                bullets.add(bullet1);

                Timeline shootDelay = new Timeline(new KeyFrame(Duration.millis(300), event -> {
                    Bullet bullet2 = new Bullet(type, getRow(), getCol());
                    bullets.add(bullet2);
                }));
                shootDelay.setCycleCount(1);
                shootDelay.play();
            }));
            shootTimeline.setCycleCount(Timeline.INDEFINITE);
        }
        if (shootTimeline.getStatus() != Animation.Status.RUNNING)
            shootTimeline.play();
    }
}
