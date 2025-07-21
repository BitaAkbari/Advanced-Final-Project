package advancedfinalproject.game.PlantsVsZombies;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameScreen extends Application {
    private final int WIDTH = 1500;
    private final int HEIGHT = 750;
    private final int ROW_COUNT = 5;
    private final int COL_COUNT = 9;
    private Rectangle[][] gridCells = new Rectangle[ROW_COUNT][COL_COUNT];
    private final int ITEM_COUNT = 6;
    private Rectangle[] store = new Rectangle[ITEM_COUNT];

    @Override
    public void start(Stage gameStage) {

        Pane pane = new Pane();

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

//        canvas.setOnMouseClicked((MouseEvent event) -> {
//            double x = event.getX();
//            double y = event.getY();
//            System.out.println(x + " " + y);
//        });

        Rectangle rectangle1 = new Rectangle(10, 10 , 130 , 90);
        Rectangle rectangle2 = new Rectangle(10, 110 , 130 , 90);
        Rectangle rectangle3 = new Rectangle(10, 210 , 130 , 90);
        Rectangle rectangle4 = new Rectangle(10, 310 , 130 , 90);
        Rectangle rectangle5 = new Rectangle(10, 410 , 130 , 90);
        Rectangle rectangle6 = new Rectangle(10, 510 , 130 , 90);

        Color rectangleColor = Color.RED;
        for (Rectangle rectangle : new Rectangle[]{rectangle1, rectangle2, rectangle3,
        rectangle4, rectangle5, rectangle6}) {
            rectangle.setFill(rectangleColor);
            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(2);
        }

        pane.getChildren().addAll(rectangle1, rectangle2, rectangle3,
                rectangle4, rectangle5, rectangle6);

        Scene screenScene = new Scene(pane, WIDTH, HEIGHT);
        gameStage.setScene(screenScene);
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

    }
}
