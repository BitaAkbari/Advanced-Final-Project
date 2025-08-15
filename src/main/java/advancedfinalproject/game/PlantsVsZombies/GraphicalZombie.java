package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class GraphicalZombie {

    private Zombie zombie;
    private int frameCount;
    private int frameNo;
    private Image[] frames;
    private ImageView zombieImage;
    public static final double ZOMBIE_WIDTH = 90, ZOMBIE_HEIGHT = 120;
    private double zombieX, zombieY;
//    private Circle dot;/////////////
    private Timeline timeline;
    private int delay;

    public GraphicalZombie(Zombie z) {

        zombie = z;

        zombieX = centerX_To_X();
        zombieY = centerY_To_Y();

        String folderName = "";
        switch (zombie.getType()) {
            case ZOMBIE:
                frameCount = 7;
                folderName = "/simple zombie/";
                delay = 150;
                break;
            case CONEHEAD_ZOMBIE:
                frameCount = 7;
                folderName = "/conehead zombie/";
                delay = 150;
                break;
            case SCREEN_DOOR_ZOMBIE:
                frameCount = 7;
                folderName = "/screen door zombie/";
                delay = 150;
                break;
            case IMP_ZOMBIE:
                frameCount = 28;
                folderName = "/imp zombie/";
                delay = 80;
                break;
        }

        frames = new Image[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = getClass().getResource(folderName + i + ".png").toExternalForm();
            frames[i] = new Image(path);
        }

        zombieImage = new ImageView(frames[0]);
        zombieImage.setFitWidth(ZOMBIE_WIDTH);
        zombieImage.setFitHeight(ZOMBIE_HEIGHT);
        zombieImage.setLayoutX(zombieX);
        zombieImage.setLayoutY(zombieY);
        zombieImage.setMouseTransparent(true);

//        dot = new Circle(zombie.getCenterX(), zombie.getCenterY(), 5);//////////////////////
//        dot.setFill(Color.RED);///////////////

        frameNo = 0;
        timeline = new Timeline(new KeyFrame(Duration.millis(delay), e -> {
            frameNo = (frameNo + 1) % frameCount;
            zombieImage.setImage(frames[frameNo]);
            zombieX = centerX_To_X();
            zombieX -= (zombie.getCurrSpeed() * GameScreen.CELL_WIDTH * delay / 1000);
            if (zombieX > GameScreen.WIDTH + zombieImage.getFitWidth() / 2)
                zombie.setHp(0);
            zombie.setCenterX(xToCenterX());
            zombieImage.setLayoutX(zombieX);
//            dot.setCenterX(zombie.getCenterX());/////////////
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public Zombie getZombie() {
        return zombie;
    }

    public ImageView getZombieImage() {
        return zombieImage;
    }

//    public Circle getDot() {/// ////////////////////////
//        return dot;
//    }

    public void stop() {
        timeline.pause();
    }

    public void start() {
        if (timeline.getStatus() != Timeline.Status.RUNNING)
            timeline.play();
    }

    private double xToCenterX() {
        return zombieX + ZOMBIE_WIDTH / 2;
    }

    private double centerX_To_X() {
        return zombie.getCenterX() - ZOMBIE_WIDTH / 2;
    }

    private double centerY_To_Y() {
        return zombie.getCenterY() - ZOMBIE_HEIGHT / 2;
    }

    public void hypnosis() {
        zombie.hypnotize();
        zombieImage.setScaleX(-1);
    }

}
