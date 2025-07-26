package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameScreen {
    private final int WIDTH = 1500;
    private final int HEIGHT = 750;
    private final int ROW_COUNT = 5;
    private final int COL_COUNT = 9;
    private Rectangle[][] gridCells = new Rectangle[ROW_COUNT][COL_COUNT];
    private final int ITEM_COUNT = 6;
    private Rectangle[] store = new Rectangle[ITEM_COUNT];
    private Item.ItemName[] items;

    public void start(Stage gameStage, Item.ItemName[] selectedItems) {

        Pane pane = new Pane();
        items = selectedItems;

        gameStage.setTitle("game screen");
        //gameStage.setMaximized(true);
        gameStage.setResizable(false); //size stage ro fix mikone


        Image screen = new Image(getClass().getResource("/Screen Images/day-background.png").toExternalForm());
        ImageView screenImage = new ImageView(screen);
        screenImage.fitWidthProperty().bind(pane.widthProperty());
        screenImage.fitHeightProperty().bind(pane.heightProperty());

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        //GraphicsContext gc = canvas.getGraphicsContext2D();

        pane.getChildren().addAll(screenImage, canvas);
        drawGrid(pane);
        buildStore(pane);

//        ---------------------------------------------------------------
        Label timerLabel = new Label("0 s");
        timerLabel.setLayoutX(800);
        timerLabel.setLayoutY(20);
        timerLabel.setFont(Font.font("Arial", 40));
        pane.getChildren().add(timerLabel);

        final int[] seconds = {0};
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds[0]++;
            timerLabel.setText(seconds[0] + " s");
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
//          ------------------------------------------------------------------

//        canvas.setOnMouseClicked((MouseEvent event) -> {
//            double x = event.getX();
//            double y = event.getY();
//            System.out.println(x + " " + y);
//        });

        ZombieHandler.sendZombie(pane);

        Scene screenScene = new Scene(pane, WIDTH, HEIGHT);
        gameStage.setScene(screenScene);
        gameStage.centerOnScreen();
        gameStage.show();
    }

    private void drawGrid(Pane pane) {
        final int START_X = 370;
        final int START_Y = 106;
        final int CELL_WIDTH = 118;
        final int CELL_HEIGHT = 123;

        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COL_COUNT; col++) {
                Rectangle cell = new Rectangle(START_X + col * CELL_WIDTH, START_Y + row * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
                cell.setFill(Color.TRANSPARENT);
                cell.setStroke(Color.GREEN);
                cell.setStrokeWidth(2);
                gridCells[row][col] = cell;

                int cellRow = row;
                int cellCol = col;

                // vaghti mouse rooye grid harekat mikone
                cell.setOnMouseEntered(e -> {
                    cell.setFill(Color.rgb(0, 255, 0, 0.2)); // sabze shaffaf
                });
                cell.setOnMouseExited(e -> {
                    cell.setFill(Color.TRANSPARENT);
                });

                // vaghti click shod
                cell.setOnMouseClicked(e -> {
                    System.out.println(cellRow + ", " + cellCol + " is clicked");
                });

                pane.getChildren().add(cell);
            }
        }
    }

    private void buildStore(Pane pane) {
        int rectWidth = 130;
        int rectHeight = 90;
        for (int i = 0; i < store.length; i++) {
            store[i] = new Rectangle(20, 10 + (i * 100), rectWidth, rectHeight);
            store[i].setFill(Color.TRANSPARENT);
            store[i].setStroke(Color.BLACK);
            store[i].setStrokeWidth(2);
            int number = Item.getOrder(items[i]);
            Image image = new Image(getClass().getResource("/items/" + number + ".png").toExternalForm());
            ImageView itemImage = new ImageView(image);
            itemImage.setFitWidth(rectWidth);
            itemImage.setFitHeight(rectHeight);
            itemImage.setLayoutX(20);
            itemImage.setLayoutY(10 + (i * 100));
            pane.getChildren().add(itemImage);
            pane.getChildren().add(store[i]);
        }
    }
}