package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class GraphicalBullet {
    private Bullet bullet;
    private Circle dot;//////////////
    private ImageView bulletImage;
    private double bulletWidth;
    private double bulletHeight;
    private final int DELAY = 100;
    private Timeline moving;

    public GraphicalBullet(Bullet b) {
        bullet = b;

        if (bullet.getType() == Bullet.BulletType.PUFF_SHROOM_BULLET ||
            bullet.getType() == Bullet.BulletType.SCARDY_SHROOM_BULLET) {
            bulletWidth = bulletHeight = 15;
        }
        else {
            bulletWidth = bulletHeight = 20;
        }

        dot = new Circle(bullet.getCenterX(), bullet.getCenterY(), 3);////////

        String imageName;
        if (b.getType() == Bullet.BulletType.NORMAL)
            imageName = "bullet.png";
        else if (b.getType() == Bullet.BulletType.FROZEN)
            imageName = "frozen-bullet.png";
        else
            imageName = "shroom-bullet.png";

        Image image = new Image(getClass().getResource("/plants/bullets/" + imageName).toExternalForm());

        bulletImage = new ImageView(image);
        bulletImage.setFitWidth(bulletWidth);
        bulletImage.setFitHeight(bulletHeight);
        bulletImage.setLayoutX(centerX_To_X());
        bulletImage.setLayoutY(centerY_To_Y());


        double speed = bullet.getSpeed() * GameScreen.CELL_WIDTH * DELAY / 1000;

        moving = new Timeline(new KeyFrame(Duration.millis(DELAY), e -> {
            double x = bullet.getCenterX() + speed;
            bullet.setCenterX(x);
            dot.setCenterX(x);//////////////
            bulletImage.setLayoutX(centerX_To_X());
        }));

        moving.setCycleCount(Timeline.INDEFINITE);
        moving.play();
    }

    public Bullet getBullet() {
        return bullet;
    }

    public Circle getDot() {/// ////////
        return dot;
    }

    public ImageView getBulletImage() {
        return bulletImage;
    }

    private double centerX_To_X() {
        return bullet.getCenterX() - bulletWidth / 2;
    }

    private double centerY_To_Y() {
        return bullet.getCenterY() - bulletHeight / 2;
    }

    public void stop() {
        moving.stop();
    }

}