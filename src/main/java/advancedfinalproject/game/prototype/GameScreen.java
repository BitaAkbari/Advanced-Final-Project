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


        pane.getChildren().add(screenImage);
        pane.getChildren().add(canvas);

        Scene screenScene = new Scene(pane, width, height);
        gameStage.setScene(screenScene);
        gameStage.show();

    }
}
