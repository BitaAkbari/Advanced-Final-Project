package game.prototype.AdvancedFinalProject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MultiBallReturnApp extends Application {

    private static final int BALL_COUNT = 5;
    private static final int BALL_RADIUS = 15;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private double[] dx = new double[BALL_COUNT];
    private boolean[] isRunning = new boolean[BALL_COUNT];
    private boolean[] isReturning = new boolean[BALL_COUNT];
    private double startX = 100;
    private double endX = 500;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Circle[] balls = new Circle[BALL_COUNT];
        AnimationTimer[] timers = new AnimationTimer[BALL_COUNT];

        for (int i = 0; i < BALL_COUNT; i++) {
            Circle ball = new Circle(BALL_RADIUS, Color.hsb(i * 60, 0.9, 0.9));
            double y = 50 + i * 60;
            ball.setCenterX(startX);
            ball.setCenterY(y);
            balls[i] = ball;

            dx[i] = 3;
            int index = i;

            Button btn = new Button("شروع " + (i + 1));
            btn.setLayoutX(20);
            btn.setLayoutY(y - 15);

            timers[i] = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (!isRunning[index]) return;

                    Circle b = balls[index];
                    double x = b.getCenterX();

                    if (!isReturning[index]) {
                        // مرحله رفت
                        if (x < endX) {
                            b.setCenterX(x + dx[index]);
                        } else {
                            isReturning[index] = true;
                        }
                    } else {
                        // مرحله برگشت
                        if (x > startX) {
                            b.setCenterX(x - dx[index]);
                        } else {
                            isRunning[index] = false;
                            isReturning[index] = false;
                            this.stop();
                        }
                    }
                }
            };

            btn.setOnAction(e -> {
                if (!isRunning[index]) {
                    balls[index].setCenterX(startX);
                    isRunning[index] = true;
                    isReturning[index] = false;
                    timers[index].start();
                }
            });

            root.getChildren().addAll(ball, btn);
        }

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("حرکت رفت و برگشتی تا برگشت کامل");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

