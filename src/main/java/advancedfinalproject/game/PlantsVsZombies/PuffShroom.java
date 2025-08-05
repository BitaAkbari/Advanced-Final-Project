package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;

public class PuffShroom extends Shroom {

    private Bullet.BulletType type;
    private ArrayList<Bullet> bullets;
    private Timeline shootTimeline;
    private final double DELAY = 1.5;

    public PuffShroom(int row, int col) {
        super(PlantName.PUFF_SHROOM, row, col);
        System.out.println("puff shroom");
        type = Bullet.BulletType.PUFF_SHROOM_BULLET;
        bullets = new ArrayList<>();
    }

    public void act() {
        if (shootTimeline == null) {
            shootTimeline = new Timeline(new KeyFrame(Duration.seconds(DELAY), e -> {
                Bullet bullet = new Bullet(type, getRow(), getCol());
                bullets.add(bullet);
            }));
            shootTimeline.setCycleCount(Timeline.INDEFINITE);
        }
        if (shootTimeline.getStatus() != Animation.Status.RUNNING) {

            Bullet bullet = new Bullet(type, getRow(), getCol());
            bullets.add(bullet);

            shootTimeline.play();
        }
    }

    public void stopAct() {
        if (shootTimeline != null)
            shootTimeline.pause();
    }

    @Override
    public void clear() {
        if (shootTimeline != null) {
            shootTimeline.stop();
            shootTimeline = null;
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}

