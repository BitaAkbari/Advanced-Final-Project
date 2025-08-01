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
    private final double BULLET_WIDTH = 20;
    private final double BULLET_HEIGHT = 20;
    private final int DELAY = 100;

    public GraphicalBullet(Bullet b) {
        bullet = b;

        dot = new Circle(bullet.getCenterX(), bullet.getCenterY(), 3);////////

        String imageName;
        if (b.getType() == Bullet.BulletType.NORMAL)
            imageName = "bullet.png";
        else
            imageName = "frozen-bullet.png";

        Image image = new Image(getClass().getResource("/plants/bullets/" + imageName).toExternalForm());

        bulletImage = new ImageView(image);
        bulletImage.setFitWidth(BULLET_WIDTH);
        bulletImage.setFitHeight(BULLET_HEIGHT);
        bulletImage.setLayoutX(centerX_To_X());
        bulletImage.setLayoutY(centerY_To_Y());


        double speed = bullet.getSpeed() * GameScreen.CELL_WIDTH * DELAY / 1000;

        Timeline moving = new Timeline(new KeyFrame(Duration.millis(DELAY), e -> {
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
        return bullet.getCenterX() - BULLET_WIDTH / 2;
    }

    private double centerY_To_Y() {
        return bullet.getCenterY() - BULLET_HEIGHT / 2;
    }
}