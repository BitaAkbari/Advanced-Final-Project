package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;

public class PeaPlant extends Plant {
    protected Bullet.BulletType type;
    protected ArrayList<Bullet> bullets;
    protected Timeline shootTimeline;
    protected final double DELAY = 1.5;

    public PeaPlant(PlantName name, int row, int col) {
        super(name, row, col);
        type = Bullet.BulletType.NORMAL;
        hp = 4;
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
