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
    private final int WIDTH = 30;
    private final int HEIGHT = 30;
    private final int DELAY = 100;

    public GraphicalSun(Sun sun) {
        this.sun = sun;
        path = "/plants/sun.png";
        Image image = new Image(getClass().getResource(path).toExternalForm());
        sunImage = new ImageView(image);
        sunImage.setFitWidth(WIDTH);
        sunImage.setFitHeight(HEIGHT);
        sunImage.setLayoutY(0 - HEIGHT);
        sunImage.setLayoutX(sun.getX());
        if(sun.getSpeed() != 0) {
            double speed = sun.getSpeed() * GameScreen.CELL_HEIGHT * DELAY / 1000;
            Timeline move = new Timeline(new KeyFrame(Duration.millis(DELAY), e -> {
              double y = sun.getY() + speed;
              sun.setY(y);
              sunImage.setLayoutY(y);
              if (y >= sun.getRealY()) {
                  ((Timeline) e.getSource()).stop();
              }
            }));
            move.setCycleCount(Timeline.INDEFINITE);
            move.play();
        }
    }

    public Sun getSun() {
        return sun;
    }

    public ImageView getSunImage() {
        return sunImage;
    }
}
