package advancedfinalproject.game.PlantsVsZombies;


import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameScreen {
    public static final int WIDTH = 1500;
    public static final int HEIGHT = 750;

    public final int ROW_COUNT = GameState.ROW_COUNT;
    public final int COL_COUNT = GameState.COL_COUNT;

    public static final int GRID_START_X = 370;
    public static final int GRID_START_Y = 106;
    public static final int CELL_WIDTH = 118;
    public static final int CELL_HEIGHT = 123;

    private class Shovel {
        private Rectangle shovelBtn;
        private ImageView shovelImage;
        private boolean isSelected;

        public Shovel(Pane pane) {
            int width = 80, height = 50;
            int startX = 400, startY = 10;

            Image image = new Image(getClass().getResource("/buttons/shovelBtn.png").toExternalForm());
            shovelImage = new ImageView(image);
            shovelImage.setLayoutX(startX);
            shovelImage.setLayoutY(startY);
            shovelImage.setFitWidth(width);
            shovelImage.setFitHeight(height);
            pane.getChildren().add(shovelImage);

            shovelBtn = new Rectangle(startX, startY, width, height);
            shovelBtn.setFill(Color.TRANSPARENT);
            pane.getChildren().add(shovelBtn);

            isSelected = false;
            shovelBtn.setOnMouseClicked(e -> {
                if (gameState.getLastChoice() != -1) {
                    store[gameState.getLastChoice()].setStroke(Color.BLACK);
                    gameState.setLastChoice(-1);
                }
                changeSelection();
            });
        }

        public boolean getIsSelected() {
            return isSelected;
        }

        public void changeSelection() {
            isSelected = !isSelected;
            if (isSelected) {
                shovelBtn.setStroke(Color.RED);
            }
            else {
                shovelBtn.setStroke(Color.BLACK);
            }
        }
    }

    private Rectangle[][] gridCells = new Rectangle[ROW_COUNT][COL_COUNT];
    private final int ITEM_COUNT = GameState.ITEM_COUNT;
    private Rectangle[] store = new Rectangle[ITEM_COUNT];
    private GameState gameState;
    Label point;
    GameHandler gameHandler;
    static Label timerLabel;
    Shovel shovel;

    public void start(Stage gameStage, Item.ItemName[] selectedItems) {

        Pane pane = new Pane();
        gameState = new GameState(selectedItems);

        gameStage.setTitle("game screen");
        //gameStage.setMaximized(true);
        gameStage.setResizable(false); //size stage ro fix mikone


        Image screen = new Image(getClass().getResource("/Screen Images/day-background.png").toExternalForm());
        ImageView screenImage = new ImageView(screen);
        screenImage.fitWidthProperty().bind(pane.widthProperty());
        screenImage.fitHeightProperty().bind(pane.heightProperty());


        pane.getChildren().addAll(screenImage);
        drawGrid(pane);
        buildStore(pane);

//        --------------------------------------------------------------------

        point = new Label("1000");
        point.setLayoutX(1000);
        point.setLayoutY(10);
        point.setFont(Font.font("Arial", 40));
        pane.getChildren().add(point);
//        ---------------------------------------------------------------
        timerLabel = new Label("0 s");
        timerLabel.setLayoutX(800);
        timerLabel.setLayoutY(20);
        timerLabel.setFont(Font.font("Arial", 40));
        pane.getChildren().add(timerLabel);

//          ------------------------------------------------------------------

        shovel = new Shovel(pane);

        gameHandler = new GameHandler(pane, gameState);


        Scene screenScene = new Scene(pane, WIDTH, HEIGHT);
        gameStage.setScene(screenScene);
        gameStage.centerOnScreen();
        gameStage.show();
    }

    private void drawGrid(Pane pane) {
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COL_COUNT; col++) {
                Rectangle cell = new Rectangle(GRID_START_X + col * CELL_WIDTH, GRID_START_Y + row * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
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
                    //System.out.println(cellRow + ", " + cellCol + " is clicked");
                    if (gameState.buyAndPlant(cellRow, cellCol)) {
                        store[gameState.getLastChoice()].setStroke(Color.BLACK);
                        gameState.setLastChoice(-1);
                        point.setText(gameState.getSunPoint() + "");
                        gameHandler.getPlantHandler().addPlant(cellRow, cellCol);
                        gameHandler.getZombieHandler().updateImages();
                    }
                    else if (gameState.getField()[cellRow][cellCol].getPlant() != null) {
                        if (shovel.isSelected) {
                            gameState.getField()[cellRow][cellCol].removePlant();
                            gameHandler.getPlantHandler().deletePlant(cellRow, cellCol);
                            shovel.changeSelection();
                        }
                    }
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

            int number = gameState.getStore()[i].getName().ordinal();
            Image image = new Image(getClass().getResource("/items/" + number + ".png").toExternalForm());
            ImageView itemImage = new ImageView(image);
            itemImage.setFitWidth(rectWidth);
            itemImage.setFitHeight(rectHeight);
            itemImage.setLayoutX(20);
            itemImage.setLayoutY(10 + (i * 100));
            pane.getChildren().add(itemImage);
            pane.getChildren().add(store[i]);

            final int index = i;
            store[i].setOnMouseClicked(e -> {
                if (gameState.selectItem(index)) {
                    if (gameState.getLastChoice() != -1)
                        store[gameState.getLastChoice()].setStroke(Color.BLACK);
                    store[index].setStroke(Color.RED);
                    gameState.setLastChoice(index);
                    if (shovel.isSelected)
                        shovel.changeSelection();
                }
                else {
                    store[index].setStroke(Color.BLACK);
                    gameState.setLastChoice(-1);
                }
            });
        }
    }

    public static void setTimerLabel(String newTime) {
        timerLabel.setText(newTime);
    }
}