package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class GraphicalPlant {
    private Plant plant;
    private Timeline timeline;
    private int frameCount;
    private int frameNo;
    private Image[] frames;
    private ImageView plantImage;
    private int delay;
    private String folderName;

    private final int PLANT_WIDTH = GameScreen.CELL_WIDTH;
    private final int PLANT_HEIGHT = GameScreen.CELL_HEIGHT;
    private double x;
    private double y;

    public GraphicalPlant(Plant basicPlant) {
        plant = basicPlant;

        folderName = "/plants/";
        if (plant.getClass() == Sunflower.class) {
            frameCount = 31;
            folderName += "/sunflower/";
            delay = 50;
        }
        if (plant.getClass() == Peashooter.class) {
            frameCount = 24;
            folderName += "/peashooter/";
            delay = 100;
        }
        if (plant.getClass() == Repeater.class) {
            frameCount = 28;
            folderName += "/repeater/";
            delay = 50;
        }
        if (plant.getClass() == SnowPea.class) {
            frameCount = 11;
            folderName += "/snow pea/";
            delay = 150;
        }
        if (plant.getClass() == CherryBomb.class) {
            frameCount = 7;
            folderName += "/cherry/";
            delay = 90;
        }
        if (plant.getClass() == Jalapeno.class) {
            frameCount = 8;
            folderName += "/jalapeno/";
            delay = 90;
        }

        x = GameScreen.GRID_START_X + plant.getCol() * GameScreen.CELL_WIDTH;
        y = GameScreen.GRID_START_Y + plant.getRow() * GameScreen.CELL_HEIGHT;


        frames = new Image[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = getClass().getResource(folderName + i + ".png").toExternalForm();
            frames[i] = new Image(path);
        }

        plantImage = new ImageView(frames[0]);
        plantImage.setFitWidth(PLANT_WIDTH);
        plantImage.setFitHeight(PLANT_HEIGHT);
        plantImage.setLayoutX(x);
        plantImage.setLayoutY(y);

        frameNo = 0;
        timeline = new Timeline(new KeyFrame(Duration.millis(delay), e -> {
            frameNo = (frameNo + 1) % frameCount;
            plantImage.setImage(frames[frameNo]);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public ImageView getPlantImage() {
        return plantImage;
    }

    public void stop() {
        timeline.pause();
    }

    public void start() {
        timeline.play();
    }
}
