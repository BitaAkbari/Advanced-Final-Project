package advancedfinalproject.game.prototype;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Zombie extends Application {
    private ZombieController zombie;
    private boolean isMoving = true;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 400);

        // ایجاد زامبی
        List<Image> frames = loadZombieFrames();
        zombie = new ZombieController(frames);
        root.getChildren().add(zombie.getImageView());

        // دکمه توقف/شروع
        Button toggleButton = new Button("توقف زامبی");
        toggleButton.setLayoutX(350);
        toggleButton.setLayoutY(350);
        toggleButton.setOnAction(e -> {
            isMoving = !isMoving;
            toggleButton.setText(isMoving ? "توقف زامبی" : "شروع حرکت");
        });
        root.getChildren().add(toggleButton);

        // انیمیشن زامبی
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 100_000_000 && isMoving) { // هر 100 میلی‌ثانیه
                    zombie.update();
                    lastUpdate = now;
                }
            }
        };
        timer.start();

        primaryStage.setTitle("زامبی متحرک");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // بارگذاری فریم‌های زامبی
    private List<Image> loadZombieFrames() {
        List<Image> frames = new ArrayList<>();
        try {
            for (int i = 1; i <=50; i++) { //  50 فریم
                frames.add(new Image(getClass().getResourceAsStream(
                        "/resources/zombie/frame" + i + ".png"
                )));
            }
        } catch (Exception e) {
            System.err.println("خطا در بارگذاری تصاویر زامبی!");
            e.printStackTrace();
        }
        return frames;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
