package advancedfinalproject.game.prototype;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameScreen extends Application {
    private final int width = 1500;
    private final int height = 750;

    @Override
    public void start(Stage gameStage) {

        Pane pane = new Pane();

        gameStage.setTitle("game screen");
        //gameStage.setMaximized(true);
        gameStage.setResizable(false);


        Image screen = new Image(getClass().getResource("/Screen Images/day-background.png").toExternalForm());
        ImageView screenImage = new ImageView(screen);

        screenImage.fitWidthProperty().bind(pane.widthProperty());
        screenImage.fitHeightProperty().bind(pane.heightProperty());

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeLine(370, 106, 370, 715);
        //gc.strokeLine(1435, 106, 1435, 715);
        gc.strokeLine(370, 106, 1435, 106);
        //gc.strokeLine(370, 715, 1435, 715);

        int cellSize = 118;
        int startX = 370, endX = 370;
        int startY = 106;
        int endY = 715;
        for (int i = 1; i <= 9; i++)
            gc.strokeLine(startX + i * cellSize, startY, endX + i * cellSize, endY);

        cellSize = 123;
        startX = 370;
        endX = 1435;
        startY = endY = 100;
        for (int i = 1; i <= 5; i++)
            gc.strokeLine(startX, startY + i * cellSize, endX, endY + i * cellSize);


        canvas.setOnMouseClicked((MouseEvent event) -> {
            double x = event.getX();
            double y = event.getY();
            System.out.println(x + " " + y);
        });

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

        pane.getChildren().addAll(screenImage, canvas, rectangle1, rectangle2, rectangle3,
                rectangle4, rectangle5, rectangle6);

        Scene screenScene = new Scene(pane, width, height);
        gameStage.setScene(screenScene);
        gameStage.show();





    }
}
