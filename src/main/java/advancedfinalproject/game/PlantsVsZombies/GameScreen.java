package advancedfinalproject.game.PlantsVsZombies;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;

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
    private GameState.GameCode gameCode;
    private GameState gameState;
    static Label point;
    GameHandler gameHandler;
    static Label timerLabel;
    Shovel shovel;
    private Stage gameStage;
    private Item.ItemName[] items;


    public void start(Stage gameStage, GameState.GameCode code, Item.ItemName[] selectedItems) {

        this.gameStage = gameStage;
        items = selectedItems;
        Pane pane = new Pane();
        gameCode = code;
        gameState = new GameState(gameCode, selectedItems);

        gameStage.setTitle("game screen");
        //gameStage.setMaximized(true);
        gameStage.setResizable(false); //size stage ro fix mikone


        Image screen = new Image(getClass().getResource("/Screen Images/day-background.png").toExternalForm());
        ImageView screenImage = new ImageView(screen);
        screenImage.fitWidthProperty().bind(pane.widthProperty());
        screenImage.fitHeightProperty().bind(pane.heightProperty());


        addScreenImage(pane);
        drawGrid(pane);
        buildStore(pane);
        drawMenuBtn(pane);

//        --------------------------------------------------------------------

        Image pointImage = new Image(getClass().getResource("/Screen Images/point label.png").toExternalForm());
        ImageView pointLabel = new ImageView(pointImage);
        pointLabel.setFitWidth(150);
        pointLabel.setFitHeight(50);
        pointLabel.setLayoutX(250);
        pointLabel.setLayoutY(10);
        pane.getChildren().add(pointLabel);

        point = new Label("" + gameState.getSunPoint());
        point.setLayoutX(300);
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

        gameHandler = new GameHandler(pane, gameState, gameStage);


        Scene screenScene = new Scene(pane, WIDTH, HEIGHT);
        gameStage.setScene(screenScene);
        gameStage.centerOnScreen();
        gameStage.show();
    }

    private void addScreenImage(Pane pane) {
        String imageName;
        if (gameCode == GameState.GameCode.DAY)
            imageName = "day-background.png";
        else
            imageName = "night-background.png";

        Image screen = new Image(getClass().getResource("/Screen Images/" + imageName).toExternalForm());
        ImageView screenImage = new ImageView(screen);
        screenImage.fitWidthProperty().bind(pane.widthProperty());
        screenImage.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(screenImage);
    }

    private void drawGrid(Pane pane) {
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COL_COUNT; col++) {
                Rectangle cell = new Rectangle(GRID_START_X + col * CELL_WIDTH, GRID_START_Y + row * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
                cell.setFill(Color.TRANSPARENT);
                if (gameCode == GameState.GameCode.DAY)
                    cell.setStroke(Color.GREEN);
                else
                    cell.setStroke(Color.rgb(10, 50, 30));
                cell.setStrokeWidth(2);
                gridCells[row][col] = cell;

                int cellRow = row;
                int cellCol = col;

                // vaghti mouse rooye grid harekat mikone
                cell.setOnMouseEntered(e -> {
                    if (gameCode == GameState.GameCode.DAY)
                        cell.setFill(Color.rgb(0, 255, 0, 0.2)); // sabze shaffaf
                    else
                        cell.setFill(Color.rgb(10, 50, 30, 0.5));
                });
                cell.setOnMouseExited(e -> {
                    cell.setFill(Color.TRANSPARENT);
                });

                // vaghti click shod
                cell.setOnMouseClicked(e -> {
                    //System.out.println(cellRow + ", " + cellCol + " is clicked");
                    if (gameState.buyAndPlant(cellRow, cellCol)) {
                        store[gameState.getLastChoice()].setStroke(Color.BLACK);
                        store[gameState.getLastChoice()].setFill(Color.rgb(64, 64, 64, 0.7));
                        int rechargeTime = gameState.getStore()[gameState.getLastChoice()].getStdRechargeTime();
                        int index = gameState.getLastChoice();
                        Timeline recharge = new Timeline(new KeyFrame(Duration.seconds(rechargeTime), ev -> {
                            store[index].setFill(Color.TRANSPARENT);
                        }));
                        recharge.setCycleCount(1);
                        recharge.play();
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
        int startX = 60;
        for (int i = 0; i < store.length; i++) {
            store[i] = new Rectangle(startX, 10 + (i * 100), rectWidth, rectHeight);
            store[i].setFill(Color.TRANSPARENT);
            store[i].setStroke(Color.BLACK);
            store[i].setStrokeWidth(2);

            int number = gameState.getStore()[i].getName().ordinal();
            Image image = new Image(getClass().getResource("/items/" + number + ".png").toExternalForm());
            ImageView itemImage = new ImageView(image);
            itemImage.setFitWidth(rectWidth);
            itemImage.setFitHeight(rectHeight);
            itemImage.setLayoutX(startX);
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

    private void drawMenuBtn (Pane pane) {
        int width = 300;
        int height = 100;
        int x = 600;
        int y = 200;

        Image menuImage = new Image(getClass().getResource("/buttons/menu button.png").toExternalForm());
        ImageView menuView = new ImageView(menuImage);
        menuView.setFitWidth(150);
        menuView.setFitHeight(50);
        menuView.setLayoutX(1280);
        menuView.setLayoutY(10);
        Rectangle menuGameBtn = new Rectangle(1280 , 10 , 150 , 50);
        menuGameBtn.setStroke(Color.BLACK);
        menuGameBtn.setFill(Color.TRANSPARENT);
        pane.getChildren().addAll(menuView , menuGameBtn);

        menuGameBtn.setOnMouseClicked(e -> {
            gameHandler.stopAllTimelines();
            Image bgMenuImage = new Image(getClass().getResource("/buttons/menu button1.png").toExternalForm());
            ImageView bgMenuView = new ImageView(bgMenuImage);
            bgMenuView.setFitWidth(500);
            bgMenuView.setFitHeight(600);
            bgMenuView.setLayoutX(500);
            bgMenuView.setLayoutY(75);
            pane.getChildren().add(bgMenuView);

            Image mainMenuImage = new Image(getClass().getResource("/buttons/main menu.png").toExternalForm());
            ImageView mainMenuView = new ImageView(mainMenuImage);
            mainMenuView.setFitWidth(width);
            mainMenuView.setFitHeight(height);
            mainMenuView.setLayoutX(x);
            mainMenuView.setLayoutY(y);
            Rectangle mainMenuBtn = new Rectangle(x , y , width , height);
            mainMenuBtn.setStroke(Color.BLACK);
            mainMenuBtn.setFill(Color.TRANSPARENT);
            pane.getChildren().addAll(mainMenuView, mainMenuBtn);
            mainMenuBtn.setOnMouseClicked(event -> {
                //pane.getChildren().remove(bgMenuView);
                new Menu(gameStage);
            });
            mainMenuBtn.setOnMouseEntered(event -> {
                mainMenuBtn.setFill(Color.rgb(200, 200, 200, 0.1));
            });
            mainMenuBtn.setOnMouseExited(event -> {
                mainMenuBtn.setFill(Color.TRANSPARENT);
            });

            Image restartImage = new Image(getClass().getResource("/buttons/restart level.png").toExternalForm());
            ImageView restartView = new ImageView(restartImage);
            restartView.setFitWidth(width);
            restartView.setFitHeight(height);
            restartView.setLayoutX(x);
            restartView.setLayoutY(y + 120);
            Rectangle restartBtn = new Rectangle(x , y + 120 , width , height);
            restartBtn.setStroke(Color.BLACK);
            restartBtn.setFill(Color.TRANSPARENT);
            pane.getChildren().addAll(restartView, restartBtn);
            restartBtn.setOnMouseClicked(event -> {
                //pane.getChildren().remove(bgMenuView);
                new ItemSelectionScreen(gameStage, gameCode);
            });
            restartBtn.setOnMouseEntered(event -> {
                restartBtn.setFill(Color.rgb(200, 200, 200, 0.1));
            });
            restartBtn.setOnMouseExited(event -> {
                restartBtn.setFill(Color.TRANSPARENT);
            });

            Image backImage = new Image(getClass().getResource("/buttons/back to game.png").toExternalForm());
            ImageView  backView = new ImageView(backImage);
            backView.setFitWidth(width);
            backView.setFitHeight(height);
            backView.setLayoutX(x);
            backView.setLayoutY(y + 240);
            Rectangle backBtn = new Rectangle(x , y + 240 , width , height);
            backBtn.setStroke(Color.BLACK);
            backBtn.setFill(Color.TRANSPARENT);
            pane.getChildren().addAll(backView, backBtn);
            backBtn.setOnMouseClicked(event -> {
                //pane.getChildren().remove(bgMenuView);
            });
            backBtn.setOnMouseEntered(event -> {
                backBtn.setFill(Color.rgb(200, 200, 200, 0.1));
            });
            backBtn.setOnMouseExited(event -> {
                backBtn.setFill(Color.TRANSPARENT);
            });

        });
        menuGameBtn.setOnMouseEntered(e -> {
            menuGameBtn.setFill(Color.rgb(200, 200, 200, 0.1));
        });
        menuGameBtn.setOnMouseExited(e -> {
            menuGameBtn.setFill(Color.TRANSPARENT);
        });

    }

    public static void setTimerLabel(String newTime) {
        timerLabel.setText(newTime);
    }

    public static void setPointLabel(String newPoint) {
        point.setText(newPoint);
    }
}