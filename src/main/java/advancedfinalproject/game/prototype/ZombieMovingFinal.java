package advancedfinalproject.game.prototype;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ZombieMovingFinal extends Application {

    private int frameCount = 7;
    private int frameNo = 0;
    private Image[] frames = new Image[frameCount];
    private int stageWidth = 800, stageHeight = 600;
    private double zombieX, zombieY = stageHeight / 3;
    private final double SPEED = 5;
    private double currSpeed = SPEED;

    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();
        pane.setPrefSize(stageWidth, stageHeight);

        for (int i = 0; i < frameCount; i++) {
            String path = getClass().getResource("/simple zombie/" + (i + 1) + ".png").toExternalForm();
            frames[i] = new Image(path);
        }

        ImageView zombieImage = new ImageView(frames[0]);
        zombieImage.setFitWidth(80);
        zombieImage.setFitHeight(110);
        zombieX = pane.getPrefWidth();
        zombieImage.setLayoutX(zombieX);
        zombieImage.setLayoutY(zombieY);

        pane.getChildren().add(zombieImage);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            frameNo = (frameNo + 1) % frameCount;
            zombieImage.setImage(frames[frameNo]);
            zombieX -= currSpeed;
            if (zombieX < -zombieImage.getFitWidth())
                zombieX = pane.getPrefWidth();
            zombieImage.setLayoutX(zombieX);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Button stopBtn = new Button("stop");
        stopBtn.setLayoutX(stageWidth / 2 - 100);
        stopBtn.setLayoutY(stageHeight - 100);
        pane.getChildren().add(stopBtn);

        Button startBtn = new Button("start");
        startBtn.setLayoutX(stageWidth / 2 + 100);
        startBtn.setLayoutY(stageHeight - 100);
        pane.getChildren().add(startBtn);

        Button eatingBtn = new Button("eating");
        eatingBtn.setLayoutX(stageWidth / 2);
        eatingBtn.setLayoutY(stageHeight - 100);
        pane.getChildren().add(eatingBtn);

        stopBtn.setOnMouseClicked(e -> {
            if (timeline.getStatus() == Timeline.Status.RUNNING)
                timeline.pause();
        });
        startBtn.setOnMouseClicked(e -> {
            currSpeed = SPEED;
            if (timeline.getStatus() != Timeline.Status.RUNNING)
                timeline.play();
        });
        eatingBtn.setOnMouseClicked(e -> {
            if (timeline.getStatus() != Timeline.Status.RUNNING)
                timeline.play();
            currSpeed = 0;
        });

        Scene scene = new Scene(pane, stageWidth, stageHeight);
        stage.setScene(scene);
        stage.setTitle("zombie moving");
        stage.show();
    }
}
