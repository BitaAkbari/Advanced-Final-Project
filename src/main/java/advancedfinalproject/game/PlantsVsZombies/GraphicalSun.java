package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class GraphicalSun {
    private Sun sun;
    private ImageView sunImage;
    private String path;
    private final int WIDTH = 100;
    private final int HEIGHT = 100;
    private final int DELAY = 90;
    private Timeline move;

    public GraphicalSun(Sun sun) {
        this.sun = sun;
        path = "/plants/sun.png";
        Image image = new Image(getClass().getResource(path).toExternalForm());
        sunImage = new ImageView(image);
        sunImage.setFitWidth(WIDTH);
        sunImage.setFitHeight(HEIGHT);
        sunImage.setLayoutX(sun.getX());

        if(sun.getSpeed() != 0) {
            sunImage.setLayoutY(-HEIGHT);
            double speed = sun.getSpeed() * GameScreen.CELL_HEIGHT * DELAY / 1000;

            move = new Timeline();
            move.getKeyFrames().add(new KeyFrame(Duration.millis(DELAY), e -> {
                double y = sun.getY() + speed;
                if (y >= sun.getRealY()) {
                    //y = sun.getRealY();
                    move.stop();
                    sun.startTimeline();
                }
                sun.setY(y);
                sunImage.setLayoutY(y);
            }));
            move.setCycleCount(Timeline.INDEFINITE);
            move.play();
        }
        else {
            sunImage.setLayoutY(sun.getY());
        }
    }

    public Sun getSun() {
        return sun;
    }

    public ImageView getSunImage() {
        return sunImage;
    }

    public void stop() {
        move.stop();
        //move.getKeyFrames().clear();
    }
}
