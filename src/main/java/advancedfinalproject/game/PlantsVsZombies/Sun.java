package advancedfinalproject.game.PlantsVsZombies;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Sun {
    public static final int POINT = 25;
    private double x , y;
    private double realY;
    private double speed;
    private final int LIFE_TIME = 5;
    private boolean isAlive;
    private Timeline time;

    public Sun() {
        isAlive = true;
        Random generator = new Random();
        x = generator.nextDouble() * 1000 + 400;
        realY = generator.nextDouble() * 580 + 120;
        y = -100;
        speed = 0.5;
    }
    public Sun(double x , double y) {
        isAlive = true;
        this.x = x;
        this.y = y;
        realY = y;
        speed = 0;
        startTimeline();
    }
    public void startTimeline() {
        time = new Timeline(new KeyFrame(Duration.seconds(LIFE_TIME),e -> {
            isAlive = false;
        }));
        time.setCycleCount(1);
        time.play();
    }


     public double getX() {
        return x;
     }

     public void setX(double x) {
        this.x = x;
     }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRealY() {
        return realY;
    }

    public void setRealY(double realY) {
        this.realY = realY;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public double getSpeed() {
        return speed;
    }

}
