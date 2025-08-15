package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class GraphicalPlant {
    private Plant plant;
    private boolean isAnimation;
    private Timeline moveTimeline;
    private Timeline updateNut;
    private int nutState1, nutState2;
    private int delay;
    private int frameCount;
    private int frameNo;
    private Image[] frames;
    private ImageView plantImage;
    private int plantWidth = GameScreen.CELL_WIDTH;
    private int plantHeight = GameScreen.CELL_HEIGHT - 20;
    private double x;
    private double y;

    public GraphicalPlant(Plant basicPlant) {
        plant = basicPlant;

        x = GameScreen.GRID_START_X + plant.getCol() * GameScreen.CELL_WIDTH;
        y = GameScreen.GRID_START_Y + plant.getRow() * GameScreen.CELL_HEIGHT;


        String folderName = "/plants/";
        String imageName = "";

        switch (plant.getType()) {
            case SUNFLOWER:
                frameCount = 31;
                folderName += "sunflower/";
                delay = 50;
                isAnimation = true;
                break;
            case PEASHOOTER:
                frameCount = 24;
                folderName += "peashooter/";
                delay = 100;
                plantWidth -= 50;
                plantHeight -= 20;
                x += 25;
                y += 10;
                isAnimation = true;
                break;
            case REPEATER:
                frameCount = 28;
                folderName += "repeater/";
                delay = 50;
                plantHeight += 10;
                y -= 10;
                isAnimation = true;
                break;
            case SNOW_PEA:
                frameCount = 15;//11
                folderName += "snow pea/";
                delay = 100;//150
                //plantWidth -= 50;
                plantHeight += 30;
                //x += 20;
                y -= 10;
                isAnimation = true;
                break;
            case WALL_NUT:
                frameCount = 3;
                folderName += "wall nut/";
                plantWidth -= 20;
                plantHeight -= 20;
                x += 10;
                y += 20;
                isAnimation = false;
                nutState1 = 7;
                nutState2 = 4;
                break;
            case TALL_NUT:
                frameCount = 3;
                folderName += "tall nut/";
                plantWidth -= 10;
                x += 5;
                y += 5;
                isAnimation = false;
                nutState1 = 12;
                nutState2 = 6;
                break;
            case CHERRY_BOMB:
                frameCount = 13;
                folderName += "cherry/";
                delay = 100;//90 bood
                plantWidth -= 10;
                plantHeight -= 10;
                x += 10;
                y += 10;
                isAnimation = true;
                break;
            case JALAPENO:
                frameCount = 8;
                folderName += "jalapeno/";
                delay = 144;//90 bood
                plantWidth -= 20;
                plantHeight -= 20;
                x += 20;
                y += 20;
                isAnimation = true;
                break;
            case GRAVE_BUSTER:
                frameCount = 1;
                folderName += "night plants/";
                imageName = "grave buster.png";
                isAnimation = false;
                break;
            case COFFEE_BEAN:
                frameCount = 17;
                folderName += "coffee bean/";
                delay = 100;
                plantWidth -= 60;
                plantHeight -= 20;
                x += 25;
                isAnimation = true;
                break;
            case HYPNO_SHROOM:
                frameCount = 1;
                folderName += "night plants/";
                imageName = "hypno shroom.png";
                isAnimation = false;
                break;
            case PUFF_SHROOM:
                frameCount = 1;
                folderName += "night plants/";
                imageName = "puff shroom.png";
                plantWidth -= 70;
                plantHeight -= 60;
                x += 35;
                y += 40;
                isAnimation = false;
                break;
            case SCAREDY_SHROOM:
                frameCount = 1;
                folderName += "night plants/";
                imageName = "scaredy shroom.png";
                plantWidth -= 50;
                plantHeight -= 30;
                x += 20;
                y += 25;
                isAnimation = false;
                break;
            case DOOM_SHROOM:
                frameCount = 1;
                folderName += "night plants/";
                imageName = "doom shroom.png";
                isAnimation = false;
                break;
            case ICE_SHROOM:
                frameCount = 1;
                folderName += "night plants/";
                imageName = "ice shroom.png";
                isAnimation = false;
                break;
            case LANTERN:
                frameCount = 1;
                folderName += "night plants/";
                imageName = "lantern.png";
                isAnimation = false;
                break;
            case FAN:
                frameCount = 23;
                folderName += "fan/";
                delay = 100;
                plantWidth += 20;
                plantHeight += 20;
                x -= 10;
               // y += 25;
                isAnimation = true;
                break;
        }

        frames = new Image[frameCount];

        if (isAnimation || plant instanceof NutPlant) {
            for (int i = 0; i < frameCount; i++) {
                String path = getClass().getResource(folderName + i + ".png").toExternalForm();
                frames[i] = new Image(path);
            }
        }
        else {
            frames[0] = new Image(getClass().getResource(folderName + imageName).toExternalForm());
        }

        plantImage = new ImageView(frames[0]);
        plantImage.setFitWidth(plantWidth);
        plantImage.setFitHeight(plantHeight);
        plantImage.setLayoutX(x);
        plantImage.setLayoutY(y);
        plantImage.setMouseTransparent(true); // ba'es mishe ke giyah jeloye mouse ro nagire

        if (isAnimation) {
            frameNo = 0;
            moveTimeline = new Timeline(new KeyFrame(Duration.millis(delay), e -> {
                frameNo = (frameNo + 1) % frameCount;
                plantImage.setImage(frames[frameNo]);
            }));

            moveTimeline.setCycleCount(Timeline.INDEFINITE);
            moveTimeline.play();
        }
        else if (plant instanceof NutPlant) {
            updateNut = new Timeline(new KeyFrame(Duration.millis(200), e -> {
                if (plant.getHp() <= nutState2)
                    plantImage.setImage(frames[2]);
                else if (plant.getHp() <= nutState1)
                    plantImage.setImage(frames[1]);
            }));

            updateNut.setCycleCount(Timeline.INDEFINITE);
            updateNut.play();
        }
    }

    public ImageView getPlantImage() {
        return plantImage;
    }

    public boolean isAnimation() {
        return isAnimation;
    }

    public int getFrameNo() {
        return frameNo;
    }

    public void setFrameNo(int num) {
        frameNo = num;
    }

    public void stop() {
        if (moveTimeline != null)
            moveTimeline.stop();
    }

    public void start() {
        if (moveTimeline != null)
            moveTimeline.play();
    }
}
