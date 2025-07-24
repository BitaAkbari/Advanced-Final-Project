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
    private ZombieHandler.ZombieType zombieType;
    private int frameCount;
    private int frameNo;
    private Image[] frames;
    private ImageView zombieImage;
    public static final double ZOMBIE_WIDTH = 90, ZOMBIE_HEIGHT = 120;
    private Timeline timeline;
    private double zombieX, zombieY;
    private double speed, currSpeed;
    private Circle dot;/////////////


    public GraphicalZombie(Zombie z, ZombieHandler.ZombieType type) {

        zombie = z;
        zombieType = type;

        zombieX = centerX_To_X();
        zombieY = centerY_To_Y();
        currSpeed = speed = zombie.getSpeed();

        String folderName = "";
        switch(zombieType) {
            case ZOMBIE:
                frameCount = 7;
                folderName = "/simple zombie/";
                break;
            case CONEHEAD_ZOMBIE:
                frameCount = 21;
                folderName = "/conehead zombie/";
                break;
            case SCREEN_DOOR_ZOMBIE:
                frameCount = 21;
                folderName = "/screen door zombie/";
                break;
            case IMP_ZOMBIE:
                //frameCount = ;
                //folderName = "/imp zombie/";
                break;
        }

        frames = new Image[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = getClass().getResource(folderName + (i + 1) + ".png").toExternalForm();
            frames[i] = new Image(path);
        }

        zombieImage = new ImageView(frames[0]);
        zombieImage.setFitWidth(ZOMBIE_WIDTH);
        zombieImage.setFitHeight(ZOMBIE_HEIGHT);
        zombieImage.setLayoutX(zombieX);
        zombieImage.setLayoutY(zombieY);

        dot = new Circle(zombieX, zombie.getY(), 5);//////////////////////
        dot.setFill(Color.RED);///////////////

        frameNo = 0;
        timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            frameNo = (frameNo + 1) % frameCount;
            zombieImage.setImage(frames[frameNo]);
            zombieX = centerX_To_X();
            zombieX -= currSpeed;
            if (zombieX < -zombieImage.getFitWidth())
                zombieX = 1500;
            zombie.setX(xToCenterX());
            zombieImage.setLayoutX(zombieX);
            dot.setCenterX(zombie.getX());/////////////
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public ImageView getZombieImage() {
        return zombieImage;
    }

    public Circle getDot() {/// ////////////////////////
        return dot;
    }

    public void stop() {
        timeline.pause();
    }

    public void start() {
        timeline.play();
    }

    private double xToCenterX() {
        return zombieX + ZOMBIE_WIDTH / 2;
    }

    private double centerX_To_X() {
        return zombie.getX() - ZOMBIE_WIDTH / 2;
    }

    private double centerY_To_Y() {
        return zombie.getY() - ZOMBIE_HEIGHT / 2;
    }


}
