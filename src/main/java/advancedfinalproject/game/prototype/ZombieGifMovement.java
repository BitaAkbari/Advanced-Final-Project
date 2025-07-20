package advancedfinalproject.game.prototype;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ZombieGifMovement extends Application {

    private double x;
    private final double speed = 1;

    @Override
    public void start(Stage primaryStage) {
        Pane gamePane = new Pane();
        gamePane.setPrefSize(800, 400);

        // بارگذاری گیف متحرک زامبی
        Image zombieGif = new Image("D:\\sunflower_transparent.gif");

        ImageView zombie = new ImageView(zombieGif);
        zombie.setFitWidth(80);
        zombie.setFitHeight(110);

        x = gamePane.getPrefWidth(); // شروع از سمت راست صفحه
        zombie.setLayoutX(x);
        zombie.setLayoutY(150);

        gamePane.getChildren().add(zombie);

        Button toggleButton = new Button("توقف زامبی");

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            // حرکت زامبی از راست به چپ
            x -= speed;
            if (x < -zombie.getFitWidth()) {
                x = gamePane.getPrefWidth();
            }
            zombie.setLayoutX(x);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        toggleButton.setOnAction(e -> {
            if (timeline.getStatus() == Timeline.Status.RUNNING) {
                timeline.pause();
                toggleButton.setText("شروع زامبی");
            } else {
                timeline.play();
                toggleButton.setText("توقف زامبی");
            }
        });

        VBox root = new VBox(10, gamePane, toggleButton);
        root.setStyle("-fx-padding: 10; -fx-alignment: center;");

        Scene scene = new Scene(root, 820, 480);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Zombie GIF Movement with Control");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
