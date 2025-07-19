package advancedfinalproject.game.prototype;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Zombie extends Application {
    private List<Image> zombieFrames;
    private ImageView zombieView;
    private int currentFrame = 0;
    private double zombieX = 1200; // شروع از سمت راست
    private final double ZOMBIE_Y = 200;
    private double speed = 1;
    protected boolean isMoving = true;
    private AnimationTimer timer;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();

        loadZombieFrames();
        setupZombie();
        root.getChildren().add(zombieView);

        ZombieController controller = new ZombieController(this);

        Button btn = new Button("توقف زامبی");
        btn.setLayoutX(350);
        btn.setLayoutY(350);
        btn.setOnAction(e -> {
            controller.toggleMovement();
            btn.setText(isMoving ? "توقف زامبی" : "شروع حرکت");
        });
        root.getChildren().add(btn);

        startAnimation();

        stage.setScene(new Scene(root, 800, 400));
        stage.setTitle("زامبی با حرکت راست به چپ");
        stage.show();
    }

    private void startAnimation() {
        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 150_000_000 && isMoving) {
                    updateZombie();
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    private void loadZombieFrames() {
        zombieFrames = new ArrayList<>();
        try {
            for (int i = 1; i <= 7; i++) {
                String path = "/simple zombie/" + i + ".png";
                Image frame = new Image(getClass().getResourceAsStream(path));
                zombieFrames.add(frame);
            }
        } catch (Exception e) {
            System.err.println("خطا در بارگذاری فریم‌های زامبی!");
            e.printStackTrace();
        }
    }

    private void setupZombie() {
        zombieView = new ImageView(zombieFrames.get(0));
        zombieView.setScaleX(0.2);  // 20% اندازه اصلی در عرض
        zombieView.setScaleY(0.2);  // 20% اندازه اصلی در ارتفاع
        zombieView.setLayoutX(zombieX);
        zombieView.setLayoutY(ZOMBIE_Y);
    }

    private void updateZombie() {
        // به‌روزرسانی فریم انیمیشن
        currentFrame = (currentFrame + 1) % zombieFrames.size();
        zombieView.setImage(zombieFrames.get(currentFrame));

        // حرکت به چپ (کاهش مقدار X)
        zombieX -= speed;
        if (zombieX < -zombieView.getImage().getWidth()) {
            zombieX = 1200; // بازگشت به سمت راست
        }
        zombieView.setLayoutX(zombieX);
    }

    @Override
    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}