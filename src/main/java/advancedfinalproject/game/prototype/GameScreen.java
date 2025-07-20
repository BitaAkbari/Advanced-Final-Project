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
    @Override
    public void start(Stage gameStage) {

        Pane pane = new Pane();

        gameStage.setTitle("game screen");
        gameStage.setMaximized(true);

        Image screen = new Image(getClass().getResource("/Screen Images/day-background.png").toExternalForm());
        ImageView screenImage = new ImageView(screen);

        screenImage.fitWidthProperty().bind(pane.widthProperty());
        screenImage.fitHeightProperty().bind(pane.heightProperty());

        Canvas canvas = new Canvas(1500, 1000);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeLine(380, 106, 380, 760);
        gc.strokeLine(1490, 106, 1490, 760);
        gc.strokeLine(380, 106, 1490, 106);
        gc.strokeLine(380, 760, 1490, 760);

        int cellSize = 120;
        int startX = 380, endX = 380;
        int startY = 106;
        int endY = 760;
        for (int i = 1; i < 9; i++)
            gc.strokeLine(startX + i * cellSize, startY, endX + i * cellSize, endY);

        cellSize = 130;
        startX = 380;
        endX = 1490;
        startY = endY = 106;
        for (int i = 1; i < 5; i++)
            gc.strokeLine(startX, startY + i * cellSize, endX, endY + i * cellSize);


        canvas.setOnMouseClicked((MouseEvent event) -> {
            double x = event.getX();
            double y = event.getY();
            System.out.println(x + " " + y);
        });


        pane.getChildren().add(screenImage);
        pane.getChildren().add(canvas);

        Scene screenScene = new Scene(pane);
        gameStage.setScene(screenScene);
        gameStage.show();

    }
}
